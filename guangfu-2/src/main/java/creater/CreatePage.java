package creater;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import utils.StrUtil;
import utils.XFileUtils;



/**
 * 生成Page工具类
 * 
 * @author Xiang
 *
 */
public class CreatePage {

	public void createBiz(String packagePath) throws IOException {
		String content = parse(packagePath);
		String path = System.getProperty("user.dir") + StrUtil.codePath + packagePath.replaceAll("\\.", "/");
		path = StrUtil.createFileDir(packagePath, path,"/model/");
		String resPath = path + "Page.java";
//		System.out.println("resPath=" + resPath);
		XFileUtils.writStringToFile(content, resPath);
	}

	/**
	 * 解析处理(生成业务层代码)
	 */
	private String parse(String packagePath) {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + packagePath + ".model;\r\n\r\n");
		sb.append("import java.util.List;\r\n\r\n");
		sb.append("import " + packagePath + ".util." + "SystemConstant" + ";\r\n");
		// sb.append("@Service\r\n");
		sb.append("public class Page<T> {\r\n\r\n");


		sb.append("\tprivate Integer limit=SystemConstant.limit;\r\n");
		sb.append("\tprivate List<T> list;\r\n");
		sb.append("\tprivate T example;\r\n\r\n");
		
		sb.append("\tString sort=\"create_dtm\";\r\n");
		sb.append("\tString sortUp=\"desc\";\r\n");
		
		sb.append("\tInteger total=0;\r\n");
		sb.append("\tInteger	start=0;\r\n");
		sb.append("\tInteger index=-1;\r\n\r\n");
		
		/*public List<T> getList() {
			return list;
		}*/
		sb.append("\tpublic List<T> getList() {\r\n\t\treturn list;\r\n\t}\r\n");
		
		/*public void setList(List<T> list) {
			this.list = list;
		}*/
		sb.append("\tpublic void setList(List<T> list) {\r\n\t\tthis.list = list;\r\n\t}\r\n\r\n");
		
		/*public Integer getTotal() {
			return total;
		}*/
		sb.append("\tpublic Integer getTotal() {\r\n\t\treturn total;\r\n\t}\r\n");
		/*public void setTotal(Integer total) {
			this.total = total;
		}*/
		sb.append("\tpublic void setTotal(Integer total) {\r\n\t\tthis.total = total;\r\n\t}\r\n\r\n");
		
		/*public Integer getStart() {
			return start;
		}*/
		sb.append("\tpublic Integer getStart() {\r\n\t\treturn start;\r\n\t}\r\n");
		
		/*public void setStart(Integer start) {
			this.start = start;
		}*/
		sb.append("\tpublic void setStart(Integer start) {\r\n\t\tthis.start = start;\r\n\t}\r\n\r\n");
		
		/*public Integer getLimit() {
			return limit;
		}*/
		sb.append("\tpublic Integer getLimit() {\r\n\t\treturn limit;\r\n\t}\r\n");
		
		/*public void setLimit(Integer limit) {
			this.limit = limit;
			if (index!=-1) {
				start = limit*(index-1);
			}
		}*/
		sb.append("\tpublic void setLimit(Integer limit) {\r\n\t\tthis.limit = limit;\r\n\t\tif (index!=-1) {"
				+ "\r\n\t\t\tstart = limit*(index-1);\r\n\t\t}\r\n\t}\r\n\r\n");
		
		/*public Integer getIndex() {
			return index;
		}*/
		sb.append("\tpublic Integer getIndex() {\r\n\t\treturn index;\r\n\t}\r\n");
		
		/*public void setIndex(Integer index) {
			this.index = index;
			start = limit*(index-1);
		}*/
		sb.append("\tpublic void setIndex(Integer index) {\r\n\t\tthis.index = index;\r\n\t\tstart = limit*(index-1);\r\n\t}\r\n\r\n");
		
		/*public T getExample() {
			return example;
		}*/
		sb.append("\tpublic T getExample() {\r\n\t\treturn example;\r\n\t}\r\n");
		
		/*public void setExample(T example) {
			this.example = example;
		}*/
		sb.append("\tpublic void setExample(T example) {\r\n\t\tthis.example = example;\r\n\t}\r\n\r\n");
		
		sb.append("public String getSort() {return sort;}public void setSort(String sort) {this.sort = sort;}public String getSortUp() {return sortUp;}public void setSortUp(String sortUp) {this.sortUp = sortUp;}");

		sb.append("}");
		
//		System.out.println(sb.toString());
		return sb.toString();

	}
}