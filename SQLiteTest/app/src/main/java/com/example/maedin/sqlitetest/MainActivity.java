package com.example.maedin.sqlitetest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_add;
    Button btn_list;
    EditText edit_content;

    MyDB helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_add = (Button) findViewById(R.id.btn_add);
        edit_content = (EditText) findViewById(R.id.content);
        btn_list = (Button) findViewById(R.id.btn_list);

        helper = new MyDB(MainActivity.this, "MyDB", null,1);
        db = helper.getWritableDatabase();

        btn_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                helper.insert(db, edit_content.getText().toString());
                Toast.makeText(MainActivity.this, edit_content.getText()+" ADD", Toast.LENGTH_SHORT).show();
                edit_content.setText("");
            }
        });
        btn_list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });


    }


}
