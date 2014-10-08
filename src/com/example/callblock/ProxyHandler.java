package com.example.callblock;

import java.lang.reflect.Method;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;

public final class ProxyHandler extends Handler {
	private SparseArray mTable = new SparseArray();
	private Handler mInnterHandler;

	public ProxyHandler(Handler handler) {
		super(handler.getLooper());
		mInnterHandler = handler;

		mTable.put(100, "EVENT_DISCONNECT");
		mTable.put(101, "EVENT_PRECISE_CALL_STATE_CHANGED");
		mTable.put(102, "EVENT_NEW_RINGING_CONNECTION");
		mTable.put(103, "EVENT_UNKNOWN_CONNECTION");
		mTable.put(104, "EVENT_INCOMING_RING");
		mTable.put(105, "EVENT_RINGBACK_TONE");
		mTable.put(106, "EVENT_IN_CALL_VOICE_PRIVACY_ON");
		mTable.put(107, "EVENT_IN_CALL_VOICE_PRIVACY_OFF");
		mTable.put(108, "EVENT_CALL_WAITING");
		mTable.put(109, "EVENT_DISPLAY_INFO");
		mTable.put(110, "EVENT_SIGNAL_INFO");
		mTable.put(111, "EVENT_CDMA_OTA_STATUS_CHANGE");
		mTable.put(112, "EVENT_RESEND_INCALL_MUTE");
		mTable.put(113, "EVENT_MMI_INITIATE");
		mTable.put(114, "EVENT_MMI_COMPLETE");
		mTable.put(115, "EVENT_ECM_TIMER_RESET");
		mTable.put(116, "EVENT_SUBSCRIPTION_INFO_READY");
		mTable.put(117, "EVENT_SUPP_SERVICE_FAILED");
		mTable.put(118, "EVENT_SERVICE_STATE_CHANGED");
		mTable.put(119, "EVENT_POST_DIAL_CHARACTER");
	}
	
	private static final String PATH_CALL_MANAGER = "com.android.internal.telephony.CallManager";
	private static final String PATH_CALL = "com.android.internal.telephony.Call";
	private static final String PATH_Phone = "com.android.internal.telephony.Phone";

	@Override
	public void handleMessage(Message msg) {
		if (msg.what >= 100 && msg.what <= 119) {
			Log.i("ProxyPhone", "Event: " + mTable.get(msg.what));
		}
		
//		Class callManagerClass = Class.forName(PATH_CALL_MANAGER);
//		Object callManagerObj = ReflectUtil.invokeStaticMethod(PATH_CALL_MANAGER, "getInstance", new Object[0], new Class[0]);
//		
//		Phone phone = CallManager.getInstance().getAllPhones().get(0);
//		Call ringingCall = phone.getRingingCall();
//		String incomingNumber = null;
//		if (ringingCall != null && ringingCall.getEarliestConnection() != null) {
//			incomingNumber = ringingCall.getEarliestConnection().getAddress();
//		}
//
//		Log.i("ProxyPhone", "incomingNumber: " + incomingNumber);
//		if (incomingNumber != null) { // 遇到有号码则直接返回
//			return;
//		}
//
//		mInnterHandler.handleMessage(msg);
	}

	@Override
	public String toString() {
		return "Proxy " + mInnterHandler.toString();
	}

}
