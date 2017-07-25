package creater;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;

import utils.StrUtil;
import utils.XFileUtils;




/**
 * 生成Controller层代码工具类
 * 
 * @author Xiang
 *
 */
public class CreateControllerUtil {
	
	StrUtil strUtil = new StrUtil();

	public void createCtroller(String packagePath, String tableName, String domainObjectName) throws IOException {
		String content = parse(packagePath, tableName, domainObjectName);
		String path = System.getProperty("user.dir") + StrUtil.codePath + packagePath.replaceAll("\\.", "/");
		path = StrUtil.createFileDir(packagePath, path,"/controller/");
		String resPath = path + "/" + domainObjectName + "Controller.java";
//		System.out.println("resPath=" + resPath);
		XFileUtils.writStringToFile(content, resPath);
	}

	/**
	 * 解析处理(生成业务层代码)
	 */
	private String parse(String packagePath, String tableName, String domainObjectName) {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + packagePath + ".controller;\r\n\r\n");
		sb.append("import javax.annotation.Resource;\r\n");
		sb.append("import javax.servlet.http.HttpServletRequest;\r\n");
		sb.append("import javax.servlet.http.HttpServletResponse;\r\n");
		sb.append("import javax.servlet.http.HttpSession;\r\n\r\n");
		sb.append("import java.util.List;\r\n\r\n");
		sb.append("import com.alibaba.fastjson.JSON;\r\n\r\n");
		sb.append("import org.springframework.stereotype.Controller;\r\n");
		sb.append("import org.springframework.web.bind.annotation.ResponseBody;\r\n");
		sb.append("import org.springframework.web.bind.annotation.RequestMapping;\r\n\r\n");
		sb.append("import " + packagePath + ".model." + domainObjectName + ";\r\n");
		sb.append("import " + packagePath + ".vo." + domainObjectName + "VO;\r\n");
		sb.append("import " + packagePath + ".model.Page;\r\n");
		sb.append("import " + packagePath + ".vo.ResultData;\r\n");
		sb.append("import "+packagePath+".biz."+domainObjectName+"Biz;\r\n\r\n\r\n");
		
		
		/*@Controller
		@RequestMapping(value = "/user")*/
		sb.append("@Controller\r\n");
		String lCasseModel = strUtil.smallCase(domainObjectName);
		sb.append("@RequestMapping(value = \"/"+lCasseModel+"\")\r\n");
		
		
		sb.append("public class " + domainObjectName + "Controller" + " {\r\n");

		/*@Resource
		UserBiz userBiz;*/
		sb.append("@Resource\r\n");
		sb.append(domainObjectName+"Biz "+lCasseModel+"Biz;\r\n\r\n");
		
		sb.append("//@ResponseBody\r\n");
		sb.append("//@RequestMapping(value = '/delete')\r\n");
		sb.append("\tpublic " + "ResultData " + "delete"+"(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) " + "{\r\n");
		sb.append("\t\tResultData resultData = new ResultData();\r\n");
		sb.append("\t"+lCasseModel+"Biz.delete(id);\n");
		createEndReturn(sb);
		
		/*
		 * public int insertSelective(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) { return 0; }
		 */
//		createMathd("insertSelective",domainObjectName, sb);
		sb.append("//@ResponseBody\r\n");
		sb.append("//@RequestMapping(value = '/insert')\r\n");
		sb.append("\tpublic " + "ResultData " + "insert"+"("+domainObjectName+" "+lCasseModel+", HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) " + "{\r\n");
		sb.append("\t\tResultData resultData = new ResultData();\r\n");
		sb.append("\t\t"+lCasseModel+"Biz.insert("+lCasseModel+");\n");
		sb.append("\t\tresultData.setData("+lCasseModel+");\n");
		createEndReturn(sb);
		/*
		 * public int selectByPrimaryKey(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) { return null; }
		 */
//		createMathd("selectByPrimaryKey",domainObjectName, sb);
		sb.append("//@ResponseBody\r\n");
		sb.append("//@RequestMapping(value = '/select')\r\n");
		sb.append("\tpublic " + "ResultData " + "select"+"(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) " + "{\r\n");
		sb.append("\t\tResultData resultData = new ResultData();\r\n");
		sb.append("\t\t"+domainObjectName+" "+lCasseModel+" = "+lCasseModel+"Biz.select(id);\n");
		sb.append("\t\tresultData.setData("+lCasseModel+");\n");
		createEndReturn(sb);
		
