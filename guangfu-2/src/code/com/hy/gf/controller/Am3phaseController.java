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

import com.hy.gf.model.Am3phase;
import com.hy.gf.vo.Am3phaseVO;
import com.hy.gf.model.Page;
import com.hy.gf.vo.ResultData;
import com.hy.gf.biz.Am3phaseBiz;


@Controller
@RequestMapping(value = "/am3phase")
public class Am3phaseController {
@Resource
Am3phaseBiz am3phaseBiz;

//@ResponseBody
//@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
	am3phaseBiz.delete(id);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insert")
	public ResultData insert(Am3phase am3phase, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		am3phaseBiz.insert(am3phase);
		resultData.setData(am3phase);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Am3phase am3phase = am3phaseBiz.select(id);
		resultData.setData(am3phase);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/update")
	public ResultData update(Am3phase am3phase, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		am3phaseBiz.update(am3phase);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Am3phase am3phase, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Am3phase selectByExample =am3phaseBiz.selectOneByExample(am3phase);
		resultData.setData(selectByExample);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(Am3phase am3phase, Page<Am3phase> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample()==null) {
				page.setExample(am3phase);
			}
		Page<Am3phase> selectByExample = am3phaseBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
				ResultData resultData = new ResultData();
List<Am3phase> list = JSON.parseArray(listJson.getData(), Am3phase.class);
		am3phaseBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		am3phaseBiz.deleteBatch(ids);
		return resultData;
	}
}