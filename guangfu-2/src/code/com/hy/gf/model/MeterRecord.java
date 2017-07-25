package com.hy.gf.model;

import java.util.*;

public class MeterRecord {

	private Long id;

	private String station_code;

	private String c_addr;

	private Long d_addr;

	private Integer d_type;

	private Integer w_addr;

	private String meter_state; // 电表状态

	private Date meter_time;

	private Date createDtm;

	public Long getD_addr() {
		return d_addr;
	}

	public void setD_addr(Long d_addr) {
		this.d_addr = d_addr;
	}

	public Integer getD_type() {
		return d_type;
	}

	public void setD_type(Integer d_type) {
		this.d_type = d_type;
	}

	public Integer getW_addr() {
		return w_addr;
	}

	public void setW_addr(Integer w_addr) {
		this.w_addr = w_addr;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setCreateDtm(Date createDtm) {
		this.createDtm = createDtm;
	}

	public Date getCreateDtm() {
		return createDtm;
	}

	public String getStation_code() {
		return station_code;
	}

	public void setStation_code(String station_code) {
		this.station_code = station_code;
	}

	public String getC_addr() {
		return c_addr;
	}

	public void setC_addr(String c_addr) {
		this.c_addr = c_addr;
	}

	public String getMeter_state() {
		return meter_state;
	}

	public void setMeter_state(String meter_state) {
		this.meter_state = meter_state;
	}

	public Date getMeter_time() {
		return meter_time;
	}

	public void setMeter_time(Date meter_time) {
		this.meter_time = meter_time;
	}

}
