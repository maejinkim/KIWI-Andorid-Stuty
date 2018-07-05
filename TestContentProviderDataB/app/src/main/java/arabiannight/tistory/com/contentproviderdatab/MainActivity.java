package arabiannight.tistory.com.contentproviderdatab;

import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * ContentProvider Call Activity
 */
public class MainActivity extends Activity {
	
	public static final String 	AUTHORITY    = "arabiannight.tistory.com.contentproviderdataa";

	/** ContentProvider 제공 클래스에서 받을 uri.getPathSegments()를 등록해 준다
	 * 	<< content://" + AUTHORITY + PATH_GET>> 다음부터 getPathSegments[0] = PATH_GET,
	 * [1], [2], [3]... 순으로 나간다.
	 */
	public static final String  PATH_GET = "/AUTH_GET";
	public static final String  PATH_UPDATE = "/AUTH_UPDATE";
	
	/** CotentProvider 접근을 위한 ContentResolver 객체를 생성할 때 넣어 주는 매개변수에
	 *  URI를 사용 한다.
	 */
	public static final Uri 	CONTENT_URI  = 
			Uri.parse("content://" + AUTHORITY + PATH_GET);
	
	public static final Uri 	CONTENT_URI2  = 
			Uri.parse("content://" + AUTHORITY + PATH_UPDATE);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setLayout();
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_get:
			Log.i("PROVIDERT", "B Click Auth get Button!");
			// ContentResolver 객체 얻어 오기
			ContentResolver cr = getContentResolver();
			// ContentProviderDataA 어플리케이션 insert() 메서드에 접근
			Uri uri = cr.insert(CONTENT_URI, new ContentValues());
			// ContentProviderDataA 어플리케이션 에서 리턴받은 Data값 셋팅 하기
			List<String> authValues = uri.getPathSegments();
			String serviceType = authValues.get(0);
			String authkey = authValues.get(1);
			Log.i("PROVIDERT", "B_Return_serviceType = " + serviceType);
			Log.i("PROVIDERT", "B_Return_authkey = " + authkey);
			tv_Content.setText("어플리케이션에서 받아온 인증키 : " + authkey);
			break;
		case R.id.bt_update:
			// ContentResolver 객체 얻어 오기
			ContentResolver cr2 = getContentResolver();
			// ContentValuse를 사용한 Data 전달하기
			ContentValues cv = new ContentValues();
			cv.put("new_authkey", "SECONDeww33000aaacccxx");
			// ContentProviderDataA 어플리케이션 update() 메서드에 접근
			cr2.update(CONTENT_URI2, cv, null, null);
			break;
		default:
			break;
		}
	}



	/**
	 * Layout
	 */
	private TextView tv_Content;

	private void setLayout() {
		tv_Content = (TextView) findViewById(R.id.tv_content);
	}


}




