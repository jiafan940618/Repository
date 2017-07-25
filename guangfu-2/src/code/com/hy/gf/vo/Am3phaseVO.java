package com.hy.gf.vo;

import java.util.*;
import java.math.*;
import com.hy.gf.model.Am3phase;


public class Am3phaseVO extends Am3phase {

	private List<Am3phase> am3phases;

	public void setAm3phases(List<Am3phase> am3phases){
		this.am3phases=am3phases;
	}

	public List<Am3phase>  getAm3phases(){
		return am3phases;
	}


}
