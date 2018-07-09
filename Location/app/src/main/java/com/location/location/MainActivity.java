package com.location.location;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    GpsInfo gps;
    double latitude;
    double longitude;
    TextView txt_location;
    TextView txt_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_location = (TextView)findViewById(R.id.location);
        txt_address = (TextView)findViewById(R.id.address);
        GPSsetting();
    }

    //GPS 설정
    public void GPSsetting() {
        gps = new GpsInfo(MainActivity.this);

        // GPS 사용유무 설정
        if (gps.isGetLocation()) {
            latitude = gps.getLatitude();   //위도 저장
            longitude = gps.getLongitude(); //경도 저장
            txt_location.setText("위도 : "+String.valueOf(latitude)+" / 경도 : "+String.valueOf(longitude));
            txt_address.setText("" + getAddress(latitude, longitude));    //주소 표시

            /*
            //가져온 좌표로 API 연결
            try {
                ae = new ApiExplorer(handler);  //스레드 생성
                ae.setLocation(Double.toString(latitude), Double.toString(longitude));   //좌표 설정
                ae.start(); //스레드 동작
            } catch (Exception e) {
                e.printStackTrace();
            }
            */
        } else {
            gps.showSettingAlert();
        }
    }

    //위도, 경도를 이용해 주소 받아오기
    public String getAddress(double lat, double lng) {
        String address = null;
        Geocoder geocoder = new Geocoder(this, Locale.KOREAN);
        List<Address> list = null;
        try {
            list = geocoder.getFromLocation(lat, lng, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list.size() > 0) {
            Address addr = list.get(0);
            address = addr.getCountryName() + " "
                    + addr.getPostalCode() + " "
                    + addr.getLocality() + " "
                    + addr.getFeatureName();
        }
        return address;
    }
}

