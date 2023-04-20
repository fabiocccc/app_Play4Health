package com.example.tesi.AppTravisani;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tesi.R;

public class CustomPremiAdapter extends BaseAdapter {

    Context context;
    String listPremi[];
    int listImagePremi[];
    LayoutInflater inflater;


    public CustomPremiAdapter(Context ctx, String [] premiList, int [] imageList ){
        this.context= ctx;
        this.listPremi = premiList;
        this.listImagePremi = imageList;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return listPremi.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.custom_list_view, null);

        TextView txtView = convertView.findViewById(R.id.txtPremio);
        ImageView premioImg = convertView.findViewById(R.id.imagePremio);
        txtView.setText(listPremi[position]);
        premioImg.setImageResource(listImagePremi[position]);

        return convertView;
    }
}
