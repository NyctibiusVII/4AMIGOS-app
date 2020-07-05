package com.armax7.OS_4_AMIGOS_STAND_UP_COMEDY;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.chibde.visualizer.LineVisualizer;

import java.io.IOException;
import java.util.Objects;

import static android.media.session.PlaybackState.ACTION_PLAY;
import static com.instabug.library.Instabug.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class figurinhasFragment extends Fragment {
    private ViewHolder mViewHolder = new ViewHolder();
    private MediaPlayer mediaPlayer;
    private int IdMedia = 0;
    @SuppressLint({"SourceLockedOrientationActivity", "LongLogTag"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_figurinhas, container, false);
        Objects.requireNonNull(getActivity()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mediaPlayer = new MediaPlayer();
//        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);

//        this.mViewHolder.lineVisualizer = rootView.findViewById(R.id.visualizer);
//        this.mViewHolder.lineVisualizer.setColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.azulq1));
//        // defini a linha com para o visualizador entre 1 e 10 padrão 1
//        this.mViewHolder.lineVisualizer.setStrokeWidth(3);
//        // Defini o media player para o visualizador
//        this.mViewHolder.lineVisualizer.setPlayer(mediaPlayer.getAudioSessionId());

        //18 audios
        this.mViewHolder.bt01 = rootView.findViewById(R.id.btA01);
        this.mViewHolder.bt02 = rootView.findViewById(R.id.btA02);
        this.mViewHolder.bt03 = rootView.findViewById(R.id.btA03);
        this.mViewHolder.bt04 = rootView.findViewById(R.id.btA04);
        this.mViewHolder.bt05 = rootView.findViewById(R.id.btA05);
        this.mViewHolder.bt06 = rootView.findViewById(R.id.btA06);
        this.mViewHolder.bt07 = rootView.findViewById(R.id.btA07);
        this.mViewHolder.bt08 = rootView.findViewById(R.id.btA08);
        this.mViewHolder.bt09 = rootView.findViewById(R.id.btA09);
        this.mViewHolder.bt10 = rootView.findViewById(R.id.btA10);
        this.mViewHolder.bt11 = rootView.findViewById(R.id.btA11);
        this.mViewHolder.bt12 = rootView.findViewById(R.id.btA12);
        this.mViewHolder.bt13 = rootView.findViewById(R.id.btA13);
        this.mViewHolder.bt14 = rootView.findViewById(R.id.btA14);
        this.mViewHolder.bt15 = rootView.findViewById(R.id.btA15);
        this.mViewHolder.bt16 = rootView.findViewById(R.id.btA16);
        this.mViewHolder.bt17 = rootView.findViewById(R.id.btA17);
        this.mViewHolder.bt18 = rootView.findViewById(R.id.btA18);

        this.mViewHolder.bt01.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {
                    executeBtn1();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.mViewHolder.bt02.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {
                    executeBtn2();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.mViewHolder.bt03.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {
                    executeBtn3();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        //CardView
        this.mViewHolder.cardFig = rootView.findViewById(R.id.cardFig);
        this.mViewHolder.cardFig.setBackgroundResource(R.drawable.card_btn);

        this.mViewHolder.cardFig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EntryActivity.class));
            }
        });

        //rootView
        if (((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).hide();
        }
        return rootView;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) mediaPlayer.release();
    }
    public void executeBtn1() throws IOException {
        if (IdMedia == 0){
            IdMedia = 1;
        }
        if(mediaPlayer == null){//INICIA audio
            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.audio1);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();

            // mViewHolder.bt01.getResources().getDrawable(R.drawable.c_pausa);
            // MUDAR PARA c_pausa
        }else {
            if(mediaPlayer.isPlaying()){
                //se clicar novamente(MESMO button) e estiver tocando ele PARA, LIMPA e seta para NULL
                if(IdMedia == 1){
                    clear();
                    IdMedia = 0;
                    // MUDAR PARA c_audiotrack
                }else{
                    //se clicar novamente(OUTRO button) e estiver tocando(OUTRO button) ele PARA, LIMPA, seta para NULL e execulta o audio do botão clicado
                    clear();
                    IdMedia = 1;
                    executeBtn1();
                }
            }
        }
    }
    public void executeBtn2() throws IOException {
        if (IdMedia == 0){
            IdMedia = 2;
        }
        if(mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.audio2);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        }else {
            if(mediaPlayer.isPlaying()){
                if(IdMedia == 2){
                    clear();
                    IdMedia = 0;
                }else{
                    clear();
                    IdMedia = 2;
                    executeBtn2();
                }
            }
        }
    }
    public void executeBtn3() throws IOException {
        if (IdMedia == 0){
            IdMedia = 3;
        }
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.audio3);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        }else {
            if(mediaPlayer.isPlaying()){
                if(IdMedia == 3){
                    clear();
                    IdMedia = 0;
                }else{
                    clear();
                    IdMedia = 3;
                    executeBtn3();
                }
            }
        }
    }

    public void clear(){
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    private static class ViewHolder {
        private LineVisualizer lineVisualizer;
        private CardView cardFig;
        Button  bt01, bt02, bt03, bt04,
                bt05, bt06, bt07, bt08,
                bt09, bt10, bt11, bt12,
                bt13, bt14, bt15, bt16,
                bt17, bt18;
    }
}
