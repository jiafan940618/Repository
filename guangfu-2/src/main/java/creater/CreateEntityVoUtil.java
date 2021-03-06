package creater;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import utils.DataBaseUtils;
import utils.StrUtil;
import utils.XFileUtils;

public class CreateEntityVoUtil {

	private String[] colnames; // 列名数组
	private String[] colTypes; // 列名类型数组
	private int[] colSizes; // 列名大小数组
	private boolean f_util = true; // 是否需要导入包java.util.*
	private boolean f_sql = false; // 是否需要导入包java.sql.*
	private boolean f_math = false;
	StrUtil strUtil = new StrUtil();
	private String modelName = "VO";
	private String domainObjectName;

	public void createEntity(String packagePath, String tableName, String domainObjectName) throws IOException {
		Connection conn = DataBaseUtils.openConnection(); // 得到数据库连接
		PreparedStatement pstmt = null;
		ResultSetMetaData rsmd = null;
		this.domainObjectName=domainObjectName;
		String strsql = "select * from " + tableName;
		try {
			pstmt = conn.prepareStatement(strsql);
			rsmd = pstmt.getMetaData();
			int size = rsmd.getColumnCount(); // 共有多少列
			colnames = new String[size];
			colTypes = new String[size];
			colSizes = new int[size];
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				colnames[i] = strUtil.getCamelStr(rsmd.getColumnName(i + 1));
				colTypes[i] = rsmd.getColumnTypeName(i + 1);
				// 如果字段名称等于id 类型为long
				if (colnames[i].equals("id")) {
					colTypes[i] = "bigint";
				}
				if (colTypes[i].equalsIgnoreCase("datetime") || colTypes[i].equalsIgnoreCase("timestamp")) {
					f_util = true;
				}
				if (colTypes[i].equalsIgnoreCase("image") || colTypes[i].equalsIgnoreCase("text")) {
					f_sql = true;
				}
				if (colTypes[i].equalsIgnoreCase("DECIMAL")) {
					f_math = true;
				}
				colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
			}
			String content = parse(packagePath, domainObjectName);
			String path = System.getProperty("user.dir") + StrUtil.codePath + packagePath.replaceAll("\\.", "/");
			path = StrUtil.createFileDir(packagePath, path, "/"+modelName.toLowerCase()+"/");
			String resPath = path + "/" + domainObjectName +modelName+ ".java";
			// System.out.println("resPath=" + resPath);
			XFileUtils.writStringToFile(content, resPath);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rsmd != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 解析处理(生成实体类主体代码)
	 */
	private String parse(String packagePath, String domainObjectName) {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + packagePath + "."+modelName.toLowerCase()+";\r\n\r\n");
		if (f_util) {
			sb.append("import java.util.*;\r\n");
		}
		if (f_math) {
			sb.append("import java.math.*;\r\n");
		}
		if (f_sql) {
			sb.append("import java.sql.*;\r\n");
		}
		sb.append("import " + packagePath + ".model." + domainObjectName + ";\r\n");
		sb.append("\r\n\r\n");
		sb.append("public class " + domainObjectName +modelName+ " extends "+domainObjectName+" {\r\n\r\n");
		processAllAttrs(sb);
		sb.append("\r\n");
		processAllMethod(sb);
		sb.append("}\r\n");
		// System.out.println(sb.toString());
		return sb.toString();

	}

	/**
	 * 生成所有的方法
	 * 
	 * @param sb
	 */
	private void processAllMethod(StringBuffer sb) {

	}

	/**
	 * 解析输出属性
	 * 
	 * @return
	 */
	private void processAllAttrs(StringBuffer sb) {
		String lCasseModel = StrUtil.smallCase(domainObjectName)+"s";
		sb.append("\tprivate List<"+domainObjectName+"> "+lCasseModel+";\r\n\r\n");
		
		sb.append("\tpublic void set" + domainObjectName + "s(List<"+domainObjectName+"> "+lCasseModel+"){\r\n");
		sb.append("\t\tthis." + lCasseModel + "=" + lCasseModel + ";\r\n");
		sb.append("\t}\r\n\r\n");

		sb.append("\tpublic List<"+domainObjectName+"> "+ " get" + domainObjectName + "s(){\r\n");
		sb.append("\t\treturn " + lCasseModel + ";\r\n");
		sb.append("\t}\r\n\r\n");
		
//		for (int i = 0; i < colnames.length; i++) {
//			sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + colnames[i] + ";\r\n\r\n");
//		}
	}

	/**
	 * 根据数据库表的类型，生成java属性的类型
	 * 
	 * @param sqlType
	 * @return
	 */
	private String sqlType2JavaType(String sqlType) {
		if (sqlType.equalsIgnoreCase("bit")) {
			return "bool";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "short";
		} else if (sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("integer")
				|| sqlType.equalsIgnoreCase("INT UNSIGNED")) {
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "Long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "float";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("money") || sqlType.equalsIgnoreCase("smallmoney")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("longtext")
				|| sqlType.equalsIgnoreCase("nchar")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime") || sqlType.equalsIgnoreCase("timestamp")
				|| sqlType.equalsIgnoreCase("date") || sqlType.equalsIgnoreCase("time")) {
			return "Date";
		}

		else if (sqlType.equalsIgnoreCase("image")) {
			return "Blob";
		} else if (sqlType.equalsIgnoreCase("text")) {
			return "Clob";
		} else if (sqlType.equalsIgnoreCase("DOUBLE")) {
			return "Double";
		}
		return null;
	}

}
