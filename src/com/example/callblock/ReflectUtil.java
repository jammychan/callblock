package com.example.callblock;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.util.Log;



/**
 * 反射方法辅助�?
 *
 */
@SuppressWarnings("unchecked")
public class ReflectUtil {

	private static final String TAG = "ReflectUtil";

	/**
	 * 实例化对�?
	 * @param className
	 * @param args
	 * @return
	 * @throws Exception
	 */
	static public Object newInstance(String className, Object[] args, Class[] argsClass)
	throws Exception {
		Class newoneClass = Class.forName(className);

		Constructor cons = newoneClass.getConstructor(argsClass);

		return cons.newInstance(args);
	}

    /**
     * 实例化对�?
     * @param className
     * @param args
     * @return
     * @throws Exception
     */
	static public Object newInstance(String className, Object[] args)
            throws Exception {
        Class newoneClass = Class.forName(className);
        Class[] argsClass = new Class[args.length];

        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }

        Constructor cons = newoneClass.getConstructor(argsClass);

        return cons.newInstance(args);
    }

    /**
     * 获取某对象的属�?
     *
     * @param owner
     * @param fieldName
     * @return
     * @throws Exception
     */
	static public Object getProperty(Object owner, String fieldName)
            throws Exception {
        Class ownerClass = owner.getClass();

        Field field = ownerClass.getField(fieldName);

        Object property = field.get(owner);

        return property;
    }

    /**
     * 执行某对象的方法
     *
     * @param owner
     * @param methodName
     * @param args
     * @return
     * @throws Exception
     */
	static public Object invokeMethod(Object owner, String methodName, Object[] args)
            throws Exception {
        Class ownerClass = owner.getClass();
        Class[] argsClass = new Class[args.length];

        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
            if (argsClass[i] == Integer.class) {
                argsClass[i] = int.class;
            } else if (argsClass[i] == Boolean.class) {
                argsClass[i] = boolean.class;
            }
        }

        Method method = ownerClass.getMethod(methodName, argsClass);

        return method.invoke(owner, args);
    }

	/**
	 * 反射调用静�?方法
	 * @param className
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	static public Object invokeStaticMethod(String className, String methodName, Object[] args, Class[] argsClass) throws Exception {

		Class cls = Class.forName(className);
		Method staticMethod = null;
		Object ret = null;
		try {
			staticMethod = cls.getDeclaredMethod(methodName, argsClass);
			ret = staticMethod.invoke(cls, args);//这里不需要newInstance
		} catch (Exception e) {
			Log.e(TAG, "invokeStaticMethod getDeclaredMethod e=" + e.toString());
		}
		return ret;
	}

}
