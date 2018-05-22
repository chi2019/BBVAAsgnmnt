package com.example.chanakya.bbvaasgnmnt.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chanakya.bbvaasgnmnt.ui.DetailActivity;
import com.example.chanakya.bbvaasgnmnt.R;
import com.example.chanakya.bbvaasgnmnt.model.ResultsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by chanakya on 5/22/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    List<ResultsItem> list;
    ResultsItem item;

    public Adapter(Context context, List<ResultsItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("item",item);
                context.startActivity(intent);
            }
        });

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        item  = list.get(position);

        holder.name.setText(item.getName());
        holder.address.setText(item.getFormattedAddress());

        if(!item.getIcon().equals(null)) {

            Picasso.with(context)
                    .load(item.getIcon())
                    .into(holder.icon);

        }



    }




    @Override
    public int getItemCount() {
        return list.size();
    }


    public class  MyViewHolder extends RecyclerView.ViewHolder{

       TextView name,address ;
       ImageView icon;


       public MyViewHolder(View itemView) {
           super(itemView);

           name = itemView.findViewById(R.id.textViewDetailName);
           address = itemView.findViewById(R.id.textViewAddress);
           icon = itemView.findViewById(R.id.imageViewIcon);

       }

   }

}
