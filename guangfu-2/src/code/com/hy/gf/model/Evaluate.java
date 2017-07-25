package com.hy.gf.model;

import java.util.*;
import java.math.*;


public class Evaluate {

	private Long id;

	private Long orderId;

	private Long userId;

	private Long server_id;

	private Double serverQuality;

	private Double serverSpeed;
	
	private String content; //评价内容

	private Date createDtm;
	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getServer_id() {
		return server_id;
	}

	public void setServer_id(Long server_id) {
		this.server_id = server_id;
	}

	public void setServerQuality(Double serverQuality){
		this.serverQuality=serverQuality;
	}

	public Double getServerQuality(){
		return serverQuality;
	}

	public void setServerSpeed(Double serverSpeed){
		this.serverSpeed=serverSpeed;
	}

	public Double getServerSpeed(){
		return serverSpeed;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
