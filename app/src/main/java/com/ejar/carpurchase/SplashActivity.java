package com.ejar.carpurchase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;


public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.aty_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ImageView iv = (ImageView) findViewById(R.id.iv);
        AlphaAnimation anim = new AlphaAnimation(0.7f, 1f);
        anim.setDuration(500);
        anim.start();
        iv.setAnimation(anim);

            iv.postDelayed(() -> {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
                        finish();
                    }

                    , 1000);

    }


}
