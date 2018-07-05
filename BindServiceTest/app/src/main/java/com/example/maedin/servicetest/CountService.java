package com.example.maedin.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class CountService extends Service {

    private boolean isStop;
    private int count;


    public CountService() {
    }

    //통신을 위한 바인더 객체, getCount메소드를 제공해 값 전달
    IConterService.Stub binder = new IConterService.Stub() {

        @Override
        public int getConunt() throws RemoteException {
            return count;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        //Thread를 이용하여 Counter 실행
        //서비스가 개별적인 작업을 하는경우 스레드를 생성해서 사용하는 것이 좋음.
        //기본 스레드를 사용자 상호작용 전용으로 사용가능하고, ANR오류를 줄일 수 있음.
        Thread counter = new Thread(new Counter());
        counter.start();
    }

    //StopService 실행시 호출
    @Override
    public void onDestroy() {
        super.onDestroy();
        isStop = true;
        Log.d("Count", "Stop");
    }

    //바인드 구현 - 사용하지 않을때는 null반환
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        isStop = true;
        return super.onUnbind(intent);
    }

    private class Counter implements Runnable
    {
        private Handler handler = new Handler();

        @Override
        public void run() {

            for (count = 0; count < 10; count++)
            {
                if (isStop) //stop클릭시 종료
                    break;



                //Thread에서는 UI와 관련된 Toast를 못쓰기때문에 Handler를 통해 사용
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                     Toast.makeText(getApplicationContext(), count +"", Toast.LENGTH_SHORT).show();
                     Log.d("Count", Integer.toString(count));
                    }
                });

                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "서비스 종료", Toast.LENGTH_SHORT).show();
                    Log.d("Count", "Stop Complete");
                }
            });

        }
    }


}
