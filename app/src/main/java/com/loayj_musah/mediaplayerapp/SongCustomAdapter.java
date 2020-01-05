package com.loayj_musah.mediaplayerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.loayj_musah.mediaplayerapp.Song;

import java.util.ArrayList;

public class SongCustomAdapter extends ArrayAdapter<Song>  {



    public SongCustomAdapter(Context context, ArrayList<Song> song) {
        super(context, 0,song);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.songs, parent, false);
        }

        Song song = getItem(position);


//

        TextView Name = (TextView) convertView.findViewById(R.id.name);
        Name.setText(song.getName());

        return convertView;
    }
}

