package com.example.christian.ocr_c;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.christian.ocr_c.SimpleGestureFilter.SimpleGestureListener;

import java.util.Locale;


public class MainActivity extends Activity implements SimpleGestureListener {

    private SimpleGestureFilter detector;
    private TextToSpeech tts;
    Locale locSpanish = new Locale("spa", "MEX");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Detect touched area
        detector = new SimpleGestureFilter(this,this);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status){
                if(status != TextToSpeech.ERROR){
                   tts.setLanguage(locSpanish);
                }
            }
        });
    }

    public void onPause(){
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    public void speakText(String text){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }

    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT :
                str = "Abriendo camara";
                speakText(str);
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startCameraActivity();
                break;
            case SimpleGestureFilter.SWIPE_LEFT :
                str = "Abriendo configuracion ";
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                speakText(str);
                break;
            case SimpleGestureFilter.SWIPE_DOWN :
                str = "Doble tap para confirmar la salida";
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                speakText(str);
                break;
            case SimpleGestureFilter.SWIPE_UP :
                str = "Abriendo biblioteca";
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                speakText(str);
                break;

        }
    }

    @Override
    public void onDoubleTap() {
        Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
    }

    public void startCameraActivity(){
         startActivity(new Intent(this,CameraActivity.class));
        // startActivity(new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE ));
    }
}
