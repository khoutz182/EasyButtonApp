package com.houtz.kevin.easybuttonapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.houtz.kevin.easybuttonapp.constants.Constants;
import com.houtz.kevin.easybuttonapp.MainActivity;
import com.houtz.kevin.easybuttonapp.R;
import com.houtz.kevin.easybuttonapp.listeners.EasyButtonClickListener;
import com.houtz.kevin.easybuttonapp.listeners.RecordingListener;
import com.houtz.kevin.easybuttonapp.utils.Utils;

import java.io.File;

import static android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

/**
 * Created by k.houtz on 4/1/2015.
 */
public class ScreenSlideFragmentEnd extends Fragment {

    private static final String TAG = "ScreenSlideFragmentEnd";
    private MediaRecorder mediaRecorder;
    private ImageButton customImageButton = null;
    private SharedPreferences prefs;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "HERE!");
        if(resultCode == getActivity().RESULT_OK && customImageButton != null) {
            Uri selectedImage = data.getData();
            SharedPreferences.Editor editor = prefs.edit();
            String realPath = Utils.getRealPathFromURI(this.getActivity(), selectedImage);
            editor.putString(Constants.DEFAULT_CUSTOM_IMAGE, realPath);
            editor.commit();
            customImageButton.setImageURI(selectedImage);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page_end, container, false
        );

        prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        String defaultImage = prefs.getString(Constants.DEFAULT_CUSTOM_IMAGE, null);

        ImageButton button = (ImageButton)rootView.findViewById(R.id.MyImageButton);
        button.setOnClickListener(new EasyButtonClickListener(-1));
        customImageButton = button;

        if(defaultImage != null) {
            File f = new File(defaultImage);
            if(f.exists() && f.isFile() && f.canRead()){
                button.setImageURI(Uri.parse(defaultImage));
            } else {
                Log.i(TAG, "custom image file does not exist! using default...");
            }
        }


        Button recordButton = (Button) rootView.findViewById(R.id.RecordButton);
        RecordingListener listener = new RecordingListener();
        listener.setMainActivity((MainActivity)getActivity());
        recordButton.setOnTouchListener(listener);

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
