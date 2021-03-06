package com.hy.gf.model;

import java.util.List;

import com.hy.gf.util.SystemConstant;

public class Page<T> {

	private Integer limit = SystemConstant.limit;
	private List<T> list;
	private T example;

	String sort = "create_dtm";
	String sortUp = "desc";
	Integer total = 0;
	Integer start = 0;
	Integer index = -1;

	private String time_from;
	private String time_to;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
		if (index != -1) {
			start = limit * (index - 1);
		}
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
		start = limit * (index - 1);
	}

	public T getExample() {
		return example;
	}

	public void setExample(T example) {
		this.example = example;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getSortUp() {
		return sortUp;
	}

	public void setSortUp(String sortUp) {
		this.sortUp = sortUp;
	}

	public String getTime_from() {
		return time_from;
	}

	public void setTime_from(String time_from) {
		this.time_from = time_from;
	}

	public String getTime_to() {
		return time_to;
	}

	public void setTime_to(String time_to) {
		this.time_to = time_to;
	}
}