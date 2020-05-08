package com.armax7.OS_4_AMIGOS_STAND_UP_COMEDY;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Contém os elementos da tela em questão. Faz o carregamento uma única vez e pode ser usado a qualquer momento dentro da classe.
    private ViewHolder mViewHolder = new ViewHolder();
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mViewHolder.mBottomNav = findViewById(R.id.bottomNav);

        //se não tiver nada salvo ele seta o fragmento nulo para o inicial(comediantesFragment).
        if (savedInstanceState == null){
            this.mViewHolder.mBottomNav.setItemSelected(R.id.navPiadas, true);
            this.mViewHolder.mFragmentManager = getSupportFragmentManager();
            comediantesFragment comediantesFragment = new comediantesFragment();
            this.mViewHolder.mFragmentManager.beginTransaction().replace(R.id.fragment_container, comediantesFragment).commit();
        }

        /* seta o fragmento conforme (item)selecionado no bottomNav. */
        this.mViewHolder.mBottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Fragment fragment = null;
                switch (id){
                    case R.id.navPiadas:
                        fragment = new comediantesFragment();
                        break;
                    case R.id.navStickers:
                        fragment = new figurinhasFragment();
                        break;
                    case R.id.navSobre:
                        fragment = new sobreFragment();
                        break;
                }
                if (fragment != null){
                    mViewHolder.mFragmentManager = getSupportFragmentManager();
                    mViewHolder.mFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
                }else {
                    Toast.makeText(MainActivity.this, "Erro ao criar um fragmento. \n Por favor reinicie o aplicatvo.", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "erro ao criar um fragmento");
                }
            }
        });
    }
    /**para sair do aplicativo é preciso ser precionado 2 vezes seguidas o botão de voltar.**/
    @Override
    public void onBackPressed() {
        // para previnir saídas inesperadas.
        long t = System.currentTimeMillis();
        // 2 segundos para sair.
        if (t - this.mViewHolder.backPressedTime > 2000) {
            this.mViewHolder.backPressedTime = t;
            Toast.makeText(this, "clique 2 vezes para sair",Toast.LENGTH_SHORT).show();
        } else {// se pressionado novamente encerrar app.
            // limpa.
            super.onBackPressed();
        }
    }
    /**metodo que serve para declarar,
     * com a diferença de que o findViewById só chama os itens uma vez,
     * com isso á um ganho de performce.
     * **/
    private static class ViewHolder {
        private long backPressedTime = 0; // usado por onBackPressed();.
        private ChipNavigationBar mBottomNav;
        private FragmentManager mFragmentManager;
    }
}
