package com.example.tesi;

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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityMano extends AppCompatActivity implements View.OnDragListener, View.OnLongClickListener, View.OnClickListener {

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

    private FrameLayout button_aiuto;
    private ImageView help1;
    private AnimationDrawable animationDrawable = null;

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

        findViewById(R.id.polso).setOnLongClickListener(this);
        findViewById(R.id.palmo).setOnLongClickListener(this);
        findViewById(R.id.pollice).setOnLongClickListener(this);
        findViewById(R.id.indice).setOnLongClickListener(this);
        findViewById(R.id.medio).setOnLongClickListener(this);
        findViewById(R.id.anulare).setOnLongClickListener(this);
        findViewById(R.id.mignolo).setOnLongClickListener(this);

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
                path.lineTo(-430f, 300f);
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

        findViewById(R.id.framepolso).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(fpolso){
                    ArrayList parole = new ArrayList();
                    parole.add("Polso");
                    parole.add("Poignet");
                    parole.add("Wrist");
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
                    parole.add("Palmo");
                    parole.add("Paume");
                    parole.add("Palm");
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

                            ImageView img = (ImageView) view;
                            img.setOnClickListener(ActivityMano.this);
                            img.performClick();

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


                            ImageView img = (ImageView) view;
                            img.setOnClickListener(ActivityMano.this);
                            img.performClick();
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

                            img.setOnClickListener(ActivityMano.this);
                            img.performClick();

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

                            img.setOnClickListener(ActivityMano.this);
                            img.performClick();

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

                            img.setOnClickListener(ActivityMano.this);
                            img.performClick();
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

                            img.setOnClickListener(ActivityMano.this);
                            img.performClick();

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

                            img.setOnClickListener(ActivityMano.this);
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
            case R.id.polso:
                parole = new ArrayList();
                parole.add("Polso"); parole.add("Poignet"); parole.add("Wrist");
                adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                break;
            case R.id.palmo:
                parole = new ArrayList();
                parole.add("Palmo"); parole.add("Paume"); parole.add("Palm");
                adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                break;
            case R.id.pollice:
                parole = new ArrayList();
                parole.add("Pollice"); parole.add("Pouce"); parole.add("Thumb");
                adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                break;
            case R.id.indice:
                parole = new ArrayList();
                parole.add("Indice"); parole.add("Index finger"); parole.add("Index");
                adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                break;
            case R.id.medio:
                parole = new ArrayList();
                parole.add("Medio"); parole.add("Majeur"); parole.add("Middle finger");
                adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                break;
            case R.id.anulare:
                parole = new ArrayList();
                parole.add("Anulare"); parole.add("Annulaire"); parole.add("Ring finger");
                adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                break;
            case R.id.mignolo:
                parole = new ArrayList();
                parole.add("Mignolo"); parole.add("Auriculaire"); parole.add("Little finger");
                adapter = new SpinnerAdapter(getApplicationContext(), parole);
                spinner.setAdapter(adapter);
                spinner.setSelection(0);
                break;
        }

    }

}