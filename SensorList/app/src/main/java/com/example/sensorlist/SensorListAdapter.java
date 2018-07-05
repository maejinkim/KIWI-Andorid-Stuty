package com.example.sensorlist;

import java.util.*;


import android.content.*;
import android.hardware.*;
import android.view.*;
import android.widget.*;

//리스트 뷰에 설정된 Adapter클래스.
//리스트 뷰에 출력될 모양을 직접 생성하는 경우 작성해야된다.
public class SensorListAdapter extends ArrayAdapter<Sensor> {

	//Context 변수 - MainActivity의 주소를 저장.
	private Context context = null;
	
	//출력에 사용할 데이터 배열.
	private List<Sensor> objects = null;
	
	
	//생성자.
	public SensorListAdapter(Context context, int viewid, List<Sensor>objects){
		 //상위 클래스에 디폴트 생성자가 없으므로 하위 클래스의 생성자에서
		 //반드시 호출
		 //출력에 사용할 Context(Activity), 두번째는 리스트 뷰에 출력할 뷰의 아이디, 세번째는 출력될 데이터.
		super(context, viewid, objects);
		
		//다른 메서드에서 사용할 수 있도록 멤버 변수에 저장.
		this.context = context;
		this.objects = objects;
	 }
	
	
	//리스트에 출력할 셀의 개수를 설정하는 메서드.
		@Override
		public int getCount(){
				if(objects == null)
					return 0;
				else
					return objects.size();
		}
	
	
	    //리스트에 출력된 각 뷰의 아이디를 설정하는 메서드
		@Override
		//position은 각 셀의 인덱스 이다.
		public long getItemId(int position){
			//itemId는 구분만 되면 되기 때문에 일반적으로 position으로 설정.
			return position;
		}
		
		
		//리스트에 출력된 각 셀에 저장된 값(item)을 설정하는 메서드.
		@Override
		//position은 각 셀의 인덱스이다.
		//오버라이딩에서 메서드의 리턴 타입은 아무런 상관 없다.
		//원래 이메서드의 리턴 타입은 Sensor가아니라 Object이다.(상위클래스에서)
		public Sensor getItem(int position){
			//여기서는 각 살에 저장될 데이터를 리턴하면 된다.
			//보통 배열에서 position 번쨰의 데이터를 가져와서 리턴한다.
				if(objects == null)
					return null;
				else
					return objects.get(position);
			
		}
		
		
		//리스트 뷰의 각 셀의 모양을 설정하는 메서드
		@Override
		//첫번째 매개변수는 각 셀의 인덱스.
		//두번째 매개변수는 셀의 모양으로 이 뷰를 없으면 생성해서 리턴하면된다ㅏ.
		//세번쨰 매개변수는 출력될 리스트 부.
		public View getView(int position, View convertView, ViewGroup parent){
			
			
			//리턴할 뷰 변수 선언.
			View itemView = null;
			
				if(convertView == null){
					//XML로 만들어진 레이아웃을 뷰로 전개할 수 있는 inflater 생성//상식//
					LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					
					itemView = inflater.inflate(R.layout.listitem, null); 
				}
				else{
					//기존의 항목 뷰가 있으면 이를 재사용
					itemView = convertView;					
				}
			
				
				//데이터 배열에서 데이터를 가져와서 뷰에 출력.
				Sensor sensor = objects.get(position); 
					if(sensor != null){
						TextView txtName = (TextView)itemView.findViewById(R.id.txtName);
						TextView txtVendor = (TextView)itemView.findViewById(R.id.txtVendor);
						TextView txtVersion = (TextView)itemView.findViewById(R.id.txtVersion);
						
						//각각의 텍스트 뷰에 센서 정보 출력.
						txtName.setText("센서:"+sensor.getName());
						txtVendor.setText("제조사:" + sensor.getVendor());
						txtVersion.setText("버전:"+sensor.getVersion());
						
					}
				
				return itemView;
			
		
		}
		
		
		
		
}
