package utils.label;

public class XLabel {
	public static String $_for="$:forth";
	public static String fortd = "$:fortd";
	public static String fordiv = "$:fordiv";
	
	public interface model{
		public static String name="$:model.name";
		public static String item_label="$:model.item.label";
		public static String item_key="$:model.item.key";
		public static String item_valeu="$:model.item.valeu";
		public interface item{
			public static String name="$:model.item.name";
		}
	}
}
