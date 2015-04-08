package com.houtz.kevin.easybuttonapp.listeners;

import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import com.houtz.kevin.easybuttonapp.Constants;
import com.houtz.kevin.easybuttonapp.R;

/**
 * Created by k.houtz on 4/1/2015.
 */
public class EasyButtonClickListener implements View.OnClickListener {

    private int pageNum;

    public EasyButtonClickListener(int pageNum) {
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
        } else if(pageNum == -1) {
            // play recording
            mp = MediaPlayer.create(v.getContext(), Uri.parse(Constants.mainAudioFileName));
        } else {
            mp = MediaPlayer.create(v.getContext(), R.raw.that_was_easy);
        }

        if(mp != null) {
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
}
