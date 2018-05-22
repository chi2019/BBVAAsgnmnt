package com.example.chanakya.bbvaasgnmnt.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chanakya.bbvaasgnmnt.R;
import com.example.chanakya.bbvaasgnmnt.model.ResultsItem;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ImageView icon;
    TextView name,address,id,placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ResultsItem item  = getIntent().getParcelableExtra("item");

        icon = findViewById(R.id.imageViewIconImage);
        name = findViewById(R.id.textViewDetailName);
        address = findViewById(R.id.textViewAddress);
        id = findViewById(R.id.textViewID);
        placeId = findViewById(R.id.textViewPlaceId);


        Picasso.with(this).load(item.getIcon()).into(icon);
        name.setText("NAME:" + item.getName());
        address.setText("ADDRESS:" + item.getFormattedAddress());
        id.setText("ID:" + item.getId());
        placeId.setText("PlaceId:" + item.getPlaceId());

    }


}
