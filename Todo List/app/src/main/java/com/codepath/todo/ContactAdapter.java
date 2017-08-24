package com.codepath.todo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Sarahwang on 8/23/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {
    ArrayList<ContactInfo> contactList;
    Context c;
    Bitmap ThumbImage;

    public ContactAdapter(Context ctx, ArrayList<ContactInfo> contactList) {
        //ASSIGN THEM LOCALLY
        this.c = ctx;
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.namecard_layout, viewGroup, false);
        ContactViewHolder holder = new ContactViewHolder(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {

        ContactInfo ci = contactList.get(i);
        contactViewHolder.vTitle.setText(ci.name);
        contactViewHolder.vDescription.setText(ci.description);
        try {
            if (ci.path != null) {
                ParcelFileDescriptor parcelFD = null;
                parcelFD = c.getContentResolver().openFileDescriptor(Uri.parse(ci.path), "r");
                FileDescriptor imageSource = parcelFD.getFileDescriptor();

                ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFileDescriptor(imageSource), 100, 120);
                contactViewHolder.vTN.setImageBitmap(ThumbImage);
                contactViewHolder.vTN.setRotation(180f);

            }

            contactViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    //CREATE INTENT
                    Intent i = new Intent(c, EditList.class);

                    //LOAD DATA
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", contactList.get(pos).getId());
                    bundle.putString("title", contactList.get(pos).getName());
                    bundle.putString("note", contactList.get(pos).getDescription());
                    bundle.putString("aPicPath", contactList.get(pos).getPath());

                    i.putExtras(bundle);

                    //START ACTIVITY
                    c.startActivity(i);

                }
            });

        } catch (FileNotFoundException e) {
            // handle errors
        }
    }

    public void onItemDismissed(int position) {

        ContactDbHelper db = new ContactDbHelper(c);

        if (contactList.get(position).path != null) {
            File fdelete = new File(Uri.parse(contactList.get(position).path).getPath());
            if (fdelete.exists())
                fdelete.delete();
        }
        db.delete(contactList.get(position).getId());
        contactList.remove(position);


        notifyItemRemoved(position);
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        ContactInfo temp = contactList.get(fromPosition);
        contactList.set(fromPosition, contactList.get(toPosition));
        contactList.set(toPosition, temp);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }


}