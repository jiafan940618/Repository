package creater;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import utils.StrUtil;
import utils.XFileUtils;


/**
 * 生成Biz(业务)层代码工具类
 * 
 * @author Xiang
 *
 */
public class CreateBizUtil {
	

	public void createBiz(String packagePath, String tableName, String domainObjectName) throws IOException {
		String content = parse(packagePath, tableName, domainObjectName);
		String path = System.getProperty("user.dir") + StrUtil.codePath + packagePath.replaceAll("\\.", "/");
		
		path = StrUtil.createFileDir(packagePath, path,"/biz/");
		String resPath = path + "/" + domainObjectName + "Biz.java";
		System.out.println("resPath=" + resPath);
		XFileUtils.writStringToFile(content, resPath);
	}

	

	/**
	 * 解析处理(生成业务层代码)
	 */
	private String parse(String packagePath, String tableName, String domainObjectName) {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + packagePath + ".biz;\r\n\r\n");
		sb.append("import java.util.List;\r\n\r\n");
		sb.append("import java.util.Map;\r\n\r\n");
		sb.append("import javax.annotation.Resource;\r\n\r\n");
		sb.append("import org.springframework.stereotype.Service;\r\n\r\n");
		sb.append("import " + packagePath + ".model." + domainObjectName + ";\r\n");
		sb.append("import " + packagePath + ".util.*;\r\n");
		sb.append("import " + packagePath + ".mapper." + domainObjectName + "Mapper;\r\n");
		sb.append("import " + packagePath + ".model.Page;\r\n\r\n");
		
		
		
		sb.append("@Service\r\n");
		sb.append("public class " + domainObjectName + "Biz" + " {\r\n\r\n");
		sb.append("@Resource\r\n");
		String lCasseModel = StrUtil.smallCase(domainObjectName);
		sb.append(domainObjectName+"Mapper "+lCasseModel+"Mapper;\r\n\r\n");
		
		/**
		 * deleteByPK
		 */
		sb.append("\tpublic int delete(Long id) {\r\n");
		sb.append("\t\treturn "+lCasseModel+"Mapper.delete(id);\r\n");
		sb.append("\t}\r\n");
		/*
		 * public int insertSelective(Odb record) { return 0; }
		 */
		sb.append("\tpublic int insert(" + domainObjectName + " record) {\r\n");
		sb.append("\t\treturn "+lCasseModel+"Mapper.insert(record);\r\n");
		sb.append("\t}\r\n");

		/*
		 * public Odb selectByPrimaryKey(Long id) { return null; }
		 */
		sb.append("\tpublic " + domainObjectName + " select(Long id) {\r\n");
		sb.append("\t\treturn "+lCasseModel+"Mapper.select(id);\r\n");
		sb.append("\t}\r\n");

		/*
		 * public int updateByPrimaryKeySelective(Odb record) { return 0; }
		 */
		sb.append("\tpublic " + "int " + "update(" + domainObjectName + " record) " + "{\r\n");
		sb.append("\t\treturn "+lCasseModel+"Mapper.update(record);\r\n");
		sb.append("\t}\r\n");
		
		/*
		 * public int selectOneByExample(Odb record) { return 0; }
		 */
		sb.append("\tpublic " + domainObjectName + " selectOneByExample(" + domainObjectName + " record) " + "{\r\n");
		sb.append("\t\treturn "+lCasseModel+"Mapper.selectOneByExample(record);\r\n");
		sb.append("\t}\r\n");
		
		/*
		 * public int selectByExample(Odb record) { return 0; }
		 */
		sb.append("\tpublic " + "Page<"+domainObjectName+"> " + "selectByExample(Page<"+domainObjectName+"> page) " + "{\r\n");
		sb.append("Object example = page.getExample();\n");
		sb.append(domainObjectName+" "+lCasseModel+" = null;\n");
		sb.append("if (example != null) {\n");
		sb.append(""+lCasseModel+" = ("+domainObjectName+") example;\n");
		sb.append("}\n");
		sb.append("Map<String, Object> objectMap = ObjToMap.getObjectMap(page);\n");
		sb.append("Map<String, Object> objectMap2 = ObjToMap.getObjectMap("+lCasseModel+");\n");
		sb.append("objectMap.putAll(objectMap2);");
		sb.append("List<"+domainObjectName+"> selectByExample = "+lCasseModel+"Mapper.selectByExample(objectMap);\n");
		sb.append("page.setList(selectByExample);\n");
		sb.append("page.setExample(null);\n");
		sb.append("Integer selectByExampleCount = "+lCasseModel+"Mapper.selectByExampleCount("+lCasseModel+");\n");
		sb.append("page.setTotal(selectByExampleCount);\n");
		sb.append("return page;\n");
		sb.append("\t}\r\n");
	
		/**
		 * insertBatch
		 */
		sb.append("\tpublic " + "List " + "insertBatch"+"(List list) " + "{\r\n");
		sb.append("\t\t"+lCasseModel+"Mapper.insertBatch(list);\n");
		sb.append("\t\treturn list;\r\n");
		sb.append("}");
		/**
		 * deleteBatch
		 */
		sb.append("\tpublic " + "int " + "deleteBatch"+"(List ids) " + "{\r\n");
		sb.append("\t\treturn "+lCasseModel+"Mapper.deleteBatch(ids);\n");
		sb.append("}");
		sb.append("}");
//		System.out.println(sb.toString());
		return sb.toString();

	}
}