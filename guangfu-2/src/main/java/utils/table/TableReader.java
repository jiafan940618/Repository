package utils.table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.DataBaseUtils;

public class TableReader {
	private static Connection con = null;

	
	/***
	 * 打印test
	 * 
	 * @throws SQLException
	 */
	public static TableModel sysoutStrTablePdmCloumns(String Table){
		try {
			DatabaseMetaData databaseMetaData = DataBaseUtils.openConnection().getMetaData();
			ResultSet columnSet = databaseMetaData.getColumns(null, "%", Table, "%");
			if (null != columnSet) {
				TableModel table =new TableModel();
				while (columnSet.next()) {
					// 列名
					String colName = columnSet.getString("COLUMN_NAME");
					table.addName(colName);
					//是否允许使用 NULL。
					int NULLABLE = columnSet.getInt("NULLABLE");
					table.addNullable(NULLABLE);
					// 列的大小。对于 char 或 date 类型，列的大小是最大字符数，对于 numeric 和 decimal 类型，列的大小就是精度。
					Long colSize = columnSet.getLong("COLUMN_SIZE");
					table.addSize(colSize);
					// 备注
					String remarks = columnSet.getString("REMARKS");
					table.addRemark(remarks);
					// 列类型
					int colIntType = columnSet.getInt("DATA_TYPE");
					String colIntTypeString =TableConstant.getType(colIntType);
					table.addType(colIntTypeString);
//					System.out.println(colIntType);
				}
				return table;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws SQLException {

		sysoutStrTablePdmCloumns("test");

	}
}
