package utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class XFileUtils {

	public static void writStringToFile(String content, String resPath) throws IOException {
		content=content.replaceAll("'", "\"");
		FileUtils.writeStringToFile(new File(resPath), content);
	}
	
	public static void writStringToFile2(String content, String resPath) throws IOException {
		FileUtils.writeStringToFile(new File(resPath), content);
	}
}
