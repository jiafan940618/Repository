package creater;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import utils.StrUtil;
import utils.XFileUtils;




/**
 * 生成Dao层代码工具类
 * 
 * @author Xiang
 *
 */
public class CreateMapperUtil {

	StrUtil strUtil = new StrUtil();

	public void createDao(String packagePath, String tableName, String domainObjectName) throws IOException {
		String content = parse(packagePath, tableName, domainObjectName);
		String path = System.getProperty("user.dir") + StrUtil.codePath + packagePath.replaceAll("\\.", "/");
		path = StrUtil.createFileDir(packagePath, path,"/mapper/");
		String resPath = path + "/" + domainObjectName + "Mapper.java";
		System.out.println("resPath=" + resPath);
		XFileUtils.writStringToFile(content, resPath);
	}

	/**
	 * 解析处理(生成业务层代码)
	 */
	private String parse(String packagePath, String tableName, String domainObjectName) {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + packagePath + ".mapper;\r\n\r\n");
		sb.append("import org.springframework.stereotype.Component;\r\n");
		sb.append("import " + packagePath + ".model." + domainObjectName + ";\r\n");
		sb.append("import " + packagePath + ".model.Page;\r\n");
		sb.append("import java.util.List;\r\n\r\n");
		sb.append("import java.util.Map;\r\n\r\n");
		sb.append("@Component\r\n");
		sb.append("public interface " + domainObjectName + "Mapper" + " {\r\n\r\n");

		// Odb delete
		sb.append("\tint delete(Long id);\r\n\r\n");
		
		// delete
		sb.append("\t" + domainObjectName + " select(Long id);\r\n\r\n");
		
		// int insertSelective(Odb record);
		sb.append("\tint insert(" + domainObjectName + " record);\r\n\r\n");

		// Odb selectByPrimaryKey(Long id);
		sb.append("\t" + domainObjectName + " selectByPK(Long id);\r\n\r\n");

		// int updateByPrimaryKeySelective(Odb record);
		sb.append("\tint update(" + domainObjectName
				+ " record);\r\n\r\n");
		
		sb.append("\t"+domainObjectName+" selectOneByExample(" + domainObjectName
				+ " record);\r\n\r\n");
		
		//List<User> selectByExample(Page<User> page);
		sb.append("\tList<"+domainObjectName+"> selectByExample(Map<String, Object> objectMap);\r\n\r\n");

		//Integer selectByExampleCount(User user);
		sb.append("\tInteger selectByExampleCount("+domainObjectName+" "+strUtil.smallCase(domainObjectName)+");\r\n");
		
		sb.append("\tInteger insertBatch(List list);\r\n");
		
		sb.append("\tInteger deleteBatch(List ids);\r\n");
		sb.append("}");
//		System.out.println(sb.toString());
		return sb.toString();

	}
}