package com.armax7.OS_4_AMIGOS_STAND_UP_COMEDY;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.Objects;

public class sobre_Os_4amigos extends AppCompatActivity {
    // Contém os elementos da tela em questão. Faz o carregamento uma única vez e pode ser usado a qualquer momento dentro da classe.
    private ViewHolder mViewHolder = new ViewHolder();
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_os_4amigos);

        this.mViewHolder.collapsingToolbarLayout4A = findViewById(R.id.collapsing_toolbar4A);
        this.mViewHolder.collapsingToolbarLayout4A.setTitle(getTitle());

        //se tirar esta linha a cor do titulo por padrão fica branco(impossivel alterar para outra cor).
        this.mViewHolder.collapsingToolbarLayout4A.setExpandedTitleColor(R.color.azulDark4A);
        this.mViewHolder.toolbar4A = findViewById(R.id.toolbar4A);
        setUpToolbar();
    }
    //metodo que implementa botão(seta) de voltar para o pai dela na hierarquia declarada no manifest.
    protected void setUpToolbar() {
        if(this.mViewHolder.toolbar4A != null){
            //seta um suporte de actionBar para toolBar.
            setSupportActionBar(this.mViewHolder.toolbar4A);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            // USE(substitua) em caso de erro! com o código acima - getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    private static class ViewHolder {
        private Toolbar toolbar4A;
        private CollapsingToolbarLayout collapsingToolbarLayout4A;
    }
}
