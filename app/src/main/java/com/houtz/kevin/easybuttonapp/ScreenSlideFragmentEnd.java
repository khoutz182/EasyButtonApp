package com.houtz.kevin.easybuttonapp;


import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.houtz.kevin.easybuttonapp.listeners.EasyButtonClickListener;
import com.houtz.kevin.easybuttonapp.listeners.RecordingListener;

import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

/**
 * Created by k.houtz on 4/1/2015.
 */
public class ScreenSlideFragmentEnd extends Fragment {

    private static final String TAG = "ScreenSlideFragmentEnd";
    private MediaRecorder mediaRecorder;
    private ImageButton customImageButton = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "HERE!");
        if(resultCode == getActivity().RESULT_OK && customImageButton != null) {
            Uri selectedImage = data.getData();
            customImageButton.setImageURI(selectedImage);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page_end, container, false
        );

        ImageButton button = (ImageButton)rootView.findViewById(R.id.MyImageButton);
        button.setOnClickListener(new EasyButtonClickListener(-1));
        customImageButton = button;

        Button recordButton = (Button) rootView.findViewById(R.id.RecordButton);
        recordButton.setOnTouchListener(new RecordingListener());

        Button imagePickButton = (Button) rootView.findViewById(R.id.ChangeImageButton);
        imagePickButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);
            }
        });

        return rootView;
    }
}
