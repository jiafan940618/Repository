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

import com.hy.gf.model.UserServer;
import com.hy.gf.vo.UserServerVO;
import com.hy.gf.model.Page;
import com.hy.gf.vo.ResultData;
import com.hy.gf.biz.UserServerBiz;


@Controller
@RequestMapping(value = "/userServer")
public class UserServerController {
@Resource
UserServerBiz userServerBiz;

//@ResponseBody
//@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
	userServerBiz.delete(id);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insert")
	public ResultData insert(UserServer userServer, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		userServerBiz.insert(userServer);
		resultData.setData(userServer);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		UserServer userServer = userServerBiz.select(id);
		resultData.setData(userServer);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/update")
	public ResultData update(UserServer userServer, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		userServerBiz.update(userServer);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(UserServer userServer, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		UserServer selectByExample =userServerBiz.selectOneByExample(userServer);
		resultData.setData(selectByExample);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(UserServer userServer, Page<UserServer> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample()==null) {
				page.setExample(userServer);
			}
		Page<UserServer> selectByExample = userServerBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
				ResultData resultData = new ResultData();
List<UserServer> list = JSON.parseArray(listJson.getData(), UserServer.class);
		userServerBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		userServerBiz.deleteBatch(ids);
		return resultData;
	}
}