package com.loayj_musah.mediaplayerapp;


import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;

public class MusicRecever extends IntentService
    {
        public static final String PARAM_IN_MSG = "imsg";
        MediaPlayer mediaPlayer;


        public MusicRecever() {
            super("MusicRecever");


        }


        @Override
        protected void onHandleIntent(@Nullable Intent intent) {
            Bundle bundle = intent.getExtras();
            String url = bundle.getString("url");



             mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.start();
              //  MusicPlay();
            } catch (IOException e) {
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
                e.printStackTrace();

            }


        }

//        public void MusicPlay() {
//
//
//            Intent intent = new Intent(this, MusicPlayer.class);
//            int max =mediaPlayer.getDuration();
//            intent.putExtra("max",max);
//            startActivity(intent);
//            Toast.makeText(this, "MusicActivity", Toast.LENGTH_SHORT).show();
//
//        }
    }

