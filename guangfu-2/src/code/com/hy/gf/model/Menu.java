package com.hy.gf.model;

import java.util.*;


public class Menu {

	private Long id;

	private Long parentId;

	private Integer level;

	private Integer rank;

	private String name;

	private String imgUrl;

	private String path;

	private String remake;

	private Date createDtm;


	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setParentId(Long parentId){
		this.parentId=parentId;
	}

	public Long getParentId(){
		return parentId;
	}

	public void setLevel(Integer level){
		this.level=level;
	}

	public Integer getLevel(){
		return level;
	}

	public void setRank(Integer rank){
		this.rank=rank;
	}

	public Integer getRank(){
		return rank;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setImgUrl(String imgUrl){
		this.imgUrl=imgUrl;
	}

	public String getImgUrl(){
		return imgUrl;
	}

	public void setPath(String path){
		this.path=path;
	}

	public String getPath(){
		return path;
	}

	public void setRemake(String remake){
		this.remake=remake;
	}

	public String getRemake(){
		return remake;
	}

	public void setCreateDtm(Date createDtm){
		this.createDtm=createDtm;
	}

	public Date getCreateDtm(){
		return createDtm;
	}

}
