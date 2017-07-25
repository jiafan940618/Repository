package com.hy.gf.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hy.gf.biz.MeterRecordBiz;
import com.hy.gf.biz.StationBiz;
import com.hy.gf.model.MeterRecord;
import com.hy.gf.model.Page;
import com.hy.gf.vo.ResultData;

@Controller
@RequestMapping(value = "/meterRecord")
public class MeterRecordController {
	@Resource
	MeterRecordBiz meterRecordBiz;
	@Resource
	StationBiz stationBiz;

	// @ResponseBody
	// @RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		meterRecordBiz.delete(id);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/insert")
	public ResultData insert(MeterRecord meterRecord, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		meterRecordBiz.insert(meterRecord);
		resultData.setData(meterRecord);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		MeterRecord meterRecord = meterRecordBiz.select(id);
		resultData.setData(meterRecord);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/update")
	public ResultData update(MeterRecord meterRecord, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		meterRecordBiz.update(meterRecord);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(MeterRecord meterRecord, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		MeterRecord selectByExample = meterRecordBiz.selectOneByExample(meterRecord);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(MeterRecord meterRecord, Page<MeterRecord> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample() == null) {
			page.setExample(meterRecord);
		}
		Page<MeterRecord> selectByExample = meterRecordBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		List<MeterRecord> list = JSON.parseArray(listJson.getData(), MeterRecord.class);
		meterRecordBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		meterRecordBiz.deleteBatch(ids);
		return resultData;
	}

}