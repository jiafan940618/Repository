package utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

import creater.CreateBizUtil;
import creater.CreateControllerUtil;
import creater.CreateEntityUtil;
import creater.CreateEntityVoUtil;
import creater.CreateMapperUtil;
import creater.CreateMappingUtil;
import creater.CreatePage;
import creater.CreateResultData;
import creater.CreateSystemConstant;
import creater.CreateUtil;
import creater.CreateVGM;
import creater.CreateVueEdit;

public class Main {

	public static void main(String[] args) throws IOException, SQLException {
		GetElementUtil elementUtil = new GetElementUtil();
		CreateEntityUtil entityUtil = new CreateEntityUtil();
		CreateMapperUtil daoUtil = new CreateMapperUtil();
		CreateBizUtil bizUtil = new CreateBizUtil();
		CreateMappingUtil mapperUtil = new CreateMappingUtil();
		CreateControllerUtil controllerUtil = new CreateControllerUtil();
		CreatePage createPage = new CreatePage();
		CreateResultData resultData = new CreateResultData();
		CreateSystemConstant systemConstant = new CreateSystemConstant();
		
		
		
		//导入包路径（生成的java代码的第一行)，和生成的java代码放在哪里（src下的code文件夹）
		String packagePath = "com.hy.gf";
		//获取数据库表名
		
		List<Element> tableElements = elementUtil.getTableElements();
		for (int i = 0; i < tableElements.size(); i++) {
			Element element = tableElements.get(i);
			Attribute tableNameAttribute = element.attribute("tableName");
			Attribute domainObjectNameAttribute = element.attribute("domainObjectName");
			String tableName = tableNameAttribute.getText();
			String domainObjectName = domainObjectNameAttribute.getText();
			System.out.println("数据库中的表名:"+tableName);
			System.out.println("实体类名:"+domainObjectName+"\r\n");
			entityUtil.createEntity(packagePath, tableName, domainObjectName);
			new CreateEntityVoUtil().createEntity(packagePath, tableName, domainObjectName);
//			daoUtil.createDao(packagePath, tableName, domainObjectName);
//			bizUtil.createBiz(packagePath, tableName, domainObjectName);
			mapperUtil.createMapping(packagePath, tableName, domainObjectName);
//			controllerUtil.createCtroller(packagePath, tableName, domainObjectName);
			
			
			//CreateVGM.createEntity(packagePath, tableName, domainObjectName);
			CreateUtil.createEntity(packagePath, tableName, domainObjectName);
			
//			CreateVueEdit.createEntity(packagePath, tableName, domainObjectName);
		}
		createPage.createBiz(packagePath);
		resultData.createBiz(packagePath);
		systemConstant.createBiz(packagePath);
		
		DataBaseUtils.closeDatabase();
	}
}
