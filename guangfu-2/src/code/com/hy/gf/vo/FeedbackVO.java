package com.hy.gf.vo;

import java.util.*;
import com.hy.gf.model.Feedback;


public class FeedbackVO extends Feedback {

	private List<Feedback> feedbacks;

	public void setFeedbacks(List<Feedback> feedbacks){
		this.feedbacks=feedbacks;
	}

	public List<Feedback>  getFeedbacks(){
		return feedbacks;
	}


}
