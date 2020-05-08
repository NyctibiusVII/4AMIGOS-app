package com.armax7.OS_4_AMIGOS_STAND_UP_COMEDY;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class sobre_oAmigo_Activity extends AppCompatActivity implements View.OnClickListener {
    // Contém os elementos da tela em questão. Faz o carregamento uma única vez e pode ser usado a qualquer momento dentro da classe.
    private ViewHolder mViewHolder = new ViewHolder();

    private AlertDialog
            alertYout,
            alertInsta;

    int
            id1Amigo_ItemList;

    private static final String
            mailto = "mailto",
            typeComp = "text/plain",
            email = "matheus.dev.07@gmail.com",
            title_email = "Report Erro - 4AMIGOS",
            titleIntentFeedback = "Reportar";

    NumberFormatException cacheErroInItemMenu;
    Exception cacheErroException;
    Integer cacheErroNumberSurprise;
    String cacheErroNumberSurpriseComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_o_amigo);

        this.mViewHolder.fundo = findViewById(R.id.img_fundo);
        this.mViewHolder.titulo = findViewById(R.id.txt_title_sobre_MASTER);
        this.mViewHolder.descricao = findViewById(R.id.txt_desc_sobre_MASTER);

        this.mViewHolder.insta = findViewById(R.id.btn_seguir_insta);
        this.mViewHolder.yout = findViewById(R.id.btn_seguir_youtube);
        this.mViewHolder.insta.setOnClickListener(this);
        this.mViewHolder.yout.setOnClickListener(this);

        this.mViewHolder.toolbarThi = findViewById(R.id.toolbarThi);

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        id1Amigo_ItemList = extras.getInt("idX");

        try {/*id1Amigo_ItemList = Integer.parseInt("injetando erro");*/

            //de acordo com os id's, o layout é setado para o item que foi selecionado.
            if (id1Amigo_ItemList == 0) {
                //thiago
                this.mViewHolder.fundo.setImageResource(R.drawable.w_th_va);
                this.mViewHolder.titulo.setText(R.string.thi);
                this.mViewHolder.descricao.setText(R.string.thiagoVenturaD);
                this.mViewHolder.insta.setText(R.string.thi_insta);
                this.mViewHolder.yout.setText(R.string.thi_yout);
            }else if (id1Amigo_ItemList == 1){
                //afonso
                this.mViewHolder.fundo.setImageResource(R.drawable.w_af_va);
                this.mViewHolder.titulo.setText(R.string.afo);
                this.mViewHolder.descricao.setText(R.string.afonsoPadilhaD);
                this.mViewHolder.insta.setText(R.string.afo_insta);
                this.mViewHolder.yout.setText(R.string.afo_yout);
            }else if (id1Amigo_ItemList == 2){
                //dihh
                this.mViewHolder.fundo.setImageResource(R.drawable.w_di_va);
                this.mViewHolder.titulo.setText(R.string.dih);
                this.mViewHolder.descricao.setText(R.string.dihhLopesD);
                this.mViewHolder.insta.setText(R.string.dih_insta);
                this.mViewHolder.yout.setText(R.string.dih_yout);
            }else if (id1Amigo_ItemList == 3){
                //márcio
                this.mViewHolder.fundo.setImageResource(R.drawable.w_ma_va);
                this.mViewHolder.titulo.setText(R.string.mar);
                this.mViewHolder.descricao.setText(R.string.marcioDonatoD);
                this.mViewHolder.insta.setText(R.string.mar_insta);
                this.mViewHolder.yout.setText(R.string.mar_yout);
            }else{
                final String TAG = "errorNumberSurprise";
                final String surpriseComments = "Unexpected number appeared in the variable 'id1Amigo_ItemList'";
                Log.e(TAG, surpriseComments);
                Log.e(TAG, String.valueOf(id1Amigo_ItemList));
                cacheErroNumberSurprise = id1Amigo_ItemList;
                cacheErroNumberSurpriseComments = surpriseComments;
                irErrorInItemMenu();
            }
        } catch(NumberFormatException nfe) {
            final String TAG = "errorInItemMenu";
            Log.e(TAG, String.valueOf(nfe));
            cacheErroInItemMenu = nfe;
            irErrorInItemMenu();
        }catch (Exception e){
            final String TAG = "errorExeption";
            Log.e(TAG, String.valueOf(e));
            cacheErroException = e;
            irErrorInItemMenu();
        }

        setUpToolbar();
    }
    //metodo que implementa botão(seta) de voltar para o pai dela na hierarquia declarada no manifest.
    protected void setUpToolbar() {
        if(this.mViewHolder.toolbarThi != null){
            //seta um suporte de actionBar para toolBar.
            setSupportActionBar(this.mViewHolder.toolbarThi);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            // USE(substitua) em caso de erro! com o código acima - getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        //de acordo com os id's, o LINK é setado para o item que foi selecionado.
        if (view.getId() == R.id.btn_seguir_insta){
            abrirInstagram();
        }else if (view.getId() == R.id.btn_seguir_youtube){
            abrirYoutube();
        }
    }

    private void irErrorInItemMenu(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setCanceledOnTouchOutside(false);
        builder
                .setCancelable(false)
                .setTitle("ERRO! indexItemMenu")
                .setMessage(String.valueOf(cacheErroInItemMenu)
                        .concat(String.valueOf(cacheErroException)
                                .concat(cacheErroNumberSurpriseComments)
                                        .concat(String.valueOf(cacheErroNumberSurprise))))
        .setPositiveButton("REPORTAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                abrirErrorInItemMenu();
                finishAffinity();
            }
        })
        .setNegativeButton("SAIR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                finishAffinity();
            }
        });
        AlertDialog alertErrorInItemMenu = builder.create();
        alertErrorInItemMenu.show();
    }
    private void irInstaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Abrir o instagram ?");
        builder.setMessage("Deseja sair do app e abrir o instagram ?");
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                abrirInstagram();
            }
        });
        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                alertInsta.dismiss();
            }
        });
        alertInsta = builder.create();
        alertInsta.show();
    }
    private void irYoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Abrir o youtube ?");
        builder.setMessage("Deseja sair do app e abrir o youtube ?");
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                abrirYoutube();
            }
        });
        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                alertYout.dismiss();
            }
        });
        alertYout = builder.create();
        alertYout.show();
    }

    private void abrirErrorInItemMenu() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri
                .fromParts(mailto,email, null))
                .putExtra(Intent.EXTRA_SUBJECT,title_email)
                .putExtra(Intent.EXTRA_TEXT, String.valueOf(cacheErroInItemMenu)
                        .concat(String.valueOf(cacheErroException)
                        .concat(cacheErroNumberSurpriseComments)
                        .concat(String.valueOf(cacheErroNumberSurprise))));
        startActivity(Intent.createChooser(emailIntent, titleIntentFeedback));
    }
    private void abrirInstagram(){
        if (!mViewHolder.url_insta_thi.startsWith("http://") && !mViewHolder.url_insta_thi.startsWith("https://")){
            mViewHolder.url_insta_thi = "http://" + mViewHolder.url_insta_thi;
        }
        if (!mViewHolder.url_insta_afo.startsWith("http://") && !mViewHolder.url_insta_afo.startsWith("https://")){
            mViewHolder.url_insta_afo = "http://" + mViewHolder.url_insta_afo;
        }
        if (!mViewHolder.url_insta_dih.startsWith("http://") && !mViewHolder.url_insta_dih.startsWith("https://")){
            mViewHolder.url_insta_dih = "http://" + mViewHolder.url_insta_dih;
        }
        if (!mViewHolder.url_insta_mar.startsWith("http://") && !mViewHolder.url_insta_mar.startsWith("https://")){
            mViewHolder.url_insta_mar = "http://" + mViewHolder.url_insta_mar;
        }

        if (id1Amigo_ItemList == 0) {
            //thiago
            Intent siteIntent = new Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse(mViewHolder.url_insta_thi));
            try {
                startActivity(siteIntent);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mViewHolder.url_insta_thi)));
            }
        }else if (id1Amigo_ItemList == 1){
            //afonso
            Intent siteIntent = new Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse(mViewHolder.url_insta_afo));
            try {
                startActivity(siteIntent);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mViewHolder.url_insta_afo)));
            }
        }else if (id1Amigo_ItemList == 2){
            //dihh
            Intent siteIntent = new Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse(mViewHolder.url_insta_dih));
            try {
                startActivity(siteIntent);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mViewHolder.url_insta_dih)));
            }
        }else{
            //márcio
            Intent siteIntent = new Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse(mViewHolder.url_insta_mar));
            try {
                startActivity(siteIntent);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mViewHolder.url_insta_mar)));
            }
        }
    }
    private void abrirYoutube(){
        if (!mViewHolder.url_yout_thi.startsWith("http://") && !mViewHolder.url_yout_thi.startsWith("https://")){
            mViewHolder.url_yout_thi = "http://" + mViewHolder.url_yout_thi;
        }
        if (!mViewHolder.url_yout_afo.startsWith("http://") && !mViewHolder.url_yout_afo.startsWith("https://")){
            mViewHolder.url_yout_afo = "http://" + mViewHolder.url_yout_afo;
        }
        if (!mViewHolder.url_yout_dih.startsWith("http://") && !mViewHolder.url_yout_dih.startsWith("https://")){
            mViewHolder.url_yout_dih = "http://" + mViewHolder.url_yout_dih;
        }
        if (!mViewHolder.url_yout_mar.startsWith("http://") && !mViewHolder.url_yout_mar.startsWith("https://")){
            mViewHolder.url_yout_mar = "http://" + mViewHolder.url_yout_mar;
        }

        if (id1Amigo_ItemList == 0) {
            //thiago
            Intent siteIntent = new Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse(mViewHolder.url_yout_thi));
            siteIntent.setPackage("com.instagram.android");
            try {
                startActivity(siteIntent);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mViewHolder.url_yout_thi)));
            }
        }else if (id1Amigo_ItemList == 1){
            //afonso
            Intent siteIntent = new Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse(mViewHolder.url_yout_afo));
            siteIntent.setPackage("com.instagram.android");
            try {
                startActivity(siteIntent);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mViewHolder.url_yout_afo)));
            }
        }else if (id1Amigo_ItemList == 2){
            //dihh
            Intent siteIntent = new Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse(mViewHolder.url_yout_dih));
            siteIntent.setPackage("com.instagram.android");
            try {
                startActivity(siteIntent);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mViewHolder.url_yout_dih)));
            }
        }else{
            //márcio
            Intent siteIntent = new Intent(Intent.ACTION_VIEW)
                    .setData(Uri.parse(mViewHolder.url_yout_mar));
            siteIntent.setPackage("com.instagram.android");
            try {
                startActivity(siteIntent);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mViewHolder.url_yout_mar)));
            }
        }
    }

    private static class ViewHolder {
        private ImageView fundo;
        private TextView titulo, descricao;
        private Button insta, yout;
        private Toolbar toolbarThi;
        private String
                url_insta_thi = "https://www.instagram.com/othiagoventura/",
                url_insta_afo = "https://www.instagram.com/oafonsopadilha/",
                url_insta_dih = "https://www.instagram.com/odihhlopes/",
                url_insta_mar = "https://www.instagram.com/omarciodonato/",
                url_yout_thi = "https://www.youtube.com/user/thiagosouzapires",
                url_yout_afo = "https://www.youtube.com/channel/UCCqoc2as2nMEXoZPlwXei4g/featured",
                url_yout_dih = "https://www.youtube.com/user/diegosnpp",
                url_yout_mar = "https://www.youtube.com/channel/UCJlqrFCL8L26mh6rXXs_E0A";
    }
}
