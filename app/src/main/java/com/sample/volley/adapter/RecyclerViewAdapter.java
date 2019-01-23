package com.sample.volley.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sample.volley.R;
import com.sample.volley.webservice.ReturnValues;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> {

    private ArrayList<ReturnValues.SampleList> myList;
    private Activity myContext;

    public RecyclerViewAdapter(Activity aContext, ArrayList<ReturnValues.SampleList> aList) {
        this.myContext = aContext;
        this.myList = aList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_inflate_recycler_view, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        final ReturnValues.SampleList feedList = myList.get(position);

        holder.myUserName.setText("");
        holder.myFeedTitle.setText("");

        holder.myFeedTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        holder.myMainLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("feed_id", "");
                //new FragmentManager(myContext).updateContent(new Samplescreen(), null, Samplescreen.TAG, "Title", bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView myFeedTitle, myUserName;
        LinearLayout myMainLay;

        ItemViewHolder(View view) {
            super(view);
            myFeedTitle = view.findViewById(R.id.inflate_biz_feed_title);
            myUserName = view.findViewById(R.id.inflate_biz_feed_user_name);
            myMainLay = view.findViewById(R.id.inflate_biz_feed_LAY);

        }
    }

}