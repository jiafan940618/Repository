package com.hy.gf.model;

import java.util.*;


public class Information {

	private Long id;

	private Integer clientid;
	
	private String subhead;

	private String url;

	private String author;

	private String title;

	private String content;

	private String imgUrl;

	private Integer browse;

	private Integer praise;

	private Date createDtm;
	
	private Integer type; // 资讯类型：1光伏资讯，2优能动态


	public String getSubhead() {
		return subhead;
	}

	public void setSubhead(String subhead) {
		this.subhead = subhead;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return id;
	}

	public void setClientid(Integer clientid){
		this.clientid=clientid;
	}

	public Integer getClientid(){
		return clientid;
	}

	public void setUrl(String url){
		this.url=url;
	}

	public String getUrl(){
		return url;
	}

	public void setAuthor(String author){
		this.author=author;
	}

	public String getAuthor(){
		return author;
	}

	public void setTitle(String title){
		this.title=title;
	}

	public String getTitle(){
		return title;
	}

	public void setContent(String content){
		this.content=content;
	}

	public String getContent(){
		return content;
	}

	public void setImgUrl(String imgUrl){
		this.imgUrl=imgUrl;
	}

	public String getImgUrl(){
		return imgUrl;
	}

	public void setBrowse(Integer browse){
		this.browse=browse;
	}

	public Integer getBrowse(){
		return browse;
	}

	public void setPraise(Integer praise){
		this.praise=praise;
	}

	public Integer getPraise(){
		return praise;
	}

	public Date getCreateDtm() {
		return createDtm;
	}

	public void setCreateDtm(Date createDtm) {
		this.createDtm = createDtm;
	}

}
