package com.example.maedin.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_start;
    Button btn_stop;
    TextView txt_count;
    private boolean running;
    private IConterService binder;

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //Service의 binder 전달 -> 구체화한 getCount전달
            binder = IConterService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        txt_count = (TextView) findViewById(R.id.txt_count);

        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start) {
            bindService(new Intent(MainActivity.this, CountService.class), connection, BIND_AUTO_CREATE);
            running = true;
            new Thread(new GetCountThread()).start();

        }
        else if (v.getId() == R.id.btn_stop) {
            unbindService(connection);
            running = false;
        }
    }

    private class GetCountThread implements Runnable{

        //count를 가져오기 위한 핸들러
        private Handler handler = new Handler();

        @Override
        public void run() {

            while (running)
            {
                if (binder == null)
                    continue;

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            txt_count.setText(binder.getConunt()+"");
                        }catch (RemoteException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });

                try {
                    Thread.sleep(500);
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
