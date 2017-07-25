package com.hy.gf.model;

import java.util.*;


public class MeterErrcode {

	private Long id;

	private String err_code;

	private String err_reason;

	private Integer is_normal;

	private Date createDtm;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_reason() {
		return err_reason;
	}

	public void setErr_reason(String err_reason) {
		this.err_reason = err_reason;
	}

	public Integer getIs_normal() {
		return is_normal;
	}

	public void setIs_normal(Integer is_normal) {
		this.is_normal = is_normal;
	}

	public Date getCreateDtm() {
		return createDtm;
	}

	public void setCreateDtm(Date createDtm) {
		this.createDtm = createDtm;
	}

}
