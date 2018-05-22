package com.example.chanakya.bbvaasgnmnt.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chanakya.bbvaasgnmnt.R;
import com.example.chanakya.bbvaasgnmnt.adapter.Adapter;
import com.example.chanakya.bbvaasgnmnt.model.ResultsItem;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    List<ResultsItem> listForDisplay;
    RecyclerView recyclerView;
    Adapter adapter;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_list, container, false);

        listForDisplay = new ArrayList<>();
        listForDisplay = MapsActivity.listOfData;

        recyclerView = v.findViewById(R.id.recyclerView);
        adapter = new Adapter(getContext(), listForDisplay);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        return v;
    }

}
