package com.example.tesi.AppTravisani;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;

import com.example.tesi.AppTravisani.Percorso1.P1EpisodiActivity;
import com.example.tesi.AppTravisani.Percorso2.PassiP2Activity;
import com.example.tesi.R;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class Adapter extends PagerAdapter {

    MediaPlayer player;

    private List<Model> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter(List<Model> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, container, false);

        ImageView imageView;
        TextView title, desc;

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);

        imageView.setImageResource(models.get(position).getImage());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //fa click
                playsound();

                Intent i;

                // al click sulla card apre la pagina corretta
                switch (position + 1){
                    case 1:
                        i = new Intent(context, P1EpisodiActivity.class);
                        i.putExtra("flagDo", 0);
                        context.startActivity(i);
                        break;
                    case 2:
                        Toast.makeText(context, "Si apriranno gli episodi della storia 2", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(context, "Si apriranno gli episodi della storia 3", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(context, "Si apriranno gli episodi della storia 4", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }

            }

        });
        title.setText(models.get(position).getTitle());
        desc.setText(models.get(position).getDesc());

        container.addView(view, 0);

        return view;
    }

    private void playsound() {

        if(player == null)
        {
            player = MediaPlayer.create(context, R.raw.soundclick);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopPlayer();
                }
            });
        }

        player.start();


    }

    private void stopPlayer(){

        if(player != null){
            player.release();
            player = null;
           // Toast.makeText(context, "MediaPlayer releases", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


}
