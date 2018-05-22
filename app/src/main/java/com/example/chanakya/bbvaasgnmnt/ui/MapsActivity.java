package com.example.chanakya.bbvaasgnmnt.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.chanakya.bbvaasgnmnt.R;
import com.example.chanakya.bbvaasgnmnt.model.Result;
import com.example.chanakya.bbvaasgnmnt.model.ResultsItem;
import com.example.chanakya.bbvaasgnmnt.network.RetrofitInstance;
import com.example.chanakya.bbvaasgnmnt.network.RetrofitService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient googleApiClient;
    Location currentLocation;
     public static List<ResultsItem> listOfData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        listOfData = new ArrayList<>();


        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        googleApiClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleApiClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                           currentLocation = location;
                        mapFragment.getMapAsync(MapsActivity.this);

                        getBBVACompass(currentLocation);

                      //  createLocationRequest();


                    }
                });


    }


    //   https://maps.googleapis.com/maps/api/place/textsearch/json?
    // query=BBVA+Compass&location=41.9174331,88.2653861&radius=10000&key=AIzaSyDkL1ryGllP785vw6YTZFW46Wfq-eXs_D0


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.mapDisplay:
                SupportMapFragment mapFragment2 = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                ViewGroup.LayoutParams params2 = mapFragment2.getView().getLayoutParams();
                params2.height = 1550;
                params2.width = 1050;
                mapFragment2.getView().setLayoutParams(params2);

                FrameLayout frameLayout2=findViewById(R.id.fragment);
                frameLayout2.setLayoutParams(new LinearLayout.LayoutParams(0 , 0));
                mapFragment2.getMapAsync(this);

                return true;


            case R.id.listDisplay:


                ListFragment listFragment = new ListFragment();

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
                params.height = 0;
                params.width = 0;
                mapFragment.getView().setLayoutParams(params);
                FrameLayout frameLayout=findViewById(R.id.fragment);
                frameLayout.setLayoutParams(new LinearLayout.LayoutParams(1050 , 1550));

                   getSupportFragmentManager()
                           .beginTransaction()
                           .replace(R.id.fragment,listFragment)
                           .commit();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }




    private void getBBVACompass(Location loc) {

        RetrofitService service = RetrofitInstance.getRetrofitInstance().create(RetrofitService.class);

        Call<Result> call = service.getResponse("BBVA+Compass",loc.getLatitude()+","+loc.getLongitude(),10000,"AIzaSyDkL1ryGllP785vw6YTZFW46Wfq-eXs_D0" );

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
              //  Log.e("response", response.body().getResults() + "");
                listOfData = response.body().getResults();
                SupportMapFragment mapFragment2 = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

                mapFragment2.getMapAsync(MapsActivity.this);



            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
               Log.e("response",t.getMessage());
            }
        });
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
          String name,icon,address;

        for(final ResultsItem item: listOfData){
           //Log.e("currentLocation", item.getGeometry().getLocation().getLat() + "--------" +item.getGeometry().getLocation().getLng() + "" );



            LatLng location = new LatLng(item.getGeometry().getLocation().getLat() , item.getGeometry().getLocation().getLng());
            mMap.addMarker(new MarkerOptions().position(location).title(item.getName())).showInfoWindow();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));


            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    Intent intent = new Intent(MapsActivity.this,DetailActivity.class);
                     intent.putExtra("item", item);
                     startActivity(intent);

                }
            });

        }




        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);


    }



}
