package com.hy.gf.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import com.alibaba.fastjson.JSON;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hy.gf.model.Feedback;
import com.hy.gf.vo.FeedbackVO;
import com.hy.gf.model.Page;
import com.hy.gf.vo.ResultData;
import com.hy.gf.biz.FeedbackBiz;

@Controller
@RequestMapping(value = "/feedback")
public class FeedbackController {
	@Resource
	FeedbackBiz feedbackBiz;

	// @ResponseBody
	// @RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		feedbackBiz.delete(id);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/insert")
	public ResultData insert(Feedback feedback, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		feedbackBiz.insert(feedback);
		resultData.setData(feedback);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Feedback feedback = feedbackBiz.select(id);
		resultData.setData(feedback);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/update")
	public ResultData update(Feedback feedback, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		feedbackBiz.update(feedback);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Feedback feedback, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Feedback selectByExample = feedbackBiz.selectOneByExample(feedback);
		resultData.setData(selectByExample);
		return resultData;
	}

	 @ResponseBody
	 @RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(Feedback feedback, Page<Feedback> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample() == null) {
			page.setExample(feedback);
		}
		Page<Feedback> selectByExample = feedbackBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		List<Feedback> list = JSON.parseArray(listJson.getData(), Feedback.class);
		feedbackBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		feedbackBiz.deleteBatch(ids);
		return resultData;
	}
}