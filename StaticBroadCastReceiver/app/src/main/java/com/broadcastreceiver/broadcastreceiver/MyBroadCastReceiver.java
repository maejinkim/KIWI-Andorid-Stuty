package com.broadcastreceiver.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
public class MyBroadCastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String actionName = intent.getAction();
        Toast.makeText(context, "받은 액션 : "+actionName , Toast.LENGTH_SHORT).show();

    }
}
