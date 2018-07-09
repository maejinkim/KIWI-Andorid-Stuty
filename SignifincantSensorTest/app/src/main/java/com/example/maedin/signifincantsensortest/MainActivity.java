package com.example.maedin.signifincantsensortest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TriggerListener mTriggerEventListener;

    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);

        text = (TextView) findViewById(R.id.text);

        if(mSensor != null)
            text.setText("센서 감지");
        else
            text.setText("센서 감지X");


        mTriggerEventListener = new TriggerListener();
        mSensorManager.requestTriggerSensor(mTriggerEventListener, mSensor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.requestTriggerSensor(mTriggerEventListener, mSensor);
    }

    @Override
    protected void onPause() {
        super.onPause();
        text.setText("돌아왔다!");
        mSensorManager.requestTriggerSensor(mTriggerEventListener, mSensor);
    }

    private class TriggerListener extends TriggerEventListener
    {
        @Override
        public void onTrigger(TriggerEvent event) {

                Toast.makeText(MainActivity.this, "모션감지",Toast.LENGTH_SHORT).show();
                //text.setText(event.values.toString());
                text.setText("getName : "+event.sensor.getName()+"\n"+"getVendor : "+event.sensor.getVendor()+"\n"
                                + "getMaximumRange : "+ event.sensor.getMaximumRange());
                Log.d("Motion","detect");
            mSensorManager.requestTriggerSensor(mTriggerEventListener, mSensor);

        }
    }
}
