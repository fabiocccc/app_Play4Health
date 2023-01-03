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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityCorpo extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {

    private ImageView img1;

    int windowwidth;
    int windowheight;
    private Spinner spinner;
    private TextToSpeech textToSpeech;

    private Boolean faddome = false;
    private Boolean fpetto = false;
    private Boolean ftesta = false;
    private Boolean fpiedes = false;
    private Boolean fpieded = false;
    private Boolean fpolpd = false;
    private Boolean fpolps = false;
    private Boolean fcoscias = false;
    private Boolean fcosciad = false;
    private Boolean fmanos = false;
    private Boolean fmanod = false;
    private Boolean favambs = false;
    private Boolean favambd = false;
    private Boolean fbraccios = false;
    private Boolean fbracciod = false;

    private Drawable enterShape;
    private Drawable normalShape;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corpo);

        enterShape = getResources().getDrawable(
                R.drawable.shape_droptarget);
        normalShape = getResources().getDrawable(R.drawable.shape);

        spinner = findViewById(R.id.spinner_Corpo);
        windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        windowheight = getWindowManager().getDefaultDisplay().getHeight();

        findViewById(R.id.piedes).setOnTouchListener(this);
        findViewById(R.id.pieded).setOnTouchListener(this);
        findViewById(R.id.polps).setOnTouchListener(this);
        findViewById(R.id.polpd).setOnTouchListener(this);
        findViewById(R.id.cosciad).setOnTouchListener(this);
        findViewById(R.id.coscias).setOnTouchListener(this);
        findViewById(R.id.addome).setOnTouchListener(this);
        findViewById(R.id.testa).setOnTouchListener(this);
        findViewById(R.id.petto).setOnTouchListener(this);
        findViewById(R.id.manod).setOnTouchListener(this);
        findViewById(R.id.manos).setOnTouchListener(this);
        findViewById(R.id.bracciod).setOnTouchListener(this);
        findViewById(R.id.braccios).setOnTouchListener(this);
        findViewById(R.id.avambd).setOnTouchListener(this);
        findViewById(R.id.avambs).setOnTouchListener(this);


        findViewById(R.id.framepieded).setOnDragListener(this);
        findViewById(R.id.framepolpd).setOnDragListener(this);
        findViewById(R.id.framegambad).setOnDragListener(this);
        findViewById(R.id.framepetto).setOnDragListener(this);
        findViewById(R.id.frameaddome).setOnDragListener(this);
        findViewById(R.id.frametesta).setOnDragListener(this);
        findViewById(R.id.framemanod).setOnDragListener(this);
        findViewById(R.id.frameavambd).setOnDragListener(this);
        findViewById(R.id.framebracciod).setOnDragListener(this);
        findViewById(R.id.framepiedes).setOnDragListener(this);
        findViewById(R.id.framepolps).setOnDragListener(this);
        findViewById(R.id.framegambas).setOnDragListener(this);
        findViewById(R.id.framemanos).setOnDragListener(this);
        findViewById(R.id.frameavambs).setOnDragListener(this);
        findViewById(R.id.framebraccios).setOnDragListener(this);

        findViewById(R.id.frame1).setOnDragListener(this);
        findViewById(R.id.frame2).setOnDragListener(this);
        findViewById(R.id.frame3).setOnDragListener(this);
        findViewById(R.id.frame4).setOnDragListener(this);
        findViewById(R.id.frame5).setOnDragListener(this);
        findViewById(R.id.frame6).setOnDragListener(this);
        findViewById(R.id.frame7).setOnDragListener(this);
        findViewById(R.id.frame8).setOnDragListener(this);
        findViewById(R.id.frame9).setOnDragListener(this);
        findViewById(R.id.frame10).setOnDragListener(this);
        findViewById(R.id.frame11).setOnDragListener(this);
        findViewById(R.id.frame12).setOnDragListener(this);
        findViewById(R.id.frame13).setOnDragListener(this);
        findViewById(R.id.frame14).setOnDragListener(this);
        findViewById(R.id.frame15).setOnDragListener(this);

       /* findViewById(R.id.framepolpd).setOnDragListener(new MyDragListener());
        findViewById(R.id.framepieded).setOnDragListener(new MyDragListener());
        findViewById(R.id.frameLayout1).setOnDragListener(new MyDragListener());
        findViewById(R.id.frameLayout2).setOnDragListener(new MyDragListener());*/

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

                    switch (getResources().getResourceEntryName(view.getId())){
                        case "testa":

                            if(getResources().getResourceEntryName(v.getId()).equals("frametesta") && !ftesta) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                ftesta = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frametesta") && ftesta) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "braccios":

                            if(getResources().getResourceEntryName(v.getId()).equals("framebraccios") && !fbraccios) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fbraccios = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("framebraccios") && fbraccios) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "bracciod":

                            if(getResources().getResourceEntryName(v.getId()).equals("framebracciod") && !fbracciod) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fbracciod = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("framebracciod") && fbracciod) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "avambd":

                            if(getResources().getResourceEntryName(v.getId()).equals("frameavambd") && !favambd) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                favambd = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameavambd") && favambd) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "avambs":

                            if(getResources().getResourceEntryName(v.getId()).equals("frameavambs") && !favambs) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                favambs = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameavambs") && favambs) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "manod":

                            if(getResources().getResourceEntryName(v.getId()).equals("framemanod") && !fmanod) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fmanod = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("framemanod") && fmanod) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "manos":

                            if(getResources().getResourceEntryName(v.getId()).equals("framemanos") && !fmanos) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fmanos = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("framemanos") && fmanos) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "petto":

                            if(getResources().getResourceEntryName(v.getId()).equals("framepetto") && !fpetto) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fpetto = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("framepetto") && fpetto) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "addome":

                            if(getResources().getResourceEntryName(v.getId()).equals("frameaddome") && !faddome) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                faddome = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameaddome") && faddome) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "cosciad":

                            if(getResources().getResourceEntryName(v.getId()).equals("framegambad") && !fcosciad) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fcosciad = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("framegambad") && fcosciad) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "coscias":

                            if(getResources().getResourceEntryName(v.getId()).equals("framegambas") && !fcoscias) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fcoscias = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("framegambas") && fcoscias) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "polps":

                            if(getResources().getResourceEntryName(v.getId()).equals("framepolps") && !fpolps) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fpolpd = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("framepolpd") && fpolpd) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "polpd":

                            if(getResources().getResourceEntryName(v.getId()).equals("framepolpd") && !fpolpd) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fpolps = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("framepolps") && fpolps) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "piedes":

                            if(getResources().getResourceEntryName(v.getId()).equals("framepiedes") && !fpiedes) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fpiedes = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("framepiedes") && fpiedes) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "pieded":
                            if(getResources().getResourceEntryName(v.getId()).equals("framepieded") && !fpieded) {
                                fpieded = true;

                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            } else if(getResources().getResourceEntryName(v.getId()).equals("framepieded") && fpieded) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }


}