package com.example.sensorlist;

import java.util.*;

import android.app.*;
import android.content.*;
import android.hardware.*;
import android.os.*;
import android.widget.*;

public class SensorDataActivity extends Activity implements SensorEventListener {

     //이 액티비티를 호출하는 액티비티에서 데이터 저장에 사용하는 키 값 변수
     //안드로이드나 스프링에서는 데이터 전달에 맵 구조를 이용합니다.
     public static final String SENSOR_INDEX = "SensorIndex";
     
     //센서 정보를 가져올 수 있는 변수
     SensorManager manager = null;
     //모든 센서의 정보를 저장할 배열
     List<Sensor>sensors = null;
     
     //이전 액티비티에서 누른 센서의 인덱스(셀의 인덱스)를 저장할 변수
     int sensorIndex = 0;
     //센서의 이름을 저장할 변수
     String sensorName = null;
     
     //Data.xml에 있는 텍스트 뷰를 저장할 변수
     TextView txtSensorName = null;
     TextView txtSensorAccuracy = null;
     TextView txtSensorValues = null;
     
     //액티비티 클래스에서는 onCreate를 재정의해서 상위 클래스의 메서드를 호출하고
     //자신의 뷰를 설정해야 합니다.
     @Override
     public void onCreate(Bundle savedInstanceState){
          super.onCreate(savedInstanceState);
          //자신의 뷰 설정
          setContentView(R.layout.data);
          
          //텍스트 뷰 가져오기
          txtSensorName = (TextView)findViewById(R.id.txtSensorName);
          txtSensorAccuracy = (TextView)findViewById(R.id.txtSensorAccuracy);
          txtSensorValues = (TextView)findViewById(R.id.txtSensorValues);
          
          //모든 센서의 정보를 sensors에 저장
          manager = (SensorManager)getSystemService(SENSOR_SERVICE);
          //매개변수 센서의 정보를 sensors에 저장
          sensors = manager.getSensorList(Sensor.TYPE_ALL);
          
          //현재 액티비티의 인텐트 가져오기
          Intent passedIntent = getIntent();
          if(passedIntent != null){
               //이전 인텐트에서 전달해온 데이터 가져오기
               //안드로이드에서는 화면 전환에 액티비티를 사용하지 않고 액티비티를 포함하는
               //인텐트를 사용합니다.
               //인텐트에서 데이터를 전달하고 할 때는 putExtra(키값, 실제 데이터)
               //안드로이드에서는 맵 대신에 Extra를 사용하고
               //웹 프로그래밍에서 parameter나 attribute를 사용
               //간혹 안드로이드에서는 ContentValues를 사용하기도 합니다.
               //위 4개는 전부 맵 구조
               sensorIndex = passedIntent.getIntExtra(SENSOR_INDEX, 0);
               sensorName = sensors.get(sensorIndex).getName();
               txtSensorName.setText(sensorName);
          }
          
     }
     
     //액티비티가 활성화 되어서 화면에 출력되었을 때 호출되는 메서드
     @Override
     protected void onResume(){
          super.onResume();
          
          //센서매니저의 이벤트 핸들러 연결
          //센서의 정밀도나 센서의 값이 변경될 때 호출될 메서드의 위치를 설정
          manager.registerListener(this, sensors.get(sensorIndex),
                    SensorManager.SENSOR_DELAY_UI);
     }
     //액티비티가 중지되었을 때 호출되는 메서드
     @Override
     protected void onStop(){
          super.onStop();
          //센서의 이벤트 핸들러 연결 해제
          //하지 않으면 화면에서 액티비티가 제거되더라도 센서의 값이 변경될 때 메서드를 호출하게 되서
          //자원의 낭비를 가져오게 됩니다.
          //센서는 애플리케이션의 소유가 아니고 운영체제의 소유이기 때문에 해제를 하지 않으면
          //계속 작업을 진행합니다.
          //위치정보와 블루투스가 가장 유의해야 하는 하드웨어 입니다.
          //GPS와 블루투스가 배터리 소모량이 가장 많습니다.
          manager.unregisterListener(this);
     }
     
     //센서의 값이 변경되었을 때 호출되는 메서드
     @Override
     public void onSensorChanged(SensorEvent event) {
          //센서의 값이 변경된 시간을 문자열로 생성
          String data = "Sensor Timestamp:" + event.timestamp +"\n\n";
          //센서의 값은 event.values 하게되면 float 배열로 리턴해줍니다.
          for(int i=0; i<event.values.length; i++){
               float value = Float.parseFloat(String.format("%.2f",event.values[i]));
               data += ("Sensor Value #" + (i+1) + ":" + value + "\n");
          }
          //텍스트 뷰에 data를 출력
          txtSensorValues.setText(data);
     }

     //센서의 정밀도가 변경되었을 때 호출되는 메서드
     @Override
     public void onAccuracyChanged(Sensor sensor, int accuracy) {
          txtSensorAccuracy.setText("Sensor Accuracy:" + 
     getAccuracyString(accuracy));
     }
     
     //사용자 정의 메서드 - 정밀도를 매개변수로 받아서 문자열을 리턴해주는 메서드
     private String getAccuracyString(int accuracy){
          String accuracyString = "";
          switch(accuracy){
          case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
               accuracyString = "High";
               break;
          case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
               accuracyString = "Low";
               break;
          case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
               accuracyString = "Medium";
               break;
          case SensorManager.SENSOR_STATUS_UNRELIABLE:
               accuracyString = "UNRELIABLE";
               break;
          default:
               accuracyString = "Unknown";
               break;
          }
          return accuracyString;
     }

}











