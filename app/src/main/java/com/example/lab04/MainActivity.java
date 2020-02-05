package com.example.lab04;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {


    private CameraManager mCameraManager;
    private String mCameraId;

    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {
            showNoFlashError();
        }


        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        toggleButton = findViewById(R.id.toggleButton);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchFlashLight(isChecked);
            }
        });
    }

    public void showNoFlashError() {
        AlertDialog alert = new AlertDialog.Builder(this)
                .create();
        alert.setTitle("Oops!");
        alert.setMessage("Flash not available in this device...");
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alert.show();
    }

    public void switchFlashLight(boolean status) {
        try {
            mCameraManager.setTorchMode(mCameraId, status);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void runAutoFormat(View view) {


        String paragraph1 = "We're taking action against evil people. Because this great nation of many religions understands, our war is not against Islam, or against faith practiced by the Muslim people. Our war is a war against evil. This is clearly a case of good versus evil, and make no mistake about it - good will prevail.";
        StringBuffer paragraph2 = new StringBuffer();
        StringTokenizer st = new StringTokenizer(paragraph1, " .,", true);
        TextView tv = findViewById(R.id.textView);
        HashMap<String, String>  words = new HashMap<String, String>();

        words.put("evil", "cat");
        words.put("war", "party");
        words.put("good", "dogs");
        words.put("Islam", "animals");
        words.put("Muslim", "furry");

        while (st.hasMoreTokens()) {
            String nextWord = st.nextToken();
            Log.d("lab4_log1", nextWord);

            if (words.containsKey(nextWord)) {
                paragraph2.append(words.get(nextWord));
            } else {
                paragraph2.append(nextWord);
                tv.setText(paragraph2.toString());
            }
        }


    }



}