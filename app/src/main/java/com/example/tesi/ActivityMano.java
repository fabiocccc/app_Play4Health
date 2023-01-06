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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityMano extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {

    private Boolean fpolso = false;
    private Boolean fpalmo = false;
    private Boolean fpollice = false;
    private Boolean findice = false;
    private Boolean fmedio = false;
    private Boolean fanulare = false;
    private Boolean fmignolo = false;

    private TextToSpeech textToSpeech;
    private Spinner spinner;
    private Drawable enterShape;
    private Drawable normalShape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mano);

        enterShape = getResources().getDrawable(
                R.drawable.shape_droptarget);
        normalShape = getResources().getDrawable(R.drawable.shape);

        spinner = findViewById(R.id.spinner_Mano);

        findViewById(R.id.framepalmo).setOnDragListener(this);
        findViewById(R.id.framepolso).setOnDragListener(this);
        findViewById(R.id.framepollice).setOnDragListener(this);
        findViewById(R.id.frameindice).setOnDragListener(this);
        findViewById(R.id.framemedio).setOnDragListener(this);
        findViewById(R.id.frameanulare).setOnDragListener(this);
        findViewById(R.id.framemignolo).setOnDragListener(this);

        findViewById(R.id.frame1).setOnDragListener(this);
        findViewById(R.id.frame2).setOnDragListener(this);
        findViewById(R.id.frame3).setOnDragListener(this);
        findViewById(R.id.frame4).setOnDragListener(this);
        findViewById(R.id.frame5).setOnDragListener(this);
        findViewById(R.id.frame6).setOnDragListener(this);
        findViewById(R.id.frame7).setOnDragListener(this);

        findViewById(R.id.polso).setOnTouchListener(this);
        findViewById(R.id.palmo).setOnTouchListener(this);
        findViewById(R.id.pollice).setOnTouchListener(this);
        findViewById(R.id.indice).setOnTouchListener(this);
        findViewById(R.id.medio).setOnTouchListener(this);
        findViewById(R.id.anulare).setOnTouchListener(this);
        findViewById(R.id.mignolo).setOnTouchListener(this);

    }


    @Override
    protected void onStart() {
        super.onStart();



        findViewById(R.id.framepolso).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(fpolso){
                    ArrayList parole = new ArrayList();
                    parole.add("Gamba destra");
                    parole.add("Surface de but");
                    parole.add("Goal area");
                    SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(0);

                }

                return true;
            }
        });

        findViewById(R.id.framepalmo).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(fpalmo){
                    ArrayList parole = new ArrayList();
                    parole.add("Gamba destra");
                    parole.add("Surface de but");
                    parole.add("Goal area");
                    SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(0);

                }

                return true;
            }
        });


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
                    case "polso":

                        if (getResources().getResourceEntryName(v.getId()).equals("framepolso") && !fpolso) {
                            //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                            fpolso = true;
                            owner.removeView(view);
                            owner.setVisibility(View.GONE);

                            if(fpalmo){
                                findViewById(R.id.image_mano_polso_palmo).setVisibility(View.VISIBLE);
                                findViewById(R.id.image_mano_palmo).setVisibility(View.GONE);
                            }else{
                                findViewById(R.id.image_mano_polso).setVisibility(View.VISIBLE);
                                findViewById(R.id.image_mano_vuota).setVisibility(View.GONE);
                            }


                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);

                        } else if (getResources().getResourceEntryName(v.getId()).equals("framepolso") && fpolso) {
                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);
                        }
                        break;
                    case "palmo":

                        if (getResources().getResourceEntryName(v.getId()).equals("framepalmo") && !fpalmo) {
                            //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                            fpalmo = true;
                            owner.removeView(view);
                            owner.setVisibility(View.GONE);

                            if(fpolso){
                                findViewById(R.id.image_mano_polso_palmo).setVisibility(View.VISIBLE);
                                findViewById(R.id.image_mano_polso).setVisibility(View.GONE);
                            }else{
                                findViewById(R.id.image_mano_palmo).setVisibility(View.VISIBLE);
                                findViewById(R.id.image_mano_vuota).setVisibility(View.GONE);
                            }


                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);

                        } else if (getResources().getResourceEntryName(v.getId()).equals("framepalmo") && fpalmo) {
                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);
                        }
                        break;
                    case "pollice":

                        if (getResources().getResourceEntryName(v.getId()).equals("framepollice") && !fpollice) {
                            //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                            fpollice = true;
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
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);

                        } else if (getResources().getResourceEntryName(v.getId()).equals("framepollice") && fpollice) {
                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);
                        }
                        break;
                    case "indice":

                        if (getResources().getResourceEntryName(v.getId()).equals("frameindice") && !findice) {
                            //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                            findice = true;
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
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);

                        } else if (getResources().getResourceEntryName(v.getId()).equals("frameindice") && findice) {
                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);
                        }
                        break;
                    case "medio":

                        if (getResources().getResourceEntryName(v.getId()).equals("framemedio") && !fmedio) {
                            //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                            fmedio = true;
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
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);

                        } else if (getResources().getResourceEntryName(v.getId()).equals("framemedio") && fmedio) {
                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);
                        }
                        break;
                    case "anulare":

                        if (getResources().getResourceEntryName(v.getId()).equals("frameanulare") && !fanulare) {
                            //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                            fanulare = true;
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
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);

                        } else if (getResources().getResourceEntryName(v.getId()).equals("frameanulare") && fanulare) {
                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);
                        }
                        break;
                    case "mignolo":

                        if (getResources().getResourceEntryName(v.getId()).equals("framemignolo") && !fmignolo) {
                            //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                            fmignolo = true;
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
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                            spinner.setAdapter(adapter);
                            spinner.setSelection(0);

                        } else if (getResources().getResourceEntryName(v.getId()).equals("framemignolo") && fmignolo) {
                            ArrayList parole = new ArrayList();
                            parole.add("Gamba destra");
                            parole.add("Surface de but");
                            parole.add("Goal area");
                            SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
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