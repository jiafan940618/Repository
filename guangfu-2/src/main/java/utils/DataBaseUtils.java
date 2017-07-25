package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import utils.GetElementUtil;

/**
 * 数据库工具类
 * @author Xiang
 *
 */
public class DataBaseUtils {
	
	private static Connection conn = null;
	/**
	 * 连接数据库
	 * @return
	 */
    public static Connection openConnection() {
    	if (conn!=null) {
			return conn;
		}
    	GetElementUtil getElement = new GetElementUtil();
    	String url = getElement.getUrl();
    	System.out.println(url);
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("成功加载MySQL驱动程序");
			conn = DriverManager.getConnection(url);
			if(conn != null) {
				System.out.println("连接数据库成功");
			} else {
				System.out.println("连接数据库失败");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	return conn;
    }
    

    /**
     * 关闭数据库
     * @param conn
     * @param pstmt
     * @param rs
     */
	public static void closeDatabase() {
		if(conn != null) {
    		try {
				conn.close();
				System.out.println("关闭数据库成功");
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
		
	}
}
