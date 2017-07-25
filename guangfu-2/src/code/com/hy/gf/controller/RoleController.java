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

import com.hy.gf.model.Role;
import com.hy.gf.vo.RoleVO;
import com.hy.gf.model.Page;
import com.hy.gf.vo.ResultData;
import com.hy.gf.biz.RoleBiz;


@Controller
@RequestMapping(value = "/role")
public class RoleController {
@Resource
RoleBiz roleBiz;

//@ResponseBody
//@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
	roleBiz.delete(id);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insert")
	public ResultData insert(Role role, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		roleBiz.insert(role);
		resultData.setData(role);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Role role = roleBiz.select(id);
		resultData.setData(role);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/update")
	public ResultData update(Role role, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		roleBiz.update(role);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Role role, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Role selectByExample =roleBiz.selectOneByExample(role);
		resultData.setData(selectByExample);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(Role role, Page<Role> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample()==null) {
				page.setExample(role);
			}
		Page<Role> selectByExample = roleBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
				ResultData resultData = new ResultData();
List<Role> list = JSON.parseArray(listJson.getData(), Role.class);
		roleBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		roleBiz.deleteBatch(ids);
		return resultData;
	}
}