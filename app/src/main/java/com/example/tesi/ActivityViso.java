package com.example.tesi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityViso extends AppCompatActivity implements View.OnDragListener, View.OnTouchListener {

    private Boolean fbocca = false;
    private Boolean fcapelli = false;
    private Boolean forecchie = false;
    private Boolean focchi = false;
    private Boolean fsopracc = false;
    private Boolean fnaso = false;
    private Spinner spinner;
    private TextToSpeech textToSpeech;

    private Drawable enterShape;
    private Drawable normalShape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viso);

        enterShape = getResources().getDrawable(
                R.drawable.shape_droptarget);
        normalShape = getResources().getDrawable(R.drawable.shape);

        spinner = findViewById(R.id.spinner_Viso);

        findViewById(R.id.framecapelli).setOnDragListener(this);
        findViewById(R.id.frameorecchio1).setOnDragListener(this);
        findViewById(R.id.frameorecchio2).setOnDragListener(this);
        findViewById(R.id.framenaso).setOnDragListener(this);
        findViewById(R.id.frameocchi).setOnDragListener(this);
        findViewById(R.id.framesopracciglia).setOnDragListener(this);
        findViewById(R.id.framebocca).setOnDragListener(this);

        findViewById(R.id.frame1).setOnDragListener(this);
        findViewById(R.id.frame2).setOnDragListener(this);
        findViewById(R.id.frame3).setOnDragListener(this);
        findViewById(R.id.frame4).setOnDragListener(this);
        findViewById(R.id.frame5).setOnDragListener(this);
        findViewById(R.id.frame6).setOnDragListener(this);

        findViewById(R.id.bocca).setOnTouchListener(this);
        findViewById(R.id.naso).setOnTouchListener(this);
        findViewById(R.id.occhi).setOnTouchListener(this);
        findViewById(R.id.sopracciglia).setOnTouchListener(this);
        findViewById(R.id.capelli).setOnTouchListener(this);
        findViewById(R.id.orecchie).setOnTouchListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            switch (i){
                                case 0:
                                    //Toast.makeText(getApplicationContext(), "ITALIA",Toast.LENGTH_SHORT).show();
                                    textToSpeech.setLanguage(Locale.ITALIAN);
                                    String toSpeakIt = spinner.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakIt, TextToSpeech.QUEUE_FLUSH, null);
                                    break;
                                case 1:
                                    //Toast.makeText(getApplicationContext(), "ENGLISH",Toast.LENGTH_SHORT).show();
                                    textToSpeech.setLanguage(Locale.FRANCE);
                                    String toSpeakFr = spinner.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakFr, TextToSpeech.QUEUE_FLUSH, null);
                                    break;
                                case 2:
                                    //Toast.makeText(getApplicationContext(), "BAGUETA",Toast.LENGTH_SHORT).show();
                                    textToSpeech.setLanguage(Locale.ENGLISH);
                                    String toSpeakEn = spinner.getSelectedItem().toString();

                                    textToSpeech.speak(toSpeakEn, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                v.setBackgroundDrawable(enterShape);
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                v.setBackgroundDrawable(null);
                break;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup
                View view = (View) event.getLocalState(); //immagine // View v = framelayout in cui è droppato
                ViewGroup owner = (ViewGroup) view.getParent();/*
                    owner.removeView(view);
                    owner.setVisibility(View.GONE);
                    FrameLayout container = (FrameLayout) v;*/


                    /*container.addView(view); VECCHIO
                    view.setVisibility(View.VISIBLE);*/
/*

                    ImageView img = (ImageView) view;
                    container.addView(img);
                    img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    img.setScaleType(ImageView.ScaleType.FIT_XY);
                    img.setVisibility(View.VISIBLE);*/

                switch (getResources().getResourceEntryName(view.getId())) {
                    case "capelli":

                        if (getResources().getResourceEntryName(v.getId()).equals("framecapelli") && !fcapelli) {
                            //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                            fcapelli = true;
                            owner.removeView(view);
                            owner.setVisibility(View.GONE);

                            if(forecchie){
                                findViewById(R.id.image_face_capelli_orecchie).setVisibility(View.VISIBLE);
                                findViewById(R.id.image_face_orecchie).setVisibility(View.GONE);
                            }else{
                                findViewById(R.id.image_face_capelli).setVisibility(View.VISIBLE);
                                findViewById(R.id.image_face).setVisibility(View.GONE);
                            }


                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);

                        } else if (getResources().getResourceEntryName(v.getId()).equals("framecapelli") && fcapelli) {
                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);
                        }
                        break;
                    case "orecchie":

                        if (getResources().getResourceEntryName(v.getId()).equals("frameorecchio1") ||
                                getResources().getResourceEntryName(v.getId()).equals("frameorecchio2") && !forecchie) {
                            //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                            forecchie = true;
                            owner.removeView(view);
                            owner.setVisibility(View.GONE);

                            if(fcapelli){
                                findViewById(R.id.image_face_capelli_orecchie).setVisibility(View.VISIBLE);
                                findViewById(R.id.image_face_capelli).setVisibility(View.GONE);
                            }else{
                                findViewById(R.id.image_face_orecchie).setVisibility(View.VISIBLE);
                                findViewById(R.id.image_face).setVisibility(View.GONE);
                            }


                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);

                        } else if (getResources().getResourceEntryName(v.getId()).equals("framecapelli") && fcapelli) {
                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);
                        }
                        break;
                    case "bocca":

                        if (getResources().getResourceEntryName(v.getId()).equals("framebocca") && !fbocca) {
                            //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                            fbocca = true;
                            owner.removeView(view);
                            owner.setVisibility(View.GONE);
                            FrameLayout container = (FrameLayout) v;
                            ImageView img = (ImageView) view;
                            container.addView(img);
                            img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            img.setVisibility(View.VISIBLE);

                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);

                        } else if (getResources().getResourceEntryName(v.getId()).equals("frametesta") && fbocca) {
                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);
                        }
                        break;
                    case "naso":

                        if (getResources().getResourceEntryName(v.getId()).equals("framenaso") && !fnaso) {
                            //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                            fnaso = true;
                            owner.removeView(view);
                            owner.setVisibility(View.GONE);
                            FrameLayout container = (FrameLayout) v;
                            ImageView img = (ImageView) view;
                            container.addView(img);
                            img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            img.setVisibility(View.VISIBLE);

                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);

                        } else if (getResources().getResourceEntryName(v.getId()).equals("framenaso") && fnaso) {
                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);
                        }
                        break;
                    case "occhi":

                        if (getResources().getResourceEntryName(v.getId()).equals("frameocchi") && !focchi) {
                            //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                            focchi = true;
                            owner.removeView(view);
                            owner.setVisibility(View.GONE);
                            FrameLayout container = (FrameLayout) v;
                            ImageView img = (ImageView) view;
                            container.addView(img);
                            img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            img.setVisibility(View.VISIBLE);

                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);

                        } else if (getResources().getResourceEntryName(v.getId()).equals("frameocchi") && focchi) {
                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);
                        }
                        break;
                    case "sopracciglia":

                        if (getResources().getResourceEntryName(v.getId()).equals("framesopracciglia") && !fsopracc) {
                            //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                            fsopracc = true;
                            owner.removeView(view);
                            owner.setVisibility(View.GONE);
                            FrameLayout container = (FrameLayout) v;
                            ImageView img = (ImageView) view;
                            container.addView(img);
                            img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                            img.setVisibility(View.VISIBLE);

                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);

                        } else if (getResources().getResourceEntryName(v.getId()).equals("framesopracciglia") && fsopracc) {
                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);
                        }
                        break;
                }
            case DragEvent.ACTION_DRAG_ENDED:
                //v.setBackgroundDrawable(normalShape);
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText(getResources().getResourceEntryName(view.getId()), "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    view);
            view.startDrag(data, shadowBuilder, view, 0);
            //view.setVisibility(View.INVISIBLE);
            return true;
        } else {
            return false;
        }
    }
}