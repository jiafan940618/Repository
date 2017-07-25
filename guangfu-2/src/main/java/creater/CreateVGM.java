package creater;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.FileUtil;
import utils.StrUtil;
import utils.XFileUtils;
import utils.label.XLabel;
import utils.table.TableModel;
import utils.table.TableReader;

public class CreateVGM {
	
	private static String[] colnames; // 列名数组
	private static String[] colTypes; // 列名类型数组
	private static Long[] colSizes; // 列名大小数组
	private static String modelName;
	private static String[] reColth={"del","delDtm"};
	private static String[] reColtd={"id","del","delDtm"};
	
	public static void createEntity(String packagePath, String tableName, String domainObjectName) {
		modelName=domainObjectName;
		String readFileByLines = FileUtil.readFileByLines("src/main/java/template/manage.vue");
		
		StringBuilder forStringItem2 = new StringBuilder();
		
		TableModel tableModel= TableReader.sysoutStrTablePdmCloumns(tableName);
		colnames = new String[tableModel.getTableSize()];
		colTypes = new String[tableModel.getTableSize()];
		colSizes = new Long[tableModel.getTableSize()];
		String[] remarks = new String[tableModel.getTableSize()];
		tableModel.getRemarks().toArray(remarks);
		tableModel.getNamePropertys().toArray(colnames);
		tableModel.getTypes().toArray(colTypes);
		tableModel.getSizes().toArray(colSizes);
		for (int i = 0; i < colnames.length; i++) {
			if (getRecol(reColth,colnames[i])) {
				continue;
			}
			forStringItem2.append("\n                  <th v-on:click=\"sort='"+colnames[i]+"'\">"+getValue(remarks[i])+"</th>");
		}
		String content = readFileByLines.replace(XLabel.$_for, forStringItem2);
		content =createTd(content);
		
//		System.out.println(content);
	
		String path = System.getProperty("user.dir") + StrUtil.codePath + "vue/";
//		path = StrUtil.createFileDir(packagePath, path, "/mapping/");
		String resPath = path+ domainObjectName + ".vue";
		// System.out.println("resPath=" + resPath);
//		System.out.println("resPath=" + resPath);
		try {
			XFileUtils.writStringToFile2(content, resPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理td
	 * @param content
	 * @return
	 */
	private static String createTd(String content) {
		StringBuilder replacement = new StringBuilder();
		
//		List<String> asList = Arrays.asList(reCol);
		String nameCol="";
		for (int i = 0; i < colnames.length; i++) {
			//name不生成
			if (nameCol.equals("")&&colnames[i].toLowerCase().contains("name")) {
				nameCol=colnames[i];
				continue;
			}
			if (getRecol(reColtd,colnames[i])) {
				continue;
			}
			if (colnames[i].contains("Dtm")) {
				replacement.append("                  <td>{{item."+colnames[i]+" | timeFormat}}</td>\n");
			}else{
				replacement.append("                  <td>{{item."+colnames[i]+"}}</td>\n");
			}
		}
		content=content.replace(XLabel.fortd, replacement);
		content = content.replace(XLabel.model.name, modelName);
		
		content = content.replace(XLabel.model.item.name, nameCol);
		return content;
	}

	/**
	 * 是否是忽略字段
	 * @param col
	 * @return
	 */
	private static boolean getRecol(String reCol[],String col) {
		for (int j = 0; j < reCol.length; j++) {
			if (reCol[j].equals(col)) {
				return true;
			}
		}
		return false;
	}

	private static String getValue(String value) {
		int indexOf = value.indexOf("[");
		int indexOf2 = value.indexOf("]");
		if (indexOf==-1||indexOf2==-1) {
			return "";
		}
		String substring = value.substring(indexOf+1, indexOf2);
		return substring;
	}
}
