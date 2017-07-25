package creater;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import utils.FileUtil;
import utils.StrUtil;
import utils.XFileUtils;

public class CreateUtil {
	
	
	public static void createEntity2(String packagePath, String tableName, String domainObjectName) {
	
		String path = System.getProperty("user.dir") + StrUtil.codePath + packagePath.replaceAll("\\.", "/");
		path = StrUtil.createFileDir(packagePath, path,"/util/");
		String resPath = path+ "ObjToMap.java";
		try {
			File file = new File("src/main/java/template/ObjToMap.java");
			FileUtils.copyFile(file, new File(resPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createEntity(String packagePath, String tableName, String domainObjectName) {
		String readFileByLines = FileUtil.readFileByLines("src/main/java/template/ObjToMap.java");
		
		String content = readFileByLines.replace("package template;", "package " + packagePath + ".util;\r\n\r\n");
	
		String path = System.getProperty("user.dir") + StrUtil.codePath + packagePath.replaceAll("\\.", "/");
		path = StrUtil.createFileDir(packagePath, path,"/util/");
		String resPath = path+ "ObjToMap.java";
		try {
			XFileUtils.writStringToFile2(content, resPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
