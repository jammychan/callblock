package com.example.callblock;

import java.lang.reflect.Method;

import com.android.internal.telephony.ITelephony;

import android.content.Context;
import android.media.AudioManager;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

class MyPhoneStateListener extends PhoneStateListener{ 
    private static final String TAG = "MyPhoneStateListener";
    private Context mContext;
    
    public MyPhoneStateListener(Context context){
    	this.mContext = context;
    }
    

	@Override 
    public void onCallStateChanged(int state, String incomingNumber) { 
    	String result = "";
        switch (state) { 
        case TelephonyManager.CALL_STATE_IDLE: 
            result+=" 手机空闲起来了  "; 
            break; 
        case TelephonyManager.CALL_STATE_RINGING: 
            result+="  手机铃声响了，来电号码:"+incomingNumber; 
            final String number = incomingNumber;
            if (incomingNumber.startsWith("1598919")){
            	Log.e(TAG, "incoming number " + number);
//            	new Thread(new Runnable() {
//					@Override
//					public void run() {
//						try {
//							Thread.sleep(2000);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						stop(number);
//					}
//				}).start();
            }
            break; 
        case TelephonyManager.CALL_STATE_OFFHOOK: 
            result+=" 电话被挂起了 "; 
        default: 
            break; 
        } 
        Log.i(TAG, "result is " + result);
     super.onCallStateChanged(state, incomingNumber); 
    } 
	
	
	private static ITelephony getITelephony(Context context) {
        
        TelephonyManager mTelephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
       
        Class c = TelephonyManager.class;
        Method getITelephonyMethod = null;
        try {
            getITelephonyMethod = c.getDeclaredMethod("getITelephony",
                    (Class[]) null); // 获取声明的方法
            getITelephonyMethod.setAccessible(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
   
        ITelephony iTelephony = null;
        try {
            iTelephony = (ITelephony) getITelephonyMethod.invoke(
                    mTelephonyManager, (Object[]) null); // 获取实例
            return iTelephony;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iTelephony;
    }
    
    
	public void stop(String incoming_number) {
		AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);// 静音处理
		ITelephony iTelephony = getITelephony(mContext); // 获取电话接口
		try {
			iTelephony.endCall();// 结束电话
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		// 再恢复正常铃声
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		Log.i("----", "来电 :" + incoming_number);
	}
}
