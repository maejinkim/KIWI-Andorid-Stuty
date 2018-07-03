package com.kiwistudy.lifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class child extends AppCompatActivity {

    static final String TAG = "ActChild";

    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child);
    }

    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }

    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    public void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
