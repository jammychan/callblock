package com.example.callblock;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

class MyPhoneStateListener extends PhoneStateListener{ 
    private static final String TAG = "MyPhoneStateListener";

	@Override 
    public void onCallStateChanged(int state, String incomingNumber) { 
    	String result = "";
        switch (state) { 
        case TelephonyManager.CALL_STATE_IDLE: 
            result+=" �ֻ�����������  "; 
            break; 
        case TelephonyManager.CALL_STATE_RINGING: 
            result+="  �ֻ��������ˣ��������:"+incomingNumber; 
            break; 
        case TelephonyManager.CALL_STATE_OFFHOOK: 
            result+=" �绰�������� "; 
        default: 
            break; 
        } 
        Log.i(TAG, "result is " + result);
     super.onCallStateChanged(state, incomingNumber); 
    } 
}
