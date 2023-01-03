package com.example.tesi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SpinnerAdapter extends ArrayAdapter<String> {

    public SpinnerAdapter(Context context, int textViewResourceId,
                          ArrayList<String> lista) {
        super(context, textViewResourceId, lista);

    }


    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.spinner_base, null);
        TextView textView = (TextView)v.findViewById(R.id.textView_SB);
        String text = getItem(i);

        textView.setText(text);
        ImageView imageView = (ImageView)v.findViewById(R.id.imageView_SB);

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

        return v;

    }
    }
