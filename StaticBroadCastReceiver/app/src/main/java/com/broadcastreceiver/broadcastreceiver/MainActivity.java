package com.broadcastreceiver.broadcastreceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button1);
        button.setOnClickListener(mClickListener);
    }

    Button.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
<<<<<<< HEAD
            Intent intent = new Intent("StaticBroadCast!!");
            intent.putExtra("value", "Confirm");
            sendBroadcast(intent);
=======
            sendBroadcast(new Intent("button_click"));
>>>>>>> 4a8aa23ace29daaa1e9b2100f3d372c340785226
        }
    };
}
