package com.accelometersensor.accelometersensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    //Using the Accelometer & Gyroscoper
    private SensorManager mSensorManager = null;

    //Using the Accelometer
    private SensorEventListener mAccLis;
    private Sensor mAccelometerSensor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Using the Gyroscope & Accelometer
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Using the Accelometer
        mAccelometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccLis = new AccelometerListener();

        //Touch Listener for Accelometer
        findViewById(R.id.a_start).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mSensorManager.registerListener(mAccLis, mAccelometerSensor, SensorManager.SENSOR_DELAY_UI);
                        break;
                    case MotionEvent.ACTION_UP:
                        mSensorManager.unregisterListener(mAccLis);
                        break;
                }
                return false;
            }
        });
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.e("LOG", "onPause()");
        mSensorManager.unregisterListener(mAccLis);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.e("LOG", "onDestroy()");
        mSensorManager.unregisterListener(mAccLis);
    }

    private class AccelometerListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {
            double accX = event.values[0];
            double accY = event.values[1];
            double accZ = event.values[2];

            double angleXZ = Math.atan2(accX,  accZ) * 180/Math.PI;
            double angleYZ = Math.atan2(accY,  accZ) * 180/Math.PI;

            Log.e("LOG", "ACCELOMETER           [X]:" + String.format("%.4f", event.values[0])
                    + "           [Y]:" + String.format("%.4f", event.values[1])
                    + "           [Z]:" + String.format("%.4f", event.values[2])
                    + "           [angleXZ]: " + String.format("%.4f", angleXZ)
                    + "           [angleYZ]: " + String.format("%.4f", angleYZ));

        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }
}
