package com.hy.gf.model;

import java.util.*;


public class Push {

	private Long id;

	private Integer userId;

	private String title;

	private Integer type;

	private String content;

	private Integer isPush;

	private Integer isRead;

	private Date pushDtm;

	private Date createDtm;


	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setUserId(Integer userId){
		this.userId=userId;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setTitle(String title){
		this.title=title;
	}

	public String getTitle(){
		return title;
	}

	public void setType(Integer type){
		this.type=type;
	}

	public Integer getType(){
		return type;
	}

	public void setContent(String content){
		this.content=content;
	}

	public String getContent(){
		return content;
	}

	public void setIsPush(Integer isPush){
		this.isPush=isPush;
	}

	public Integer getIsPush(){
		return isPush;
	}

	public void setIsRead(Integer isRead){
		this.isRead=isRead;
	}

	public Integer getIsRead(){
		return isRead;
	}

	public void setPushDtm(Date pushDtm){
		this.pushDtm=pushDtm;
	}

	public Date getPushDtm(){
		return pushDtm;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
