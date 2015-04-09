package com.houtz.kevin.easybuttonapp.listeners;

import android.media.MediaRecorder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.houtz.kevin.easybuttonapp.constants.Constants;
import com.houtz.kevin.easybuttonapp.MainActivity;
import com.houtz.kevin.easybuttonapp.R;

import java.io.File;

/**
 * Created by k.houtz on 4/8/2015.
 */
public class RecordingListener implements View.OnTouchListener {

    private MainActivity mainActivity;
    private MediaRecorder mediaRecorder;
    private static final String TAG = "MyRecordListener";

    private float firstX = -10.0f;
    private float firstY = -10.0f;
    private final static float MAX_DIFF = 75.0f;

    private void startRecording() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(Constants.tempAudioFileName);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setAudioEncodingBitRate(16);
        mediaRecorder.setAudioSamplingRate(44100);

        Button recordButton = (Button) mainActivity.findViewById(R.id.RecordButton);
        recordButton.setPressed(true);
        recordButton.setText("Recording...");

        try {
            mediaRecorder.prepare();
        } catch (Exception e) {
            Log.i(TAG, "prepare() failed");
        }

        mediaRecorder.start();
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
            } catch (RuntimeException stopException) {
                //Log.i(TAG, "StopException thrown", stopException);
            }
            mediaRecorder.release();
            mediaRecorder = null;
        }

        Button recordButton = (Button) mainActivity.findViewById(R.id.RecordButton);
        recordButton.setPressed(false);
        recordButton.setText("Record");
    }

    private void saveRecording() {
        File file = new File(Constants.tempAudioFileName);
        if (file == null || !file.exists() || !file.isFile()) {
            return;
        }
        File fileDest = new File(Constants.mainAudioFileName);
        boolean success = file.renameTo(fileDest);
        Log.i(TAG, "success: " + success);
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //start recording
            Log.i(TAG, "Start Recording");
            startRecording();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //stop recording
            Log.i(TAG, "Stop Recording");
            stopRecording();
            Log.i(TAG, "Save recording");
            saveRecording();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_CANCEL) {
            // cancel the recording and use the old one (don't overwrite)
            Log.i(TAG, "Stop Recording and don't save");

            if(firstX == -10.0f && firstY == -10.0f) { //this is terrible
                firstX = event.getX();
                firstY = event.getY();
            }

            if(Math.abs(firstX - event.getX()) > MAX_DIFF || Math.abs(firstY - event.getY()) > MAX_DIFF) {
                stopRecording();
            }
        }
        return true;
    }
}
