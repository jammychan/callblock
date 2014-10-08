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
            result+=" �ֻ�����������  "; 
            break; 
        case TelephonyManager.CALL_STATE_RINGING: 
            result+="  �ֻ��������ˣ��������:"+incomingNumber; 
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
            result+=" �绰�������� "; 
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
                    (Class[]) null); // ��ȡ�����ķ���
            getITelephonyMethod.setAccessible(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
   
        ITelephony iTelephony = null;
        try {
            iTelephony = (ITelephony) getITelephonyMethod.invoke(
                    mTelephonyManager, (Object[]) null); // ��ȡʵ��
            return iTelephony;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iTelephony;
    }
    
    
	public void stop(String incoming_number) {
		AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);// ��������
		ITelephony iTelephony = getITelephony(mContext); // ��ȡ�绰�ӿ�
		try {
			iTelephony.endCall();// �����绰
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		// �ٻָ���������
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		Log.i("----", "���� :" + incoming_number);
	}
}
