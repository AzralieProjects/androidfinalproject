package com.loayj_musah.mediaplayerapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener  {
    private Button Insert;
    private ImageButton Btn1ID,uploadBtn;
    private EditText Name, URL;
    private ListView listView;
    private ArrayList<Song> arrayList;
    private SongCustomAdapter arrayAdapter;
    private MediaPlayer player;
    int played=-1;
    String name ;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = findViewById(R.id.NameID);
        URL = findViewById(R.id.UrlID);
        Insert =findViewById(R.id.InsertID);
        Btn1ID=findViewById(R.id.Btn1Id);
        uploadBtn=findViewById(R.id.UploadID);
        listView = findViewById(R.id.SongsListViewId);
        arrayList = new ArrayList<>();
        arrayAdapter = new SongCustomAdapter(this, arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()


        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //if(played!=-1)
                //  stopService();

                startService();
                // MusicPlay();
                played=position;

            }



        });

        Insert.setOnClickListener(this);
        Btn1ID.setOnClickListener(this);
        uploadBtn.setOnClickListener(this);

    }


    public void startService()
    {
        Intent serviceIntent = new Intent(this, MusicRecever.class);

        startService(serviceIntent);



        // }

    }
    public void stopService(){
        stopService(new Intent(getApplicationContext(), MusicRecever.class));
    }

    public void MusicPlay() {


        Intent intent = new Intent(MainActivity.this, MusicPlayer.class);
        startActivity(intent);
        Toast.makeText(this, "MusicActivity", Toast.LENGTH_SHORT).show();

    }
    public void upload_activity(){

        Intent intent = new Intent(MainActivity.this, UplaodSong.class);
//      startActivity(intent);
        Toast.makeText(this, "upload activity", Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }

    public void insert(){

        name = Name.getText().toString();
        url= URL.getText().toString();
        Name.setText("");
        URL.setText("");
        if(name.equals("")||url.equals("")){
            Toast.makeText(this, " Please fill the empty space", Toast.LENGTH_SHORT).show();
            return;
        }
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());


        //arrayList.add(new Song(name,url,currentDate));
        arrayAdapter.notifyDataSetChanged();
        Toast.makeText(this, "inserted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.InsertID:
                insert();
                break;
            case R.id.Btn1Id:
                MusicPlay();
                break;
            case R.id.UploadID:
                upload_activity();
                break;


        }



    }
}
