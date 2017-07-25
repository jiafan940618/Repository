package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.Information;


public class InformationVO extends Information {

	private List<Information> informations;

	public void setInformations(List<Information> informations){
		this.informations=informations;
	}

	public List<Information>  getInformations(){
		return informations;
	}


}
