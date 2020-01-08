package com.loayj_musah.mediaplayerapp;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MusicPlayer extends AppCompatActivity implements  View.OnClickListener{
    SeekBar seekBar;
    String url;
    ImageView play;
    boolean buttonPressed = false;
    int possition;
    TextView Songname;
    private IntentFilter intentFilter;
    private MusicRecever musicRecever;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_music_player);
        intentFilter = new IntentFilter(Intent.ACTION_CALL_BUTTON);
        seekBar= (SeekBar)findViewById(R.id.seekBar);
        Songname=findViewById(R.id.songNameId);
        musicRecever=new MusicRecever();
        play=findViewById(R.id.play);
        play.setOnClickListener(this);
        Intent intent = getIntent();
        int max= intent.getIntExtra("max",0);
         possition= intent.getIntExtra("possition",0);
         String name=intent.getStringExtra("name");
         url=intent.getStringExtra("url");
        play.setImageResource(R.drawable.ic_play_circle_filled);
        Songname.setText(name);
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
        if(isMyServiceRunning(MusicService.class)){

            intent.putExtra("playing",true);
            play.setImageResource(R.drawable.ic_pause);
            buttonPressed=!buttonPressed;
        }
    }
    public void oncycle(){
        seekBar.setProgress(possition);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                startBroadCasting();

              break;


        }
    }
    public void startBroadCasting(){
        buttonPressed=!buttonPressed;

        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MusicService.class);

        if(buttonPressed){
            intent.putExtra("url",url);
            play.setImageResource(R.drawable.ic_pause);
            startService(intent);
        }
        else{
                play.setImageResource(R.drawable.ic_play_circle_filled);

            stopService(intent);

        }




    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
