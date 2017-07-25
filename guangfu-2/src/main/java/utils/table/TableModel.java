package utils.table;

import java.util.ArrayList;
import java.util.List;

import utils.StrUtil;

public class TableModel {

	private List<String> names = new ArrayList<>(); // 列名数组
	
	private List<String> namePropertys = new ArrayList<>(); // 列名数组
	private List<String> types = new ArrayList<>(); // 列名类型数组
	private List<Long> sizes = new ArrayList<>(); // 列名大小数组
	private List<Integer> nullable = new ArrayList<>(); //是否允许空
	private List<String> remarks = new ArrayList<>(); //是否允许空

	public int getTableSize() {
		return names.size();
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> colnames) {
		this.names = colnames;
	}

	public List<String> getNamePropertys() {
		return namePropertys;
	}

	public void setNamePropertys(List<String> namePropertys) {
		this.namePropertys = namePropertys;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> colTypes) {
		this.types = colTypes;
	}

	public List<Long> getSizes() {
		return sizes;
	}

	public void setSizes(List<Long> colSizes) {
		this.sizes = colSizes;
	}

	public List<String> addName(String colname) {
		names.add(colname);
		namePropertys.add(StrUtil.getCamelStr(colname));
		return names;
	}

	public List<String> addType(String colType) {
		types.add(colType);
		return types;
	}

	public List<Long> addSize(Long colSize) {
		sizes.add(colSize);
		return sizes;
	}
	
	public List<Integer> addNullable(Integer nullable) {
		this.nullable.add(nullable);
		return this.nullable;
	}
	
	public List<String> addRemark(String remarks) {
		this.remarks.add(remarks);
		return this.remarks;
	}

	public List<Integer> getNullable() {
		return nullable;
	}

	public void setNullable(List<Integer> nullable) {
		this.nullable = nullable;
	}

	public List<String> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<String> remarks) {
		this.remarks = remarks;
	}
	
}
