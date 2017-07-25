package creater;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import utils.DataBaseUtils;
import utils.StrUtil;
import utils.TypeChange;
import utils.XFileUtils;

/**
 * 生成Mapping.xml工具类
 * 
 * @author Xiang
 *
 */
public class CreateMappingUtil {
	private StringBuffer sb;
	private String packagePath;
	private String domainObjectName;
	private String tableName;
	private int size;// 共有多少列
	private String[] colnames; // 列名数组 (如：bossId)
	private String[] colTypes; // 列名类型数组
	// private int[] colSizes; // 列名大小数组
	private List<String> rsmdColnameList; // 数据库里的列名数组（如：boss_id）
	StrUtil strUtil = new StrUtil();
	TypeChange typeChange = new TypeChange();

	public void createMapping(String packagePath, String tableName, String domainObjectName)
			throws IOException, SQLException {
		sb = new StringBuffer();
		this.packagePath = packagePath;
		this.tableName = tableName;
		this.domainObjectName = domainObjectName;
		String content = createMapper();
		String path = System.getProperty("user.dir") + StrUtil.codePath + "mybatis/";
		// path = StrUtil.createFileDir(packagePath, path, "/mapping/");
		String resPath = path + domainObjectName + "Mapper.xml";
		// System.out.println("resPath=" + resPath);
		XFileUtils.writStringToFile(content, resPath);
	}

