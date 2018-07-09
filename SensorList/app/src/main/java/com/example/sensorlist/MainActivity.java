package com.example.sensorlist;

import java.util.*;

import android.app.*;
import android.content.*;
import android.hardware.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.os.Build;

public class MainActivity extends ListActivity {

	
	//로그에 출력할 때 사용할 태그
	public static final String TAG = "MainActivity";
	
	//센서를 가져올 센서 매니저 변수
	SensorManager manager = null;
	
	//모든 센서의 목록을 저장할 배열
	List<Sensor> sensors = null;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		manager = (SensorManager)getSystemService(SENSOR_SERVICE);
		
		sensors = manager.getSensorList(Sensor.TYPE_ALL);
		
		
		
		//가속도 센서의 존재 여부 확인
		
		//아이폰은 기기의 명칭만 알면 센서의 존재 여부를 확인 가능함.
		//아이폰은 단일 모델이기 때문에 가능함.
		//안드로이드는 단일 모델이 아니기 때문에 센서를 사용할 때는 항상 센서의
		//존재유무를 코드상에서 확인해야 한다.
		//동일모델에서도 하드웨어 존재 여부가 다른경우도 있다.
		//NFC 관련된 기능을 구현할 때도 반드시 먼저 NFC 존재여부를 확인해야한다 
		//센서 관련 API는 제조사 developer 사이트에서 확인도 해야한다.
		//연락처 API나 센서API는 디바이스 제조회사에서 변경하는 경우도 있음.
		//대표정인 경우는 안드로이드 표준 API는 삼성제품에서 사용할 수 없음.
		int i;
		for(i=0; i<sensors.size(); i++){
			Sensor sensor = sensors.get(i);
			//센서의 이름 가져오기
			String name =sensor.getName();
			if(name.contains("Accel")) //Accel이 포함되어있으면 탈출 ㄱㄱ
				break;
		}
		if(i == sensors.size()){
			Toast.makeText(this, "No Sensor", Toast.LENGTH_LONG).show();
		}
		else{
			Toast.makeText(this, "Sensor is exist", Toast.LENGTH_LONG).show();
		}
		
		
		
		//위의 내용을 어댑터로 설정.
		SensorListAdapter adapter = new SensorListAdapter(this, R.layout.listitem, sensors);
		
		//리스트 뷰에 adapter 설정.
		setListAdapter(adapter);
		
		 
	}
	
	//리스트 뷰에서 셀을 선택했을 때 호출되는 이벤트 핸들러 작성
	//SensorDataActivity 화면을 출력하고 이 때 SENSOR_INDEX라는 키로 선택한 셀의
	//인덱스를 넘겨준다.
	@Override
	//첫번째 매개변수는 클릭한 리스트 뷰 (activity_main의 ListView)
	//두번째 매개변수는 클릭한 항목 뷰(listitem.xml의 뷰)
	//세번쨰 매개변수는 선택한 항목의 위치
	//네번째 매개변수는 선택한 항목의 아이디 - getItemId()의 return값.
    protected void onListItemClick(ListView l, View v, int position, long id){
       super.onListItemClick(l, v, position, id);
       
       //새로운 액티비티를 화면에 출력하기 위해서 Intent 생성
       Intent intent = new Intent(this, SensorDataActivity.class);
       //위의 intent에 데이터 넘기기
       intent.putExtra(SensorDataActivity.SENSOR_INDEX, position);
			startActivity(intent); //출력
		
	}
			
			
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	

}
