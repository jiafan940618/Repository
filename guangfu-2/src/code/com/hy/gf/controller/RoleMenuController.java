package com.hy.gf.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hy.gf.biz.MenuBiz;
import com.hy.gf.biz.RoleMenuBiz;
import com.hy.gf.model.Menu;
import com.hy.gf.model.Page;
import com.hy.gf.model.RoleMenu;
import com.hy.gf.vo.MenuVO;
import com.hy.gf.vo.ResultData;


@Controller
@RequestMapping(value = "/roleMenu")
public class RoleMenuController {
@Resource
RoleMenuBiz roleMenuBiz;
@Resource
MenuBiz menuBiz;

@ResponseBody
@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
	roleMenuBiz.delete(id);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/insert")
	public ResultData insert(RoleMenu roleMenu, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		roleMenuBiz.insert(roleMenu);
		resultData.setData(roleMenu);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		RoleMenu roleMenu = roleMenuBiz.select(id);
		resultData.setData(roleMenu);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/update")
	public ResultData update(RoleMenu roleMenu, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		roleMenuBiz.update(roleMenu);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(RoleMenu roleMenu, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		RoleMenu selectByExample =roleMenuBiz.selectOneByExample(roleMenu);
		resultData.setData(selectByExample);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(RoleMenu roleMenu, Page<RoleMenu> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample()==null) {
				page.setExample(roleMenu);
			}
		Page<RoleMenu> selectByExample = roleMenuBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
				ResultData resultData = new ResultData();
List<RoleMenu> list = JSON.parseArray(listJson.getData(), RoleMenu.class);
		roleMenuBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		roleMenuBiz.deleteBatch(ids);
		return resultData;
	}
	
}