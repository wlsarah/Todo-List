package com.codepath.todo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sarahwang on 8/23/17.
 */

public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // protected TextView vName;
    protected TextView vDescription;
    protected TextView vTitle;
    protected ImageView vTN;
    ItemClickListener itemClickListener;

    public ContactViewHolder(View v) {
        super(v);
        v.setOnClickListener(this);
        vDescription = (TextView) v.findViewById(R.id.aDescription);
        vTitle = (TextView) v.findViewById(R.id.title);
        vTN = (ImageView) v.findViewById(R.id.thumbnail);
    }

    @Override
    public void onClick(View v) {
//        if(v == null)
//        {System.out.println("V----NULL");}
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic) {
        this.itemClickListener = ic;
    }
}
