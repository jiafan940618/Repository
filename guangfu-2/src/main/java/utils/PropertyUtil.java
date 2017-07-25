package utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropertyUtil {
	
	public static <T> T setDefault(T t, Class<T> clazz) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			String name = field.getName();
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
			
			String type = field.getGenericType().toString(); // 获取属性的类型
            if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                Method m = t.getClass().getMethod("get" + name);
                String value = (String) m.invoke(t); // 调用getter方法获取属性值
                if (value == null) {
                    m = t.getClass().getMethod("set"+name,String.class);
                    m.invoke(t, "");
                }
            }
            if (type.equals("class java.lang.Integer")) {
                Method m = t.getClass().getMethod("get" + name);
                Integer value = (Integer) m.invoke(t);
                if (value == null) {
                    m = t.getClass().getMethod("set"+name,Integer.class);
                    m.invoke(t, 0);
                }
            }
            if (type.equals("class java.lang.Long")) {
                Method m = t.getClass().getMethod("get" + name);
                Long value = (Long) m.invoke(t);
                if (value == null) {
                    m = t.getClass().getMethod("set"+name,Long.class);
                    m.invoke(t, 0l);
                }
            }
            if (type.equals("class java.lang.Double")) {
                Method m = t.getClass().getMethod("get" + name);
                Double value = (Double) m.invoke(t);
                if (value == null) {
                    m = t.getClass().getMethod("set"+name,Double.class);
                    m.invoke(t, 0.0);
                }
            }
		}
		return t;
	}

}
