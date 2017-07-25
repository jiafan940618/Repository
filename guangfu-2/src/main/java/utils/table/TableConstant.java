package utils.table;

public class TableConstant {
	public static int[] date_type_int = { -6, 4, -5, 12, -1, -4, -4, 91, 93, 92, 93,8,3 };
	public static String[] date_type_String = { "tinyint", "int", "bigint", "varchar", "text", "longblob", "blob",
			"date", "datetime", "time", "timestamp","double","decimal" };
	public static String getType(int type_int) {
		for (int i = 0; i < date_type_int.length; i++) {
			if (date_type_int[i] == type_int) {
				return date_type_String[i];
			}
		}
		return "";
	}
}
