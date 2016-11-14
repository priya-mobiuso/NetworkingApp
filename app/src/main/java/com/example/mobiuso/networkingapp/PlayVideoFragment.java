package com.example.mobiuso.networkingapp;

import java.io.File;
import java.lang.*;

import android.os.Environment;
import android.view.View;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;


public class PlayVideoFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_play_video, parent, false);
        VideoView videoView =(VideoView)v.findViewById(R.id.videoView1);
        videoView.setVideoPath(Environment.getExternalStorageDirectory().toString() + File.separator + "lab1201.mp4");
        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()  {
            @Override
            public void onPrepared(MediaPlayer mp){
                    mp.start();
                }
            });
        return v;
    }

}



















