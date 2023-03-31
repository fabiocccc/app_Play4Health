package com.example.tesi.AppPavone;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.graphics.Path;
import android.graphics.drawable.AnimationDrawable;
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

import com.example.tesi.R;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityViso extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener, View.OnClickListener {

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

    private FrameLayout button_aiuto;
    private ImageView help1;
    private AnimationDrawable animationDrawable = null;

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

        findViewById(R.id.bocca).setOnLongClickListener(this);
        findViewById(R.id.naso).setOnLongClickListener(this);
        findViewById(R.id.occhi).setOnLongClickListener(this);
        findViewById(R.id.sopracciglia).setOnLongClickListener(this);
        findViewById(R.id.capelli).setOnLongClickListener(this);
        findViewById(R.id.orecchie).setOnLongClickListener(this);

        button_aiuto = findViewById(R.id.button_aiuto);
        help1 = findViewById(R.id.help1);

        findViewById(R.id.button_indietro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        button_aiuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                help1.setVisibility(View.VISIBLE);
                button_aiuto.setVisibility(View.GONE);

                help1.setBackgroundResource(R.drawable.animation_pointing);
                animationDrawable = (AnimationDrawable) help1.getBackground();
                animationDrawable.setOneShot(true);
                animationDrawable.start();

                //ObjectAnimator animation = ObjectAnimator.ofFloat(help1, "translationY", -700f);
                Path path = new Path();
                path.lineTo(-520f, 700f);
                ObjectAnimator animation = ObjectAnimator.ofFloat(help1, "translationY", "translationX", path);
                animation.setStartDelay(500);
                animation.setDuration(1000);
                animation.start();

                animation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        help1.setBackgroundResource(R.drawable.animation_drag);
                        animationDrawable = (AnimationDrawable) help1.getBackground();
                        animationDrawable.start();
                        button_aiuto.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });



            }
        });

        findViewById(R.id.framecapelli).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(fcapelli){
                    ArrayList parole = new ArrayList();
                    parole.add("Capelli");
                    parole.add("Cheveux");
                    parole.add("Hair");
                    SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(0);

                }

                return true;
            }
        });

        findViewById(R.id.frameorecchio1).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(forecchie){
                    ArrayList parole = new ArrayList();
                    parole.add("Orecchie");
                    parole.add("Oreilles");
                    parole.add("Ears");
                    SpinnerAdapter adapter = new SpinnerAdapter(getApplicationContext(), parole);
                    spinner.setAdapter(adapter);
                    spinner.setSelection(0);

                }

                return true;
            }
        });

        findViewById(R.id.frameorecchio2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(forecchie){
                    ArrayList parole = new ArrayList();
                    parole.add("Orecchie");
                    parole.add("Oreilles");
                    parole.add("Ears");
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
                v.setBackgroundResource(0);
                break;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup
                View view = (View) event.getLocalState(); //immagine // View v = framelayout in cui è droppato
                ViewGroup owner = (ViewGroup) view.getParent();

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

                            ImageView img = (ImageView) view;
                            img.setOnClickListener(ActivityViso.this);
                            img.performClick();

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

                            ImageView img = (ImageView) view;
                            img.setOnClickListener(ActivityViso.this);
                            img.performClick();

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

                            img.setOnClickListener(ActivityViso.this);
                            img.performClick();
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

                            img.setOnClickListener(ActivityViso.this);
                            img.performClick();

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

                            img.setOnClickListener(ActivityViso.this);
                            img.performClick();
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

                            img.setOnClickListener(ActivityViso.this);
                            img.performClick();
                        }
                        break;
                }
            case DragEvent.ACTION_DRAG_ENDED:
                v.setBackgroundResource(0);
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onLongClick(View view) {

        ClipData data = ClipData.newPlainText(getResources().getResourceEntryName(view.getId()), "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
        view);
        view.startDrag(data, shadowBuilder, view, 0);
        //view.setVisibility(View.INVISIBLE);
        return true;

    }

    @Override
    public void onClick(View view) {

        ArrayList parole;
        SpinnerAdapter adapter;

        switch (view.getId()){
            case R.id.capelli:
                parole = new ArrayList();
                parole.add("Capelli"); parole.add("Cheveux"); parole.add("Hair");
                adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                break;
            case R.id.orecchie:
                parole = new ArrayList();
                parole.add("Orecchie"); parole.add("Oreilles"); parole.add("Ears");
                adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                break;
            case R.id.sopracciglia:
                parole = new ArrayList();
                parole.add("Sopracciglia"); parole.add("Sourcils"); parole.add("Eyebrows");
                adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                break;
            case R.id.occhi:
                parole = new ArrayList();
                parole.add("Occhi"); parole.add("Yeux"); parole.add("Eyes");
                adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                break;
            case R.id.naso:
                parole = new ArrayList();
                parole.add("Naso"); parole.add("Nez"); parole.add("Nose");
                adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                break;
            case R.id.bocca:
                parole = new ArrayList();
                parole.add("Bocca"); parole.add("Bouche"); parole.add("Mouth");
                adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                break;
        }

    }
}