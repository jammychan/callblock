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
			
//			List phones = new ArrayList(callmanager.getAllPhones()); // 第一步，必须先拿到各个phone的引用
			Method getAllPhonesMethod = callManagerClass.getDeclaredMethod("getAllPhones", new Class[0]);
			List<Object> phoneList = (List<Object>) getAllPhonesMethod.invoke(callManagerObj, new Object[0]);
		
//			// 第二步，然后遍历unregister。必须先unregister，然后才能进行第三步
//			// 如果反过来操作，那unregister以及重新register都会有问题
//			for (Phone phone : phones) {
//				Log.i("NNNN", "unregister phone: " + phone.toString());
//				callmanager.unregisterPhone(phone);
//			}
			Method unregisterPhoneMethod = callManagerClass.getDeclaredMethod("unregisterPhone", phoneClass);
			for (Object phone : phoneList){
				Log.e(TAG, phone.toString());
				unregisterPhoneMethod.invoke(callManagerObj, new Object[]{phone});
			}
			
			
//			// 第三步，通过反射，把CallManager中的mHandler替换到我们的ProxyHandler
			Handler handler = (Handler) mHandlerField.get(callManagerObj);
			Log.i(TAG, "mHandler: " + handler.toString());

			handler = new ProxyHandler(handler);
			mHandlerField.set(callManagerObj, handler);

			handler = (Handler) mHandlerField.get(callManagerObj);
			Log.i(TAG, "mHandler: " + handler.toString());
			
			
//			// 第四步，重新注册。这里必须用往第一步的phones，而不是通过callmanager.getAllPhones()
//			// 因为unregisterPhone和registerPhone会直接修改callmanager中的mPhones，因此第一步必须先保存各个phone的引用
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
