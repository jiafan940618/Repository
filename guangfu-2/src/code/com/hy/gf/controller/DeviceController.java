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

import com.hy.gf.model.Device;
import com.hy.gf.vo.DeviceVO;
import com.hy.gf.model.Page;
import com.hy.gf.vo.ResultData;
import com.hy.gf.biz.DeviceBiz;

@Controller
@RequestMapping(value = "/device")
public class DeviceController {
	@Resource
	DeviceBiz deviceBiz;

	// @ResponseBody
	// @RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		deviceBiz.delete(id);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/insert")
	public ResultData insert(Device device, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		deviceBiz.insert(device);
		resultData.setData(device);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Device device = deviceBiz.select(id);
		resultData.setData(device);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/update")
	public ResultData update(Device device, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		deviceBiz.update(device);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Device device, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Device selectByExample = deviceBiz.selectOneByExample(device);
		resultData.setData(selectByExample);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(Device device, Page<Device> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample() == null) {
			page.setExample(device);
		}
		Page<Device> selectByExample = deviceBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		List<Device> list = JSON.parseArray(listJson.getData(), Device.class);
		deviceBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		deviceBiz.deleteBatch(ids);
		return resultData;
	}

	/**
	 * 根据服务商id查找设备
	 * 
	 * @param serverId
	 */
	@ResponseBody
	@RequestMapping(value = "/findDeviceByServerId")
	public List<Device> findDeviceByServerId(Device device, Page<Device> page) {

		page.setExample(device);
		page.setStart(0);
		page.setLimit(Integer.MAX_VALUE);
		Page<Device> sbe = deviceBiz.selectByExample(page);

		return sbe.getList();
	}
}