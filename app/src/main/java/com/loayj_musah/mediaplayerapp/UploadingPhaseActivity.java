package com.loayj_musah.mediaplayerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UploadingPhaseActivity extends AppCompatActivity implements View.OnClickListener {

    TextView DbUpload,LinkUpload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploading_phase);
        DbUpload=findViewById(R.id.DbUploadId);
        DbUpload.setOnClickListener(this);
        LinkUpload=findViewById(R.id.LinkUploadId);
        LinkUpload.setOnClickListener(this);
    }
    public void upload_activity(){

        Intent intent = new Intent(this, UplaodSong.class);
        startActivity(intent);

    }
    public void Link_upload_activity(){

        Intent intent = new Intent(this, LinkUploadActivity.class);
        startActivity(intent);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.DbUploadId:
                upload_activity();


                break;
            case R.id.LinkUploadId:
                Link_upload_activity();

                break;



        }

    }
}
