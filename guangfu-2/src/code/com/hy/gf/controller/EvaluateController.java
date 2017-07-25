package com.hy.gf.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hy.gf.biz.EvaluateBiz;
import com.hy.gf.biz.OrderBiz;
import com.hy.gf.biz.PushBiz;
import com.hy.gf.biz.ServerBiz;
import com.hy.gf.biz.StationBiz;
import com.hy.gf.model.Evaluate;
import com.hy.gf.model.Order;
import com.hy.gf.model.Page;
import com.hy.gf.model.Server;
import com.hy.gf.model.Station;
import com.hy.gf.model.User;
import com.hy.gf.vo.ResultData;


@Controller
@RequestMapping(value = "/evaluate")
public class EvaluateController {
@Resource
EvaluateBiz evaluateBiz;
@Resource
OrderBiz orderBiz;
@Resource
ServerBiz serverBiz;
@Resource
PushBiz pushBiz;
@Resource
StationBiz stationBiz;

//@ResponseBody
//@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
	evaluateBiz.delete(id);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insert")
	public ResultData insert(Evaluate evaluate, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		evaluateBiz.insert(evaluate);
		resultData.setData(evaluate);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Evaluate evaluate = evaluateBiz.select(id);
		resultData.setData(evaluate);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/update")
	public ResultData update(Evaluate evaluate, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		evaluateBiz.update(evaluate);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Evaluate evaluate, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Evaluate selectByExample =evaluateBiz.selectOneByExample(evaluate);
		resultData.setData(selectByExample);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(Evaluate evaluate, Page<Evaluate> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample()==null) {
				page.setExample(evaluate);
			}
		Page<Evaluate> selectByExample = evaluateBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
				ResultData resultData = new ResultData();
List<Evaluate> list = JSON.parseArray(listJson.getData(), Evaluate.class);
		evaluateBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		evaluateBiz.deleteBatch(ids);
		return resultData;
	}
	
	/**
	 * 评论订单
	 */
	@ResponseBody
	@RequestMapping(value = "/insertEvaluate")
	public ResultData insertEvaluate(Evaluate evaluate, HttpSession httpSession){
		ResultData resultData = new ResultData();
		
		Object attribute = httpSession.getAttribute("user");
		if(attribute != null) {
			User user = (User)attribute;
			evaluate.setUserId(user.getId());
		}
		
		Order order = orderBiz.select(Long.valueOf(evaluate.getOrderId()));
		Station station = new Station();
		station.setOrderId(order.getId());
		Station sobe = stationBiz.selectOneByExample(station);
		if (StringUtils.isEmpty(sobe.getAmmeterCode())) {
			resultData.setCode(777);
			resultData.setMsg("电站未绑定电表");
			resultData.setSuccess(false);
			return resultData;
		}
		
		Server server = serverBiz.select(order.getServerId());
		evaluate.setServer_id(server.getId());
		evaluateBiz.insert(evaluate);
		
		resultData.setData(evaluate);
		return resultData;
	}
}