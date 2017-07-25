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

import com.hy.gf.model.Vas;
import com.hy.gf.util.Constant;
import com.hy.gf.model.Page;
import com.hy.gf.vo.ResultData;
import com.hy.gf.biz.VasBiz;

@Controller
@RequestMapping(value = "/vas")
public class VasController {
	@Resource
	VasBiz vasBiz;

	@ResponseBody
	@RequestMapping(value = "/delete")
	public Object delete(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		Object attribute = httpSession.getAttribute("admin");
		if(attribute == null){
			return Constant.noLogin(resultData);
		}
		vasBiz.delete(id);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/insert")
	public Object insert(Vas vas, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		Object attribute = httpSession.getAttribute("admin");
		if(attribute == null){
			return Constant.noLogin(resultData);
		}
		vasBiz.insert(vas);
		resultData.setData(vas);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/select")
	public Object select(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		Vas vas = vasBiz.select(id);
		resultData.setData(vas);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/update")
	public Object update(Vas vas, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData<Object> resultData = new ResultData<Object>();
		Object attribute = httpSession.getAttribute("admin");
		if(attribute == null){
			return Constant.noLogin(resultData);
		}
		vasBiz.update(vas);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Vas vas, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Vas selectByExample = vasBiz.selectOneByExample(vas);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(Vas vas, Page<Vas> page, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample() == null) {
			page.setExample(vas);
		}
		Page<Vas> selectByExample = vasBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
//	@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		List<Vas> list = JSON.parseArray(listJson.getData(), Vas.class);
		vasBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}

	@ResponseBody
//	@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		vasBiz.deleteBatch(ids);
		return resultData;
	}
}