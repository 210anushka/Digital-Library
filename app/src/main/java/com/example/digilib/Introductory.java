package com.example.digilib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class Introductory extends AppCompatActivity {
    ImageView splashimg;
    TextView appname;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(Introductory.this,option.class);
                startActivity(i);

            }
        },5000);
        setContentView(R.layout.activity_introductory);

        appname=findViewById(R.id.tagline);
        lottieAnimationView=findViewById(R.id.lottie);

        lottieAnimationView.animate().translationX(1600).setDuration(1000).setStartDelay(4000);



    }
}