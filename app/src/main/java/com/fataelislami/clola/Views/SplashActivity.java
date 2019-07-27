package com.fataelislami.clola.Views;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.fataelislami.clola.MainActivity;
import com.fataelislami.clola.R;

public class SplashActivity extends AppCompatActivity {
    private int loadTime=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // membuat transparan notifikasi
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            );
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //setelah loading maka akan langsung berpindah ke home activity
                Intent i=new Intent(getApplication(), LoginActivity.class);
                startActivity(i);
                finish();

            }
        },loadTime);
    }
}
