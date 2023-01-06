package com.example.tesi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class testAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list;

    public testAdapter(Context context, ArrayList<String> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public String getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View rootView = LayoutInflater.from(context).
                inflate(R.layout.spinner_base, viewGroup, false);

        TextView textView = (TextView) rootView.findViewById(R.id.textView_SB);
        String text = getItem(i);

        textView.setText(text);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView_SB);

        switch(i) {
            case 0:
                imageView.setImageResource(R.drawable.ita);
                break;
            case 1:
                imageView.setImageResource(R.drawable.fra);
                break;
            case 2:
                imageView.setImageResource(R.drawable.eng);

        }

        return rootView;
    }
}
