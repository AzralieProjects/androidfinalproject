package com.loayj_musah.mediaplayerapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class UplaodSong extends AppCompatActivity implements View.OnClickListener  {
EditText title;
TextView textViewImg;
ProgressBar progressBar;
Uri audioUri;
private Button get_fileUpload ,uploadSong;
public StorageReference mStorageref;
StorageTask mUploadTask;
DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_uplaod_song);
        title=findViewById(R.id.SongTitleId);
        textViewImg=findViewById(R.id.FileSelctedID);
        progressBar=findViewById(R.id.ProgressbarID);
        get_fileUpload=findViewById(R.id.getFileUploadID);
        uploadSong=findViewById(R.id.UploadID);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        mStorageref= FirebaseStorage.getInstance().getReference().child("songs");
        get_fileUpload.setOnClickListener(this);
        uploadSong.setOnClickListener(this);
    }

    public void get_file(){
        Intent intent_upload = new Intent();
        intent_upload.setType("audio/*");
        intent_upload.setAction(Intent.ACTION_GET_CONTENT);
        intent_upload.getAction();
        startActivityForResult(intent_upload,1);

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&data.getData()!=null){
audioUri=data.getData();
String fileName=getFileName(audioUri);
            Log.d("myTag", fileName);

textViewImg.setText(fileName);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getFileName(Uri audioUri) {
        String result=null;
        if(audioUri.getScheme().equals("content")){
            Cursor cursor =getContentResolver().query(audioUri,null,null,null);
            try{
                if(cursor!=null&&cursor.moveToFirst()){
                    result=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }finally {
                cursor.close();
            }

        }
        if(result==null) {
            result = audioUri.getPath();
            int cut=result.lastIndexOf('/');
            if(cut!=-1){
                result=result.substring(cut+1);
            }
        }
        return result;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void UploadToFirebase(View v){
        if(textViewImg.getText().toString().equals("No file selected")){
            Toast.makeText(this, "Please Select Song", Toast.LENGTH_SHORT).show();
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                UploadFile();
            }
        }

    }


    public void UploadFile() {
        if(audioUri!=null){
            String duration;
            Toast.makeText(this, "Uploading Please Wait...", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(textViewImg.VISIBLE);
                final StorageReference storageReference=mStorageref.child(title.getText()+"."+getFileExtention(audioUri));
mUploadTask=storageReference.putFile(audioUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
    @Override
    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String url=uri.toString();
                Song song=new Song(title.getText().toString(),url,currentDate,audioUri);
                String UploadId=databaseReference.push().getKey();
                databaseReference.child("songs").child(UploadId).setValue(song);
            }
        });


    }
}).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
    @Override
    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
        double progress=(100.0)*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount();
        progressBar.setProgress((int)progress);
    }
});

        }
        else{
            Toast.makeText(this, "No file Selected to upload", Toast.LENGTH_SHORT).show();
        }

        
    }

    private String getFileExtention(Uri audioUri) {

        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(audioUri));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(View v) {



        switch (v.getId()) {
            case R.id.getFileUploadID:
                get_file();
                break;


            case R.id.UploadID:

                    UploadFile();



        }

    }

}
