package com.loayj_musah.mediaplayerapp;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    private Button Insert;
    private ImageView Btn1ID,uploadBtn;
    private EditText Name, URL;
    private ListView listView;
    private ArrayList<Song> arrayList;
    private SongCustomAdapter arrayAdapter;
    private int index;
    boolean playedB =false;
    private ArrayList keys;
    FirebaseStorage mStorage;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    int played=-1;
    String name ;
    String url;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Btn1ID=findViewById(R.id.Btn1Id);
        uploadBtn=findViewById(R.id.UploadID1);
        listView = findViewById(R.id.SongsListViewId);
        Btn1ID.setEnabled(false);
        Btn1ID.setVisibility(View.INVISIBLE);
        serviceIntent = new Intent(this, MusicService.class);
        arrayList = new ArrayList<>();
        keys=new ArrayList<String>();
        arrayAdapter = new SongCustomAdapter(this, arrayList);
        listView.setAdapter(arrayAdapter);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("songs");
        valueEventListener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot dss:dataSnapshot.getChildren()){
                    keys.add(dss.getKey());
                    Song song=dss.getValue(Song.class);

                    arrayList.add(song);
                    arrayAdapter.notifyDataSetChanged();
                }
                Btn1ID.setEnabled(true);
                Btn1ID.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "database error", Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()


        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                startService(position);
                played=position;

            }



        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                DeleteFromDB(pos);

                return true;
            }
        });


        Btn1ID.setOnClickListener(this);
        uploadBtn.setOnClickListener(this);

    }

    private void DeleteFromDB(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Delete this song?\n ")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("songs").child(keys.get(pos).toString());
                        dR.removeValue();
                        dR. removeValue();


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    public void startService(int position)
    {
        index=position;
        Intent serviceIntent = new Intent(this, MusicService.class);


       String url=arrayList.get(position).getUrl();
       serviceIntent.putExtra("url",url);
        MusicPlay();
       if(isMyServiceRunning(MusicService.class))
           stopService(serviceIntent);
        startService(serviceIntent);

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

    public void MusicPlay() {
        Intent intent = new Intent(MainActivity.this, MusicPlayer.class);

        intent.putExtra("name",arrayList.get(index).getName());
        intent.putExtra("url",arrayList.get(index).getUrl());
        intent.putExtra("arraylist", arrayList);
        intent.putExtra("index", index);
        if(playedB)
        intent.putExtra("play",true);

        startActivity(intent);
        Toast.makeText(this, "MusicActivity", Toast.LENGTH_SHORT).show();

    }
    public void PhaseUpload(){

        Intent intent = new Intent(MainActivity.this, UploadingPhaseActivity.class);
        startActivity(intent);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(valueEventListener);
    }
//    public void insert(){
//        name = Name.getText().toString();
//        url= URL.getText().toString();
//        Name.setText("");
//        URL.setText("");
//        if(name.equals("")||url.equals("")){
//            Toast.makeText(this, " Please fill the empty space", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
//
//        arrayAdapter.notifyDataSetChanged();
//        Toast.makeText(this, "inserted", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.Btn1Id:
                MusicPlay();
                break;
            case R.id.UploadID1:
                PhaseUpload();
                break;
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.menu1:
                AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
                alertbox.setTitle("About the app");
                alertbox.setMessage("This app is a media player app.\nAll the media is connected to the firebase cloud so it requires internet co" +
                        "nnectivity.\nMade By Loay & Musa ©.");

                // add a neutral button to the alert box and assign a click listener
                alertbox.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {

                    // click listener on the alert box
                    public void onClick(DialogInterface arg0, int arg1) {
                        // the button was clicked

                    }
                });
                alertbox.show();
                break;

            case R.id.menu2:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to exit the app?\n ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(isMyServiceRunning(MusicService.class))
                                    stopService(serviceIntent);
                                MainActivity.this.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                break;

            case R.id.menu3:
                MainActivity.this.finish();



        }
        return true;
    }


}
