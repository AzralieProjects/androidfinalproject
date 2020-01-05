package com.loayj_musah.mediaplayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.SeekBar;
import android.widget.Toast;

public class MusicPlayer extends AppCompatActivity {
    SeekBar seekBar;
    Handler handler;
    int possition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        seekBar=findViewById(R.id.seekBar);
        Intent intent = getIntent();

        int max= intent.getIntExtra("max",0);
         possition= intent.getIntExtra("possition",0);
        Toast.makeText(this, ""+max, Toast.LENGTH_SHORT).show();
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


    }
    public void oncycle(){
        seekBar.setProgress(possition);

    }
}
