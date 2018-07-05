package com.example.maedin.scheduletest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class MainActivity extends AppCompatActivity {

    Button btn_set;
    Button btn_cancel;
    long period = 1000 * 2;
    int num = 0;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Alarm","onDestroy()");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        final Intent intent = new Intent(MainActivity.this, BroadCast.class);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

        btn_set = (Button) findViewById(R.id.btn_set);
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+period, period, pendingIntent);
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+period, period, pendingIntent);


                Log.d("Alarm","SET click");

            }
        });

        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                alarmManager.cancel(pendingIntent);
                Log.d("Alarm","CANCEL click");
            }
        });


    }
}
