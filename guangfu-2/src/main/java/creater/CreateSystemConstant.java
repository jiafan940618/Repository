package creater;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import utils.StrUtil;
import utils.XFileUtils;


/**
 * 生成SystemConstant.java工具类
 * 
 * @author Xiang
 *
 */
public class CreateSystemConstant {

	public void createBiz(String packagePath) throws IOException {
		String content = parse(packagePath);
		String path = System.getProperty("user.dir") + StrUtil.codePath + packagePath.replaceAll("\\.", "/");
		path = StrUtil.createFileDir(packagePath, path,"/util/");
		String resPath = path + "/SystemConstant.java";
//		System.out.println("resPath=" + resPath);
		XFileUtils.writStringToFile(content, resPath);
	}

	
	
	private String parse(String packagePath) {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + packagePath + ".util;\r\n\r\n");
		sb.append("public class SystemConstant {\r\n");
		sb.append("\tpublic static int limit =50;\r\n");
		
		

		sb.append("}");
		System.out.println(sb.toString());
		return sb.toString();

	}
}