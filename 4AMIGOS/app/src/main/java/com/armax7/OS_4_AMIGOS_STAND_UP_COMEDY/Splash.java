package com.armax7.OS_4_AMIGOS_STAND_UP_COMEDY;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.logo_splash);

        //tira a nevigation bar e status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        //começa animação alpha da img logo.
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.img_alpha_splash);
        logo.startAnimation(myanim);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //chama o metodo para abrir outra tela.
                mostrarMainActivity();
            }
        }, 4000);//deley de 4 segundos para abrir outra tela.
    }
    /**abre a activity principal.**/
    private void mostrarMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**desaparece novamente o navBar e statusBar.**/
    @Override
    public void onBackPressed() {
        //tira a nevigation bar e status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
