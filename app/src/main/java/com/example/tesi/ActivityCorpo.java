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
    private Boolean ftorace = false;
    private Boolean fcollo = false;
    private Boolean ftesta = false;
    private Boolean fpiedi = false;
    private Boolean fcaviglie = false;
    private Boolean fstinchi = false;
    private Boolean fginocchia = false;
    private Boolean fcoscie = false;
    private Boolean fmanod = false;
    private Boolean fmanos = false;
    private Boolean favambd = false;
    private Boolean favambs = false;
    private Boolean fbracciod = false;
    private Boolean fbraccios = false;

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

        findViewById(R.id.piedi).setOnTouchListener(this);
        findViewById(R.id.caviglie).setOnTouchListener(this);
        findViewById(R.id.stinchi).setOnTouchListener(this);
        findViewById(R.id.ginocchia).setOnTouchListener(this);
        findViewById(R.id.coscie).setOnTouchListener(this);
        findViewById(R.id.addome).setOnTouchListener(this);
        findViewById(R.id.torace).setOnTouchListener(this);
        findViewById(R.id.collo).setOnTouchListener(this);
        findViewById(R.id.testa).setOnTouchListener(this);
        findViewById(R.id.braccioDestro).setOnTouchListener(this);
        findViewById(R.id.braccioSinistro).setOnTouchListener(this);
        findViewById(R.id.avambraccioDestro).setOnTouchListener(this);
        findViewById(R.id.avambraccioSinistro).setOnTouchListener(this);
        findViewById(R.id.manoDestra).setOnTouchListener(this);
        findViewById(R.id.manoSinistra).setOnTouchListener(this);


        findViewById(R.id.framePiedi).setOnDragListener(this);
        findViewById(R.id.frameCaviglie).setOnDragListener(this);
        findViewById(R.id.frameStinchi).setOnDragListener(this);
        findViewById(R.id.frameGinocchia).setOnDragListener(this);
        findViewById(R.id.frameCoscie).setOnDragListener(this);
        findViewById(R.id.frameAddome).setOnDragListener(this);
        findViewById(R.id.frameTorace).setOnDragListener(this);
        findViewById(R.id.frameCollo).setOnDragListener(this);
        findViewById(R.id.frameTesta).setOnDragListener(this);
        findViewById(R.id.frameBraccioD).setOnDragListener(this);
        findViewById(R.id.frameBraccioS).setOnDragListener(this);
        findViewById(R.id.frameAvambraccioD).setOnDragListener(this);
        findViewById(R.id.frameAvambraccioS).setOnDragListener(this);
        findViewById(R.id.frameManoS).setOnDragListener(this);
        findViewById(R.id.frameManoD).setOnDragListener(this);

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

                            if(getResources().getResourceEntryName(v.getId()).equals("frameTesta") && !ftesta) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                ftesta = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameTesta") && ftesta) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "braccioDestro":

                            if(getResources().getResourceEntryName(v.getId()).equals("frameBraccioD") && !fbracciod) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fbracciod = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameBraccioD") && fbracciod) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "braccioSinistro":

                            if(getResources().getResourceEntryName(v.getId()).equals("frameBraccioS") && !fbraccios) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fbraccios = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameBraccioS") && fbraccios) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "avambraccioDestro":

                            if(getResources().getResourceEntryName(v.getId()).equals("frameAvambraccioD") && !favambd) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                favambd = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameAvambraccioD") && favambd) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "avambraccioSinistro":

                            if(getResources().getResourceEntryName(v.getId()).equals("frameAvambraccioS") && !favambs) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                favambs = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameAvambraccioS") && favambs) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "manoDestra":

                            if(getResources().getResourceEntryName(v.getId()).equals("frameManoD") && !fmanod) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fmanod = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameManoD") && fmanod) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "manoSinistra":

                            if(getResources().getResourceEntryName(v.getId()).equals("frameManoS") && !fmanos) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fmanos = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameManoS") && fmanos) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "torace":

                            if(getResources().getResourceEntryName(v.getId()).equals("frameTorace") && !ftorace) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                ftorace = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameTorace") && ftorace) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "addome":

                            if(getResources().getResourceEntryName(v.getId()).equals("frameAddome") && !faddome) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                faddome = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameAddome") && faddome) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "coscie":

                            if(getResources().getResourceEntryName(v.getId()).equals("frameCoscie") && !fcoscie) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fcoscie = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameCoscie") && fcoscie) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "ginocchia":

                            if(getResources().getResourceEntryName(v.getId()).equals("frameGinocchia") && !fginocchia) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fginocchia = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameGinocchia") && fginocchia) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "stinchi":

                            if(getResources().getResourceEntryName(v.getId()).equals("frameStinchi") && !fstinchi) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fstinchi = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameStinchi") && fstinchi) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "caviglie":

                            if(getResources().getResourceEntryName(v.getId()).equals("frameCaviglie") && !fcaviglie) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fcaviglie = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameCaviglie") && fcaviglie) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "piedi":

                            if(getResources().getResourceEntryName(v.getId()).equals("framePiedi") && !fpiedi) {
                                //Toast.makeText(getApplicationContext(), ":", Toast.LENGTH_SHORT).show();
                                fpiedi = true;
                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setVisibility(View.VISIBLE);
                                img.setScaleType(ImageView.ScaleType.FIT_XY);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);

                            } else if(getResources().getResourceEntryName(v.getId()).equals("framePiedi") && fpiedi) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            }
                            break;
                        case "collo":
                            if(getResources().getResourceEntryName(v.getId()).equals("frameCollo") && !fcollo) {
                                fcollo = true;

                                owner.removeView(view);
                                owner.setVisibility(View.GONE);
                                FrameLayout container = (FrameLayout) v;
                                ImageView img = (ImageView) view;
                                container.addView(img);
                                img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                img.setVisibility(View.VISIBLE);

                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                                spinner.setAdapter(adapter);
                                spinner.setSelection(0);
                            } else if(getResources().getResourceEntryName(v.getId()).equals("frameCollo") && fcollo) {
                                ArrayList parole = new ArrayList();
                                parole.add("Gamba destra"); parole.add("Surface de but"); parole.add("Goal area");
                                SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
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