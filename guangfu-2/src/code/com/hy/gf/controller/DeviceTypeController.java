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

import com.hy.gf.model.DeviceType;
import com.hy.gf.vo.DeviceTypeVO;
import com.hy.gf.model.Page;
import com.hy.gf.util.Constant;
import com.hy.gf.vo.ResultData;
import com.hy.gf.biz.DeviceTypeBiz;

@Controller
@RequestMapping(value = "/deviceType")
public class DeviceTypeController {
	@Resource
	DeviceTypeBiz deviceTypeBiz;

	@ResponseBody
	@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Object attribute = httpSession.getAttribute("admin");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		}
		deviceTypeBiz.delete(id);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/insert")
	public ResultData insert(DeviceType deviceType, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Object attribute = httpSession.getAttribute("admin");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		}
		deviceTypeBiz.insert(deviceType);
		resultData.setData(deviceType);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		DeviceType deviceType = deviceTypeBiz.select(id);
		resultData.setData(deviceType);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/update")
	public ResultData update(DeviceType deviceType, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Object attribute = httpSession.getAttribute("admin");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		}
		deviceTypeBiz.update(deviceType);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(DeviceType deviceType, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		DeviceType selectByExample = deviceTypeBiz.selectOneByExample(deviceType);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(DeviceType deviceType, Page<DeviceType> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample() == null) {
			page.setExample(deviceType);
		}
		Page<DeviceType> selectByExample = deviceTypeBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Object attribute = httpSession.getAttribute("admin");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		}
		List<DeviceType> list = JSON.parseArray(listJson.getData(), DeviceType.class);
		deviceTypeBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		
		Object attribute = httpSession.getAttribute("admin");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		}
		deviceTypeBiz.deleteBatch(ids);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectByExample2")
	public ResultData selectByExample2(DeviceType deviceType, Page<DeviceType> page, Integer type,
			HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample() == null) {
			page.setExample(deviceType);
		}
		List<DeviceType> selectByExample = deviceTypeBiz.selectByExample2(page, type);
		resultData.setData(selectByExample);
		return resultData;
	}
}