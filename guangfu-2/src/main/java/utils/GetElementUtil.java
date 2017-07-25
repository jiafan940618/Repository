package utils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.*;
import org.dom4j.io.*;


/**
 * 读取xml工具
 * @author Xiang
 *
 */
public class GetElementUtil {
	
    
    /*
     * 获取连接数据库的url
     */
    public String getUrl() {
    	SAXReader sax = new SAXReader();
    	String url = null;
    	
    	try {
			Document document = sax.read("src/main/java/resources/generatorConfig.xml");
			Element rootElement = document.getRootElement();
			Element contextElement = rootElement.element("context");
			Element jdbcConnectionElement = contextElement.element("jdbcConnection");
			Attribute connectionURLAttribute = jdbcConnectionElement.attribute("connectionURL");
			Attribute userIdAttribute = jdbcConnectionElement.attribute("userId");
			Attribute passwordAttribute = jdbcConnectionElement.attribute("password");
			String connectionURLValue = connectionURLAttribute.getText();
			String userIdValue = userIdAttribute.getText();
			String passwordValue = passwordAttribute.getText();
			url = connectionURLValue+"?user="+userIdValue+"&password="+passwordValue+"&useUnicode=true&characterEncoding=UTF8";
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    	return url;
    }
    
    /**
	 * 获取table节点元素
	 */
    public List<Element> getTableElements() {
    	SAXReader sax = new SAXReader();
    	Map<String, String> elementNameMap = new HashMap<String, String>();
    	try {
			Document document = sax.read("src/main/java/resources/generatorConfig.xml");
			Element rootElement = document.getRootElement();
			Element contextElement = rootElement.element("context");
			
			List<Element> elements = contextElement.elements("table");
			return elements;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
	 * 获取table节点元素
	 */
    public Map<String, String> getTableElement() {
    	SAXReader sax = new SAXReader();
    	Map<String, String> elementNameMap = new HashMap<String, String>();
    	try {
			Document document = sax.read("src/main/java/resources/generatorConfig.xml");
			Element rootElement = document.getRootElement();
			Element contextElement = rootElement.element("context");
			Element tableElement = contextElement.element("table");
			Attribute tableNameAttribute = tableElement.attribute("tableName");
			Attribute domainObjectNameAttribute = tableElement.attribute("domainObjectName");
//			Attribute enableCountByExampleAttribute = tableElement.attribute("enableCountByExample");
//			Attribute enableUpdateByExampleAttribute = tableElement.attribute("enableUpdateByExample");
//			Attribute enableDeleteByExampleAttribute = tableElement.attribute("enableDeleteByExample");
//			Attribute enableSelectByExampleAttribute = tableElement.attribute("enableSelectByExample");
//			Attribute selectByExampleQueryIdAttribute = tableElement.attribute("selectByExampleQueryId");
			String tableName = tableNameAttribute.getText();
			String domainObjectName = domainObjectNameAttribute.getText();
			elementNameMap.put("tableName", tableName);
			elementNameMap.put("domainObjectName", domainObjectName);
			return elementNameMap;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
}

