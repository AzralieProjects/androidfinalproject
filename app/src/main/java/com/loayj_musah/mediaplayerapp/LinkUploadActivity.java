package com.loayj_musah.mediaplayerapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LinkUploadActivity extends AppCompatActivity implements View.OnClickListener{

    String name,url;
    EditText Name,URL;
    Button UploadBtn;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_upload);
        Name=findViewById(R.id.SongnameId);
        URL=findViewById(R.id.UrlNameId);
        UploadBtn=findViewById(R.id.UploadBtnId);
        UploadBtn.setOnClickListener(this);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("songs");

    }

        public void insert(){
            name = Name.getText().toString();
            url = URL.getText().toString();
            Name.setText("");
            URL.setText("");
            if (name.equals("") || url.equals("")) {
                Toast.makeText(this, " Please fill the empty space", Toast.LENGTH_SHORT).show();
                return;
            }
        if(Check_url_if_Correct()) {

            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

            Song song=new Song(name,url,currentDate,null);
            String UploadId=databaseReference.push().getKey();
            databaseReference.child(UploadId).setValue(song);
            Toast.makeText(this, "inserted", Toast.LENGTH_SHORT).show();
        }
    }
    public Boolean Check_url_if_Correct()  {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();

            if (mediaPlayer.isPlaying()) {
                //stop or pause your media player mediaPlayer.stop(); or mediaPlayer.pause();
                mediaPlayer.stop();
                mediaPlayer.release();
                return true;

            } else {
                Toast.makeText(this, "Url Is no Available", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.UploadBtnId:
                insert();

              break;
        }
    }
}
