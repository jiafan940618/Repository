package utils;

import java.io.File;

/**
 * 操作字符串
 * @author Xiang
 *
 */
public class StrUtil {
	public static String codePath = "/src/code/";
	/**
	 * 将t_user_collect变成user_collect再变成userCollect再变成UserCollect
	 * 将user_collect变成userCollect再变成UserCollect
	 * 将user变成User
	 * @param str
	 * @return
	 */
	/*public String upperCase(String str) {
		String returnStr = null;
		// System.out.println(str.indexOf("_"));
		// System.out.println(str.lastIndexOf("_"));
		System.out.println("tableName是否含有下划线_："+str.contains("_"));
		if (str.indexOf("_") == str.lastIndexOf("_") && str.contains("_")) {
			System.out.println(str);
			if (str.indexOf("_") == 1) {
				String str2 = str.substring(str.indexOf("_") + 1);
				String str3 = initcap(str2);
				returnStr = str3;
				return returnStr;
			}
			int index = str.indexOf("_");
			String str1 = str.substring(0, index) + str.substring(index + 1, index + 2).toUpperCase()
					+ str.substring(index + 2);
			System.out.println(str1);
			String str3 = initcap(str1);
			returnStr = str3;
			return returnStr;
		} else if (str.indexOf("_") != str.lastIndexOf("_")) {
			System.out.println(str);
			int index = str.indexOf("_");
			String str2 = str.substring(index + 1);
			System.out.println(str2);
			String str3 = getCamelStr(str2);
			System.out.println(str3);
			String str4 = initcap(str3);
			returnStr = str4;
			return returnStr;
		} else {
			// String str1 = str.substring(0).toUpperCase();
			String str1 = initcap(str);
			returnStr = str1;
			return returnStr;
		}
	}*/

	/**
	 * 例：user_name --> userName
	 * @param s
	 * @return
	 */
/*	public String getCamelStr(String s) {
		while (s.indexOf("_") > 0) {
			int index = s.indexOf("_");
			// System.out.println(s.substring(index+1, index+2).toUpperCase());
			s = s.substring(0, index) + s.substring(index + 1, index + 2).toUpperCase() + s.substring(index + 2);
			return s;
		}
		return s;
	}*/

	/**
	 * 创建目录
	 * @param packagePath
	 * @param path
	 * @param modelName
	 * @return
	 */
	public static String createFileDir(String packagePath, String path,String modelName) {
//		String[] split = packagePath.split(".");
//		for (int i = 0; i < split.length; i++) {
//			path+=split[i];
//		}
		path+=modelName;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}
	
	/**
	 * 把输入字符串的首字母改成大写
	 * student-->Student
	 * @param str
	 * @return
	 */
	public static String initcap(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return getCamelStr(new String(ch));
	}
	
	/**
	 *  把输入字符串的首字母改成小写
	 * @param str
	 * @return
	 */
	public static String smallCase(String str) {
		String firstStr = str.substring(0, 1).toLowerCase();
		String endStr = str.substring(1);
		return (firstStr+endStr);
	}
	
	
	/**
	 * 例如：employe_user_id变成employeUserId
	 * @param str
	 * @return
	 */
	public static String getCamelStr(String str) {
		String[] str1 = str.split("_");
		int size = str1.length;
		String str2 = null;
		String str4 = null;
		String str3 = null;
		for(int i=0;i<size;i++) {
			if(i==0) {
				str2 = str1[i];
				str4 = str2;
			}else {
				str3 = initcap(str1[i]);
				str4 = str4+str3;
			}
		}
		return str4;
	}
}
