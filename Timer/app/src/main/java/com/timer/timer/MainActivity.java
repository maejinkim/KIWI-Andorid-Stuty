package com.timer.timer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    static int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), String.valueOf(counter), Toast.LENGTH_SHORT).show();
                        Log.e("1번태스크카운터:", String.valueOf(counter));
                        counter++;
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(tt, 0, 3000);   // 1. TimerTask : 주기적으로 하고 싶은 일
                                                    // 2. int : 처음에 몇 초를 기다렸다가 실행할까?
                                                    // 3. int : 얼마마다 한번씩 실행할까?
    }
}
