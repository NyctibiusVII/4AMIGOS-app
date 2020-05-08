package com.armax7.OS_4_AMIGOS_STAND_UP_COMEDY;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class sobreFragment extends Fragment {
    private AlertDialog
            alertLoja,
            alertFeed;

    private static String
            url_loja = "https://lojaoficial4amigos.com.br/";

    private static final String
            link = " Da uma olhada nesse app! \n https://play.google.com/store/apps/details?id=com.armax7.OS_4_AMIGOS_STAND_UP_COMEDY",
            typeComp = "text/plain",
            titleIntentCompartilhar = "Compartilhar com:";

    private static final String
            mailto = "mailto",
            email = "matheus.dev.07@gmail.com",
            title_email = "FeedBack - 4AMIGOS",
            texto_corpo = "Escreva seu feed aqui:",
            titleIntentFeedback = "Enviar feedback";

    private List<String> listaNomes = new ArrayList<>(Arrays.asList(
            "Thiago Ventura",
            "Afonso Padilha",
            "Dihh Lopes",
            "Márcio Donato"));
//    private List<Integer> listaNomes = new ArrayList<>(Arrays.asList(R.string.thi, R.string.afo, R.string.dih, R.string.mar));

    private List<Integer> listaIcones = new ArrayList<>(Arrays.asList(
            R.drawable.w_th_perfil,
            R.drawable.w_af_perfil,
            R.drawable.w_di_perfil,
            R.drawable.w_ma_perfil));

    private List<String> listaDescricoes = new ArrayList<>(Arrays.asList(
            "mlk da quebrada!",
            "sem pai",
            "é casado",
            "comeu a puta"));

    private ConstraintLayout layout;

    public sobreFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_sobre, container, false);
        Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        layout = rootView.findViewById(R.id.myConst);

        Button
                btn_sobre_4_amigos = rootView.findViewById(R.id.btn_sobre_4_amigos),
                btn_loja = rootView.findViewById(R.id.btn_loja),
                btn_noAds = rootView.findViewById(R.id.btn_noAds),
                btn_feed = rootView.findViewById(R.id.btn_feed),
                btn_compartilhar = rootView.findViewById(R.id.btn_compartilhar);

        ListView
                minhaLista = rootView.findViewById(R.id.minhaLista);

        final MeuAdaptador meuAdaptador = new MeuAdaptador(Objects.requireNonNull(getContext()), R.layout.list_menu_sobre_4amigos);
        int i = 0;
        for(String nome : listaNomes){
            Dados4Amigos dados4amigos = new Dados4Amigos(listaIcones.get(i),nome, listaDescricoes.get(i));
            meuAdaptador.add(dados4amigos);
            i++;
        }

        minhaLista.setAdapter(meuAdaptador);
        minhaLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("Assert")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                 final String TAG = "itemMenu"; Log.i(TAG, String.valueOf(i));

                //bundle para passar os dados para a outra Activity
                Bundle b = new Bundle();
                int x;
                if (i == 0){
                    //thiago
                    x = 0;
                    int xx = Integer.parseInt(String.valueOf(x));
                    b.putInt("idX", xx);

                    Intent intent = new Intent(getActivity(), sobre_oAmigo_Activity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }else if (i == 1){
                    //afonso
                    x=1;
                    int xx = Integer.parseInt(String.valueOf(x));
                    b.putInt("idX", xx);

                    Intent intent = new Intent(getActivity(), sobre_oAmigo_Activity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }else if (i == 2){
                    //dihh
                    x=2;
                    int xx = Integer.parseInt(String.valueOf(x));
                    b.putInt("idX", xx);

                    Intent intent = new Intent(getActivity(), sobre_oAmigo_Activity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }else{
                    //márcio
                    x=3;
                    int xx = Integer.parseInt(String.valueOf(x));
                    b.putInt("idX", xx);

                    Intent intent = new Intent(getActivity(), sobre_oAmigo_Activity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });

        btn_sobre_4_amigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               abrirSobre4Amigos();
            }
        });
        btn_loja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    irLojaDialog();
            }
        });
        btn_noAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               abrirRemoveAds();
            }
        });
        btn_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irEmailFeedDialog();
            }
        });
        btn_compartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              abrirCompartilhamento();
            }
        });
        return rootView;
    }

    private void irLojaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Loja 4 Amigos");
        builder.setMessage("Tem certeza que deseja sair do app e abrir o site ?");
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
               abrirSite();
            }
        });
        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                alertLoja.dismiss();
            }
        });
        alertLoja = builder.create();
        alertLoja.show();
    }
    private void irEmailFeedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("FeedBack");
        builder.setMessage("Deseja abrir seu email ?");
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                abrirEmail();
            }
        });
        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                alertFeed.dismiss();
            }
        });
        alertFeed = builder.create();
        alertFeed.show();
    }

    private void abrirSobre4Amigos(){
        startActivity(new Intent(getActivity(), sobre_Os_4amigos.class));
    }
    private void abrirSite(){
        if (!url_loja.startsWith("http://") && !url_loja.startsWith("https://")){
            url_loja = "http://" + url_loja;
        }
        Intent siteIntent = new Intent(Intent.ACTION_VIEW)
                .setData(Uri.parse(url_loja));
        startActivity(siteIntent);
    }
    private void abrirEmail(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri
                .fromParts(mailto,email, null))
                .putExtra(Intent.EXTRA_SUBJECT,title_email)
                .putExtra(Intent.EXTRA_TEXT, texto_corpo);
        startActivity(Intent.createChooser(emailIntent, titleIntentFeedback));
    }
    private void abrirCompartilhamento(){
        Intent compIntent = new Intent(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, link)
                .setType(typeComp);
        startActivity(Intent.createChooser(compIntent, titleIntentCompartilhar));
    }
    private void abrirRemoveAds(){
        Snackbar.make(layout, R.string.noDisponible, Snackbar.LENGTH_LONG).show();
    }

}

class View4Amigos{
    ImageView icone;
    TextView titulo;
    TextView descricao;
}

class Dados4Amigos{
    private int icone;
    private String titulo;
    private String descricao;

    Dados4Amigos(int icone, String titulo, String descricao) {
        this.icone = icone;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    int getIcone() {
        return icone;
    }

    String getNome() {
        return titulo;
    }

    String getDescricao() {
        return descricao;
    }
}

class MeuAdaptador extends ArrayAdapter {

    MeuAdaptador(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View minhaView;
        minhaView = convertView;
        View4Amigos view4amigos;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            minhaView = inflater.inflate(R.layout.list_menu_sobre_4amigos,parent,false);

            view4amigos = new View4Amigos();
            view4amigos.icone =  minhaView.findViewById(R.id.meuIcone);
            view4amigos.titulo =  minhaView.findViewById(R.id.meuTitulo);
            view4amigos.descricao =  minhaView.findViewById(R.id.meuDescricao);

            minhaView.setTag(view4amigos);

        }else {
            view4amigos = (View4Amigos) minhaView.getTag();
        }

        Dados4Amigos dados4amigos;
        dados4amigos = (Dados4Amigos)this.getItem(position);

        assert dados4amigos != null;
        view4amigos.icone.setImageResource(dados4amigos.getIcone());
        view4amigos.titulo.setText(dados4amigos.getNome());
        view4amigos.descricao.setText(dados4amigos.getDescricao());

        return minhaView;
    }
}
