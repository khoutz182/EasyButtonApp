package com.houtz.kevin.easybuttonapp;


import android.media.Image;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by k.houtz on 4/1/2015.
 */
public class ScreenSlideFragment extends Fragment {

    private static final String TAG = "ScreenSlideFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int pageNum = 0;
        try {
            pageNum = ((Integer)getArguments().get("PAGE_NUM")).intValue();
        } catch(Exception e) {}

        ViewGroup rootView = (ViewGroup) inflater.inflate(
            R.layout.fragment_screen_slide_page, container, false
        );

        ImageButton button = (ImageButton)rootView.findViewById(R.id.MyImageButton);
        button.setOnClickListener(new MyClickListener(pageNum));
        switch(pageNum) {
            case 0: button.setImageResource(R.drawable.easy_full);
                break;
            case 1: button.setImageResource(R.drawable.hard_full);
                break;
            case 2: button.setImageResource(R.drawable.she_said_full);
                break;
            default: button.setImageResource(R.drawable.easy_full);
                break;
        }

        if(pageNum == 3) {
            button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.i(TAG, "event: " + event.toString());
                    if(event.getAction() == MotionEvent.ACTION_DOWN) {
                        //start recording
                        Log.i(TAG, "Action down");
                    } else if(event.getAction() == MotionEvent.ACTION_UP) {
                        //stop recording
                        Log.i(TAG, "Action up");
                    }

                    return true;
                }
            });
        }

        return rootView;
    }
}
