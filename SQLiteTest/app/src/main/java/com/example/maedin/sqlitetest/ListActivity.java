package com.example.maedin.sqlitetest;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView listView;
    private Button btn_delete;
    private Button btn_update;
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    MyDB helper;
    SQLiteDatabase db;

    String update;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        //변수 초기화
        listView = (ListView) findViewById(R.id.List_view);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_update = (Button) findViewById(R.id.btn_update);

        helper = new MyDB(ListActivity.this, "MyDB", null,1);
        db = helper.getWritableDatabase();

        items = new ArrayList<>();
        adapter = new ArrayAdapter(ListActivity.this, android.R.layout.simple_list_item_single_choice, items);

        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

        //어뎁터 할당
        listView.setAdapter(adapter);
        viewList();
    }


    private void viewList()
    {
        items.clear();
        String sql = "select content from MYTABLE";
        Cursor cursor = db.rawQuery(sql, null);
        int count = 0;
        if (cursor != null)
        {
            count = cursor.getCount();
            for (int i = 0; i < count; i++)
            {
                cursor.moveToNext();
                String content = cursor.getString(0);
                items.add(content);
            }
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }

    private void updateList()
    {
        final LinearLayout update_layout = (LinearLayout) View.inflate(this, R.layout.update_layout,null);
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);

        dlg.setTitle("UPDATE").setView(update_layout).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edit = (EditText) update_layout.findViewById(R.id.txt_update);
                        helper.update(db, edit.getText().toString(), update);
                        Toast.makeText(ListActivity.this, update+" -> "+edit.getText().toString(),Toast.LENGTH_SHORT).show();
                        viewList();
                    }
                });
        dlg.setNegativeButton("Cancel",null);
        dlg.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_delete:
            {
                String checked = adapter.getItem(listView.getCheckedItemPosition());
                helper.delete(db,checked);
                Toast.makeText(ListActivity.this, checked+" DELETE",Toast.LENGTH_SHORT).show();
                listView.clearChoices();
                viewList();
                break;
            }
            case R.id.btn_update:
            {
                update = adapter.getItem(listView.getCheckedItemPosition());
                updateList();
                break;
            }
        }
    }
}
