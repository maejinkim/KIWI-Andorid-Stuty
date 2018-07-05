package com.contentprovider.contentprovider;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView)findViewById(R.id.textView2);

        Cursor c =  getContentResolver().query(
                ContactsContract.CommonDataKinds
                        .Phone.CONTENT_URI,  // 조회할 컬럼명
                null, // 조회할 컬럼명
                null, // 조건 절
                null, // 조건절의 파라미터
                null);// 정렬 방향

        String str = ""; // 출력할 내용을 저장할 변수
        c.moveToFirst(); // 커서를 처음위치로 이동시킴
        do {
            String name = c.getString
                    (c.getColumnIndex(ContactsContract
                            .CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = c.getString
                    (c.getColumnIndex(ContactsContract
                            .CommonDataKinds.Phone.NUMBER));
            str += "이름 : " + name
                    +" 휴대폰번호 : " + phoneNumber + "\n";
        } while (c.moveToNext());//데이터가 없을 때까지반복
        tv.setText(str);
    } // end of onCreate
} // end of class