	/**
	 * 从数据库读取列名，列名类型，列名大小
	 */
	private void getDataFromDataBase() {
		Connection conn = DataBaseUtils.openConnection(); // 得到数据库连接
		PreparedStatement pstmt = null;
		ResultSetMetaData rsmd = null;
		String strsql = "select * from " + tableName;
		try {
			pstmt = conn.prepareStatement(strsql);
			rsmd = pstmt.getMetaData();
			size = rsmd.getColumnCount(); // 共有多少列
			rsmdColnameList = new ArrayList<String>();
			colnames = new String[size];
			colTypes = new String[size];
			// colSizes = new int[size];
			for (int i = 0; i < size; i++) {
				rsmdColnameList.add(rsmd.getColumnName(i + 1));
				colnames[i] = strUtil.getCamelStr(rsmd.getColumnName(i + 1));
				colTypes[i] = typeChange.sqlType2jdbcType(rsmd.getColumnTypeName(i + 1));
				if (colTypes[i].equals("INT UNSIGNED")) {
					colTypes[i] = "INTEGER";
				}
			}
			System.out.println("");
			/*
			 * for (int i = 0; i < size; i++) { if (colTypes[i].equals("INT")) {
			 * colTypes[i] = "INTEGER"; } }
			 */
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String createMapper() {
		getDataFromDataBase();
		topCotent();
		baseResultMap();
		baseColumnList();
		delete();
		select();
		insert();
		update();
		selectOneByExample();
		selectByExample();
		selectByExampleCount();
		insertBatch();
		deleteBatch();
		sb.append("\n");
		sb.append("</mapper>");
		// System.out.println(sb.toString());
		// for (String type : colTypes) {
		// System.out.println(type);
		// }
		String string = sb.toString();
		string = string.replaceAll("'", "\"");
		return string;
	}

	private void selectOneByExample() {
		sb.append("\t<select id=\"selectOneByExample\" resultMap=\"BaseResultMap\" >\r\n");
		sb.append("\t\tselect\r\n");
		sb.append("\t\t<include refid=\"Base_Column_List\" />\r\n");
		sb.append("\t\tfrom " + tableName + "\r\n");
		sb.append("\t\twhere 1=1\r\n");
		for (int i = 0; i < colnames.length - 1; i++) {
			sb.append("\t\t\t<if test=\"" + colnames[i + 1] + " != null\" >\r\n");
			sb.append("\t\t\t\tand " + rsmdColnameList.get(i + 1) + " = #{" + colnames[i + 1] + ",jdbcType="
					+ colTypes[i + 1] + "}\r\n");
			sb.append("\t\t\t</if>\r\n");
		}
		sb.append("\t\t\tlimit 0,1\r\n");
		sb.append("\t</select>\r\n");
	}

	/**
	 * 顶部内容topCotent
	 */
	private void topCotent() {
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "\r\n");
		sb.append(
				"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >"
						+ "\r\n");
		sb.append("<mapper namespace=\"" + packagePath + ".mapper." + domainObjectName + "Mapper\" >" + "\r\n");
	}

	/**
	 * baseResultMap
	 */
	private void baseResultMap() {
		sb.append("\t<resultMap id=\"BaseResultMap\" type=\"" + packagePath + ".model." + domainObjectName + "\" >"
				+ "\r\n");
		for (int i = 0; i < size; i++) {
			if (i == 0) {
				sb.append("\t\t<id column=\"" + rsmdColnameList.get(i) + "\" property=\"" + colnames[i]
						+ "\" jdbcType=\"" + colTypes[i] + "\" />" + "\r\n");
			}
			if (i >= 1) {
				sb.append("\t\t<result column=\"" + rsmdColnameList.get(i) + "\" property=\"" + colnames[i]
						+ "\" jdbcType=\"" + colTypes[i] + "\" />" + "\r\n");
			}
		}
		sb.append("\t</resultMap>\r\n");
	}

	/**
	 * Base_Column_List
	 */
	private void baseColumnList() {
		sb.append("\t<sql id=\"Base_Column_List\" >\r\n");
		String baseColName = StringUtils.join(rsmdColnameList, ",");
		sb.append("\t\t" + baseColName + "\r\n");
		sb.append("\t" + "</sql>" + "\r\n");
	}

	/**
	 * selectByPrimaryKey
	 */
	private void delete() {
		sb.append("<delete id='delete' parameterType=\"java.lang.Long\" >");
		sb.append("\n");
		sb.append("delete from " + tableName);
		sb.append("\n");
		sb.append("where id = #{id,jdbcType=INTEGER}");
		sb.append("\n");
		sb.append("</delete>");
		sb.append("\n");
	}

	/**
	 * selectByPrimaryKey
	 */
	private void select() {
		sb.append("\t<select id=\"select\" resultMap=\"BaseResultMap\" parameterType=\"java.lang.Long\" >" + "\r\n");
		sb.append("\t\tselect" + "\r\n");
		sb.append("\t\t" + "<include refid=\"Base_Column_List\" />" + "\r\n");
		sb.append("\t\t" + "from " + tableName + "\r\n");
		sb.append("\t\t" + "where " + rsmdColnameList.get(0) + " = #{" + strUtil.getCamelStr(rsmdColnameList.get(0))
				+ ",jdbcType=" + colTypes[0] + "}" + "\r\n");
		sb.append("\t</select>\r\n");
	}

	/**
	 * insertSelective
	 */
	private void insert() {

		sb.append("\t<insert id=\"insert\" parameterType=\"" + packagePath + ".model." + domainObjectName + "\" >"
				+ "\r\n");
		sb.append("\t<selectKey resultType='long' keyProperty='id' order='AFTER'>\r\n");
		sb.append("\tSELECT LAST_INSERT_ID() </selectKey>\r\n");
		sb.append("\t\tinsert into " + tableName + "\r\n");
		sb.append("\t\t<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >" + "\r\n");
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\t\t\t<if test=\"" + colnames[i] + " != null\" >" + "\r\n");
			sb.append("\t\t\t\t" + rsmdColnameList.get(i) + ",\r\n");
			sb.append("\t\t\t</if>\r\n");
		}
		sb.append("\t\t</trim>\r\n");
		sb.append("\t\t<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >" + "\r\n");
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\t\t\t<if test=\"" + colnames[i] + " != null\" >" + "\r\n");
			sb.append("\t\t\t\t#{" + colnames[i] + ",jdbcType=" + colTypes[i] + "}" + ",\r\n");
			sb.append("\t\t\t</if>\r\n");
		}
		sb.append("\t\t</trim>\r\n");
		sb.append("\t</insert>\r\n");
	}

	/**
	 * updateByPrimaryKeySelective
	 */
	private void update() {
		sb.append("\t<update id=\"update\" parameterType=\"" + packagePath + ".model." + domainObjectName + "\" >\r\n");
		sb.append("\t\tupdate " + tableName + "\r\n");
		sb.append("\t\t<set >\r\n");
		for (int i = 0; i < colnames.length - 1; i++) {
			sb.append("\t\t\t<if test=\"" + colnames[i + 1] + " != null\" >\r\n");
			sb.append("\t\t\t\t" + rsmdColnameList.get(i + 1) + " = #{" + colnames[i + 1] + ",jdbcType="
					+ colTypes[i + 1] + "},\r\n");
			sb.append("\t\t\t</if>\r\n");
		}
		sb.append("\t\t</set>\r\n");
		sb.append("\t\twhere " + rsmdColnameList.get(0) + " = #{" + colnames[0] + ",jdbcType=" + colTypes[0] + "}\r\n");
		sb.append("\t</update>\r\n");
	}

	/**
	 * selectByExample
	 */
	private void selectByExample() {
		sb.append("\t<select id=\"selectByExample\" resultMap=\"BaseResultMap\" >\r\n");
		sb.append("\t\tselect\r\n");
		sb.append("\t\t<include refid=\"Base_Column_List\" />\r\n");
		sb.append("\t\tfrom " + tableName + "\r\n");
		sb.append("\t\twhere 1=1\r\n");

		for (int i = 0; i < colnames.length - 1; i++) {
			sb.append("\t\t\t<if test=\"" + colnames[i + 1] + " != null\" >\r\n");
			sb.append("\t\t\t\tand " + rsmdColnameList.get(i + 1) + " = #{" + colnames[i + 1] + ",jdbcType="
					+ colTypes[i + 1] + "}\r\n");
			sb.append("\t\t\t</if>\r\n");
		}

		sb.append("<if test=\"#{sort} != null\">");
		sb.append("<if test=\"sort != null\">");
		sb.append("\t\t\torder by ${sort} ${sortUp}\r\n");
		sb.append("</if>");
		sb.append("</if>");

		sb.append("\t\t\tlimit #{start},#{limit}\r\n");
		sb.append("\t</select>\r\n");
	}

	/**
	 * selectByExampleCount
	 */
	private void selectByExampleCount() {
		sb.append("\t<select id=\"selectByExampleCount\" resultType=\"java.lang.Integer\">\r\n");
		sb.append("\t\tselect\r\n");
		sb.append("\t\tcount(1) as total\r\n");
		sb.append("\t\tfrom " + tableName + "\r\n");
		sb.append("\t\twhere 1=1\r\n");

		for (int i = 0; i < colnames.length - 1; i++) {
			sb.append("\t\t<if test=\"" + colnames[i + 1] + " != null\" >\r\n");
			sb.append("\t\t\tand " + rsmdColnameList.get(i + 1) + " = #{" + colnames[i + 1] + ",jdbcType="
					+ colTypes[i + 1] + "}\r\n");
			sb.append("\t\t</if>\r\n");
		}

		sb.append("\t</select>\r\n");
	}

	private void insertBatch() {
		sb.append("<insert id='insertBatch' useGeneratedKeys='true' parameterType='java.util.List'>");
		sb.append("\t<selectKey resultType='long' keyProperty='id' order='AFTER'>\r\n");
		sb.append("\tSELECT LAST_INSERT_ID() </selectKey>\r\n");
		sb.append("insert into " + tableName + "\r\n");
		sb.append("(");
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < colnames.length; i++) {
			// 跳过id跳过创建时间
			if (colnames[i].equals("id") || colnames[i].equals("createDtm")) {
				continue;
			}
			builder.append(rsmdColnameList.get(i) + ",");
		}
		builder.replace(builder.length() - 1, builder.length(), "");
		sb.append(builder);
		sb.append(")");
		sb.append("values");
		sb.append("<foreach collection='list' item='item' index='index' separator=','>");
		sb.append("(");
		builder = new StringBuilder();
		for (int i = 0; i < colnames.length - 1; i++) {
			// 跳过id跳过创建时间
			if (colnames[i].equals("id") || colnames[i].equals("createDtm")) {
				continue;
			}
			builder.append("#{item." + colnames[i] + "},");
		}
		builder.replace(builder.length() - 1, builder.length(), "");
		sb.append(builder);
		sb.append(")");
		sb.append("\t</foreach>\r\n");
		sb.append("\t</insert>\r\n");

	}

	/**
	 * selectByExampleCount
	 */
	private void deleteBatch() {
		sb.append("<delete id='deleteBatch' parameterType='java.util.List'>");
		sb.append("delete from " + tableName + " where id in");
		sb.append("<foreach collection='list' item='item' open='(' separator=',' close=')'>");
		sb.append("#{item}");
		sb.append("</foreach>");
		sb.append("</delete>");
	}
}
