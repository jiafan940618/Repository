package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.News;


public class NewsVO extends News {

	private List<News> newss;

	public void setNewss(List<News> newss){
		this.newss=newss;
	}

	public List<News>  getNewss(){
		return newss;
	}


}
