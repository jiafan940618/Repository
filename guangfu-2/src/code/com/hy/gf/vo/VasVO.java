package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.Vas;


public class VasVO extends Vas {

	private List<Vas> vass;

	public void setVass(List<Vas> vass){
		this.vass=vass;
	}

	public List<Vas>  getVass(){
		return vass;
	}


}
