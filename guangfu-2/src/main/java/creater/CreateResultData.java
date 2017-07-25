package creater;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import utils.StrUtil;
import utils.XFileUtils;


/**
 * 生成ResultData.java工具类
 * 
 * @author Xiang
 *
 */
public class CreateResultData {

	public void createBiz(String packagePath) throws IOException {
		String content = parse(packagePath);
		String path = System.getProperty("user.dir") + StrUtil.codePath + packagePath.replaceAll("\\.", "/");
		path = StrUtil.createFileDir(packagePath, path,"/vo/");
		String resPath = path + "/ResultData.java";
//		System.out.println("resPath=" + resPath);
		XFileUtils.writStringToFile(content, resPath);
	}

	
	
	private String parse(String packagePath) {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + packagePath + ".vo;\r\n\r\n");
		sb.append("public class ResultData<T> {\r\n\r\n");


		sb.append("\tprivate T data;\r\n\r\n");
		sb.append("\tprivate int code =200;\r\n\r\n");
		sb.append("\tprivate String msg;\r\n\r\n");
		sb.append("\tprivate Boolean success = true;\r\n\r\n");
		
		sb.append("\tpublic Boolean getSuccess() {\r\n\t\treturn success;\r\n\t}\r\n");
		sb.append("\tpublic void setSuccess(Boolean success) {\r\n\t\tthis.success = success;\r\n\t}\r\n\r\n");
		sb.append("\tpublic T getData() {\r\n\t\treturn data;\r\n\t}\r\n");
		sb.append("\tpublic void setData(T data) {\r\n\t\tthis.data = data;\r\n\t}\r\n\r\n");
		sb.append("\tpublic int getCode() {\r\n\t\treturn code;\r\n\t}\r\n");
		sb.append("\tpublic void setCode(int code) {\r\n\t\tif(code != 200){\r\n\t\t\tsuccess = false;\r\n\t\t}\r\n"
				+ "\t\tthis.code = code;\r\n\t}\r\n\r\n");
		sb.append("\tpublic String getMsg() {\r\n\t\treturn msg;\r\n\t}\r\n");
		sb.append("\tpublic void setMsg(String msg) {\r\n\t\tthis.msg = msg;\r\n\t}\r\n\r\n");
		
		
		
		
		
		

		sb.append("}");
//		System.out.println(sb.toString());
		return sb.toString();

	}
}