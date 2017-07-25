package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.Push;


public class PushVO extends Push {

	private List<Push> pushs;

	public void setPushs(List<Push> pushs){
		this.pushs=pushs;
	}

	public List<Push>  getPushs(){
		return pushs;
	}


}
