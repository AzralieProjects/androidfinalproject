package com.loayj_musah.mediaplayerapp;


import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;

public class MusicService extends IntentService
    {

        MediaPlayer mediaPlayer;


        public MusicService() {
            super("MusicService");


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
            } catch (IOException e) {
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
    }

