package com.example.cardiio;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutUs extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitu_us);
        VideoView videoView = findViewById(R.id.videoView);
         String videoPath = "android.resource ://"+getPackageName()+"/"+R.raw.cardio;
         Uri uri = Uri.parse(videoPath);
         videoView.setVideoURI(uri);

        MediaController mediaController =new MediaController(AboutUs.this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);





    }
}

