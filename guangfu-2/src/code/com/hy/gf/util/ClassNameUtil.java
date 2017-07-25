/**
 *
 */
package com.hy.gf.util;

/**
 * @Description:
 * @author yanyong
 * @date 2015年11月19日 上午12:42:35
 *
 */
public class ClassNameUtil {

	/**
	 * 接口名转化成服务名
	 * @param interfaceName
	 * @return
	 */
	public static String fromInterfaceToService(Class<?> clazz){
		String interfaceName = clazz.getSimpleName();
		return Character.toLowerCase(interfaceName.charAt(1)) + interfaceName.substring(2);
	}
}
