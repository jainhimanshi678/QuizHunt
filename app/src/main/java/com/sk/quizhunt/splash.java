package com.sk.quizhunt;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;


public class splash extends AppCompatActivity {

   // private TextView appname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
     //   appname=findViewById(R.id.appname);
        //Typeface typeface= ResourcesCompat.getFont(this,R.)
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.myanim);
      //  appname.setAnimation(animation);
        new Thread()
        {
            public void run()
            {
                try {
                    sleep(3000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                Intent intent= new Intent(splash.this,Nameactivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
     /* */
    }
}