package com.example.callblock;

import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        SharedPreferences phonenumSP = getSharedPreferences("in_phone_num", Context.MODE_PRIVATE);
		Map map = phonenumSP.getAll();
        Object[] array = map.keySet().toArray();
        Log.v("tag",map.toString()+map.size());
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,array);
        setListAdapter(adapter);
        
        
        //获取电话服务
        TelephonyManager manager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE); 
        // 设置PhoneStateListener中的listen_call_state状态进行监听  
        manager.listen(new MyPhoneStateListener(this), PhoneStateListener.LISTEN_CALL_STATE); 
    }
}