package com.example.callblock;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import android.os.Handler;
import android.util.Log;

public class ModityCallHandler {
	private static final String TAG = "ModityCallHandler";

	private static final String PATH_CALL_MANAGER = "com.android.internal.telephony.CallManager";
	private static final String PATH_CALL = "com.android.internal.telephony.Call";
	private static final String PATH_Phone = "com.android.internal.telephony.Phone";

	
	public static void modify() {
		try {
			Class callManagerClass = Class.forName(PATH_CALL_MANAGER);
			Class phoneClass = Class.forName(PATH_Phone);
			Class callClass = Class.forName(PATH_CALL);
			
			Object callManagerObj = ReflectUtil.invokeStaticMethod(PATH_CALL_MANAGER, "getInstance", new Object[0], new Class[0]);
			
//			Field field = CallManager.class.getDeclaredField("mHandler");
//			field.setAccessible(true);
			Field mHandlerField = callManagerObj.getClass().getDeclaredField("mHandler");
			mHandlerField.setAccessible(true);
			Log.e(TAG, mHandlerField.toString());
			
//			List phones = new ArrayList(callmanager.getAllPhones()); // ��һ�����������õ�����phone������
			Method getAllPhonesMethod = callManagerClass.getDeclaredMethod("getAllPhones", new Class[0]);
			List<Object> phoneList = (List<Object>) getAllPhonesMethod.invoke(callManagerObj, new Object[0]);
		
//			// �ڶ�����Ȼ�����unregister��������unregister��Ȼ����ܽ��е�����
//			// �����������������unregister�Լ�����register����������
//			for (Phone phone : phones) {
//				Log.i("NNNN", "unregister phone: " + phone.toString());
//				callmanager.unregisterPhone(phone);
//			}
			Method unregisterPhoneMethod = callManagerClass.getDeclaredMethod("unregisterPhone", phoneClass);
			for (Object phone : phoneList){
				Log.e(TAG, phone.toString());
				unregisterPhoneMethod.invoke(callManagerObj, new Object[]{phone});
			}
			
			
//			// ��������ͨ�����䣬��CallManager�е�mHandler�滻�����ǵ�ProxyHandler
			Handler handler = (Handler) mHandlerField.get(callManagerObj);
			Log.i(TAG, "mHandler: " + handler.toString());

			handler = new ProxyHandler(handler);
			mHandlerField.set(callManagerObj, handler);

			handler = (Handler) mHandlerField.get(callManagerObj);
			Log.i(TAG, "mHandler: " + handler.toString());
			
			
//			// ���Ĳ�������ע�ᡣ�������������һ����phones��������ͨ��callmanager.getAllPhones()
//			// ��ΪunregisterPhone��registerPhone��ֱ���޸�callmanager�е�mPhones����˵�һ�������ȱ������phone������
			Method registerPhoneMethod = callManagerClass.getDeclaredMethod("registerPhone", phoneClass);
			for (Object phone : phoneList) {
				registerPhoneMethod.invoke(callManagerObj, new Object[]{phone});
//				callmanager.registerPhone(phone);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, e.toString());
		}
	}
}
