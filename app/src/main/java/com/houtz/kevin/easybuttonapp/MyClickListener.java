package com.houtz.kevin.easybuttonapp;

import android.media.MediaPlayer;
import android.view.View;

public class MyClickListener implements View.OnClickListener {

    private int pageNum;

    public MyClickListener(int pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public void onClick(View v) {
        MediaPlayer mp;

        if(pageNum == 0) {
            mp = MediaPlayer.create(v.getContext(), R.raw.that_was_easy);
        } else if(pageNum == 1) {
            mp = MediaPlayer.create(v.getContext(), R.raw.that_was_hard);
        } else if(pageNum == 2) {
            mp = MediaPlayer.create(v.getContext(), R.raw.thats_what_she_said);
        } else {
            mp = MediaPlayer.create(v.getContext(), R.raw.that_was_easy);
        }

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                mp = null;
            }
        });

        mp.start();
    }
}
