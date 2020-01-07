package com.loayj_musah.mediaplayerapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MusicPlayer extends AppCompatActivity {
    SeekBar seekBar;
    Handler handler;
    ImageView play;
    int possition;
    TextView Songname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_music_player);
        seekBar= (SeekBar)findViewById(R.id.seekBar);
        Songname=findViewById(R.id.songNameId);
        play=findViewById(R.id.play);
        Intent intent = getIntent();

        int max= intent.getIntExtra("max",0);
         possition= intent.getIntExtra("possition",0);
         String name=intent.getStringExtra("name");
        play.setImageResource(R.drawable.ic_play_circle_filled);
         boolean start=intent.getBooleanExtra("play",false);

         if(start)
             play.setImageResource(R.drawable.ic_pause);


        Songname.setText(name);
        Toast.makeText(this, ""+start, Toast.LENGTH_SHORT).show();
        seekBar.setMax(max);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }


        };
    }
    public void oncycle(){
        seekBar.setProgress(possition);

    }
}
