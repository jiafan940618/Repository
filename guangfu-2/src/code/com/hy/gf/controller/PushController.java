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

import com.hy.gf.model.Push;
import com.hy.gf.vo.PushVO;
import com.hy.gf.model.Page;
import com.hy.gf.vo.ResultData;
import com.hy.gf.biz.PushBiz;


@Controller
@RequestMapping(value = "/push")
public class PushController {
@Resource
PushBiz pushBiz;

//@ResponseBody
//@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
	pushBiz.delete(id);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/insert")
	public ResultData insert(Push push, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		pushBiz.insert(push);
		resultData.setData(push);
		return resultData;
	}
	@ResponseBody
	@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Push push = pushBiz.select(id);
		resultData.setData(push);
		return resultData;
	}
	@ResponseBody
	@RequestMapping(value = "/update")
	public ResultData update(Push push, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		pushBiz.update(push);
		return resultData;
	}
	@ResponseBody
	@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Push push, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Push selectByExample =pushBiz.selectOneByExample(push);
		resultData.setData(selectByExample);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(Push push, Page<Push> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample()==null) {
				page.setExample(push);
			}
		Page<Push> selectByExample = pushBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
				ResultData resultData = new ResultData();
List<Push> list = JSON.parseArray(listJson.getData(), Push.class);
		pushBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		pushBiz.deleteBatch(ids);
		return resultData;
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectAndUpdate")
	public ResultData selectAndUpdate(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Push push = pushBiz.select(id);
		
		if(0 == push.getIsRead()){
			push.setIsRead(1);
			pushBiz.update(push);
		}
		
		resultData.setData(push);
		return resultData;
	}
	
}