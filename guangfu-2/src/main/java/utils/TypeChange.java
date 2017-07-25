package utils;

/**
 * 类型转换
 * @author Xiang
 *
 */
public class TypeChange {

	
	
	/**
	 * 数据库类型转MyBatis类型
	 * @param sqlType
	 * @return
	 */
	public String sqlType2jdbcType(String sqlType) {

		if(sqlType.equalsIgnoreCase("int")) {
			return "INTEGER";
		}
		if (sqlType.equalsIgnoreCase("timestamp") || sqlType.equalsIgnoreCase("DATETIME")) {
			return "TIMESTAMP";
		}

		return sqlType;
	}

}
