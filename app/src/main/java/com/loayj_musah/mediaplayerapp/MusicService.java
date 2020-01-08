package com.loayj_musah.mediaplayerapp;


import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

public class MusicService extends Service
    {

        MediaPlayer mediaPlayer;
        boolean isRunning;





        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            mediaPlayer = new MediaPlayer();
        }
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            if (!mediaPlayer.isPlaying()) {
                startMedia(intent);
            }
            return START_STICKY;
        }

        public void onDestroy() {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
        public void startMedia(Intent intent) {
            Bundle bundle = intent.getExtras();
            String url = bundle.getString("url");




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

