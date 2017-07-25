package com.hy.gf.job;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hy.gf.util.DateUtil;

/** 
 * 备份数据库用，直接右键运行该类，数据库文件备份到d盘dbBack文件夹下
 */
//@Component("DbBackJob")
public class DbBackJob {

	public static void main(String[] args) {
		DbBackJob dbj = new DbBackJob();
		dbj.backAndDelTables();
	}
	
	/**
	 * 每个月1号，备份和删除上个月的电表数据
	 */
//	 每月2号上午0点0分0秒触发 
//	@Scheduled(fixedDelay = 100000 * 1000)
//	@Scheduled(cron="0 0 0 2 * ?")
	private void backAndDelTables(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1); // 这里设置需要备份的月份，-1代表上个月
		String fold_name = "youneng" +DateUtil.formatDate(cal.getTime(), "yyyy_MM");
		
		Connection conn = null;
		Statement st = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String hostIP = "120.76.98.74";
			String userName = "root";
			String password = "Engross0812";
			String savePath = "D:\\dbBack\\youneng\\" + fold_name; // 备份的路径
			String fileName = "";
			String databaseName = "youneng";
			String tableName = "";
			String jdbcurl = "jdbc:mysql://" + hostIP + ":3306/" + databaseName + "?useUnicode=true&characterEncoding=utf-8";
			
			conn = DriverManager.getConnection(jdbcurl, userName, password);
			st = conn.createStatement();
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM");
			// 得到上个月的年份和月份
			String lastMonth = sdf.format(cal.getTime());
	
			List<String> mysqlTableNames = checkTable(userName, password, jdbcurl);
			// 得到上个月的所有表 tables
			List<String> tables = new ArrayList<>();
			for (String n : mysqlTableNames) {
				if (n.contains("phase_" + lastMonth)) {
					tables.add(n);
				}
			}
			
			if(tables.size() > 0 ){
				for (String n : tables) {
					fileName = n + ".sql";
					tableName = n;
					if (exportDatabaseTool(hostIP, userName, password, savePath, fileName, databaseName, tableName)) {
						System.out.println(tableName + "：备份成功");
						// 删除数据库中的表
						// deleteTable(conn,st, n);
					} else {
						System.out.println(tableName + "：备份失败");
					}
				}
			} else{
				System.out.println("数据库中没有可备份和删除的表");
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e1) {
			e1.printStackTrace();
		} finally{
			close(conn, st, null);
		}
	}

	/**
	 * Java代码实现MySQL数据库导出
	 * 
	 * @author GaoHuanjie
	 * @param hostIP
	 *            MySQL数据库所在服务器地址IP
	 * @param userName
	 *            进入数据库所需要的用户名
	 * @param password
	 *            进入数据库所需要的密码
	 * @param savePath
	 *            数据库导出文件保存路径
	 * @param fileName
	 *            数据库导出文件文件名
	 * @param databaseName
	 *            要导出的数据库名
	 * @param tableName
	 *            要导出的表名
	 * @return 返回true表示导出成功，否则返回false。
	 */
	private boolean exportDatabaseTool(String hostIP, String userName, String password, String savePath,
			String fileName, String databaseName, String tableName) {
		File saveFile = new File(savePath);
		if (!saveFile.exists()) {// 如果目录不存在
			saveFile.mkdirs();// 创建文件夹
		}
		if (!savePath.endsWith(File.separator)) {
			savePath = savePath + File.separator;
		}

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("mysqldump").append(" --opt").append(" -h").append(hostIP);
		stringBuilder.append(" --user=").append(userName).append(" --password=").append(password)
				.append(" --lock-all-tables=true");
		stringBuilder.append(" --result-file=").append(savePath + fileName).append(" --default-character-set=utf8");
		stringBuilder.append(" " + databaseName);
		stringBuilder.append(" " + tableName);
		try {
			Process process = Runtime.getRuntime().exec(stringBuilder.toString());
			if (process.waitFor() == 0) {// 0 表示线程正常终止。
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 删除表
	 * @param conn
	 * @param tableName
	 */
	private void deleteTable(Connection conn, Statement st, String tableName) {
		try {
			st = conn.createStatement();
			/*
			 * 删除表格 drop table student1;
			 */
			String sqlstr = "drop table " + tableName + ";";
			st.executeUpdate(sqlstr);
			System.out.println(tableName + "删除成功");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 查找数据库中所有表的表名
	 * 
	 * @param userName
	 * @param password
	 * @param jdbcurl
	 * @param tableName
	 * @return
	 */
	public static List<String> checkTable(String userName, String password, String jdbcurl)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<String> tableNames = new ArrayList<>();

		Connection connection = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(jdbcurl, userName, password);
			DatabaseMetaData md = connection.getMetaData();
			rs = md.getTables(null, null, "%", null);
			while (rs.next()) {
				tableNames.add(rs.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, null, rs);
		}
		return tableNames;
	}

	/**
	 * 关闭的一些操作 , 优化
	 * 
	 * @param conn
	 * @param stat
	 * @param rs
	 */
	private static void close(Connection conn, Statement stat, ResultSet rs) {
		try {
			if (conn != null) {
				conn.close();
			}
			if (stat != null) {
				stat.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