		/*
		 * public int updateByPrimaryKeySelective(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) { return 0; }
		 */
//		createMathd("updateByPrimaryKeySelective",domainObjectName, sb);
		sb.append("//@ResponseBody\r\n");
		sb.append("//@RequestMapping(value = '/update')\r\n");
		sb.append("\tpublic " + "ResultData " + "update"+"("+domainObjectName+" "+lCasseModel+", HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) " + "{\r\n");
		sb.append("\t\tResultData resultData = new ResultData();\r\n");
		sb.append("\t\t"+lCasseModel+"Biz.update("+lCasseModel+");\n");
		createEndReturn(sb);
		
		sb.append("//@ResponseBody\r\n");
		sb.append("//@RequestMapping(value = '/selectOneByExample')\r\n");
		sb.append("\tpublic " + "ResultData " + "selectOneByExample"+"("+domainObjectName+" "+lCasseModel+", HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) " + "{\r\n");
		sb.append("\t\tResultData resultData = new ResultData();\r\n");
		sb.append("\t\t"+domainObjectName+" selectByExample ="+lCasseModel+"Biz.selectOneByExample("+lCasseModel+");\n");
		
		sb.append("\t\tresultData.setData(selectByExample);\r\n");
		createEndReturn(sb);
		/*
		 * public int selectByExample(User user,Page<User> page,HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) { return 0; }
		 */
		createMathdSelectByExample(domainObjectName, sb);

//		sb.append("\tInteger insertBatch(List list);\r\n");
		sb.append("//@ResponseBody\r\n");
		sb.append("//@RequestMapping(value = '/insertBatch')\r\n");
		sb.append("\tpublic " + "ResultData " + "insertBatch"+"(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) " + "{\r\n");
		sb.append("\t\t");
		sb.append("\t\tResultData resultData = new ResultData();\r\n");
		sb.append("List<"+domainObjectName+"> list = JSON.parseArray(listJson.getData(), "+domainObjectName+".class);\n");
		
		sb.append("\t\t"+lCasseModel+"Biz.insertBatch(list);\n");
		sb.append("\t\tresultData.setData(list);\n");
		createEndReturn(sb);
		/**
		 * deleteBatch
		 */
		sb.append("//@ResponseBody\r\n");
		sb.append("//@RequestMapping(value = '/deleteBatch')\r\n");
		sb.append("\tpublic " + "ResultData " + "deleteBatch"+"(Page<Long> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) " + "{\r\n");
		sb.append("List<Long> ids = page.getList();\n");
		sb.append("\t\tResultData resultData = new ResultData();\r\n");
		sb.append("\t\t"+lCasseModel+"Biz.deleteBatch(ids);\n");
		createEndReturn(sb);
		
		/**
		 * 结束的括号
		 */
		sb.append("}");
//		System.out.println(sb.toString());
		return sb.toString();

	}

	private void createEndReturn(StringBuffer sb) {
		sb.append("\t\treturn resultData;\r\n");
		sb.append("\t}\r\n");
	}
	private void createMathd(String mathdName,String domainObjectName, StringBuffer sb) {
		sb.append("//@ResponseBody\r\n");
		sb.append("//@RequestMapping(value = \"/"+strUtil.smallCase(domainObjectName)+"/"+mathdName+"\")\r\n");
		sb.append("\tpublic " + "ResultData " + mathdName+"("+domainObjectName+" "+strUtil.smallCase(domainObjectName)+", Page<"+domainObjectName+"> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) " + "{\r\n");
		createEndReturn(sb);
	}
	
	/**
	 * 创建 selectByExample方法
	 * @param domainObjectName
	 * @param sb
	 */
	private void createMathdSelectByExample(String domainObjectName, StringBuffer sb) {
		sb.append("//@ResponseBody\r\n");
		sb.append("//@RequestMapping(value = '/selectByExample')\r\n");
		sb.append("\tpublic " + "ResultData " + "selectByExample("+domainObjectName+" "+strUtil.smallCase(domainObjectName)+", Page<"+domainObjectName+"> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) " + "{\r\n");
		//sb.append("\t\t\r\n");
		sb.append("\t\tResultData resultData = new ResultData();\r\n");
		sb.append("\t\tif (page.getExample()==null) {\r\n");
		sb.append("\t\t\t\tpage.setExample("+strUtil.smallCase(domainObjectName)+");\r\n");
		sb.append("\t\t\t}\r\n");
		sb.append("\t\tPage<"+domainObjectName+"> selectByExample = "+strUtil.smallCase(domainObjectName)+"Biz.selectByExample(page);\r\n");
		sb.append("\t\tresultData.setData(selectByExample);\r\n");
		//sb.append("\t\t\r\n");
		sb.append("\t\treturn resultData;\r\n");
		sb.append("\t}\r\n");
	}
}