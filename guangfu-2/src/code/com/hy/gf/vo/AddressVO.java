package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.Address;


public class AddressVO extends Address {

	private List<Address> addresss;

	public void setAddresss(List<Address> addresss){
		this.addresss=addresss;
	}

	public List<Address>  getAddresss(){
		return addresss;
	}


}
