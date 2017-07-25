package com.hy.gf.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.hy.gf.model.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hy.gf.model.Menu;
import com.hy.gf.vo.MenuVO;
import com.hy.gf.model.Page;
import com.hy.gf.model.Server;
import com.hy.gf.model.User;
import com.hy.gf.util.Constant;
import com.hy.gf.vo.ResultData;
import com.hy.gf.biz.MenuBiz;
import com.hy.gf.biz.UserBiz;


@Controller
@RequestMapping(value = "/menu")
public class MenuController {
@Resource
MenuBiz menuBiz;

//@ResponseBody
//@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
	menuBiz.delete(id);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insert")
	public ResultData insert(Menu menu, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		menuBiz.insert(menu);
		resultData.setData(menu);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Menu menu = menuBiz.select(id);
		resultData.setData(menu);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/update")
	public ResultData update(Menu menu, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		menuBiz.update(menu);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Menu menu, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Menu selectByExample =menuBiz.selectOneByExample(menu);
		resultData.setData(selectByExample);
		return resultData;
	}
	@ResponseBody
	@RequestMapping(value = "/selectByExample")
		public ResultData selectByExample(Menu menu, Page<Menu> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
			ResultData resultData = new ResultData();
			if (page.getExample()==null) {
					page.setExample(menu);
				}
			Page<Menu> selectByExample = menuBiz.selectByExample(page);
			resultData.setData(selectByExample);
			return resultData;
		}
//@ResponseBody
//@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
				ResultData resultData = new ResultData();
List<Menu> list = JSON.parseArray(listJson.getData(), Menu.class);
		menuBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		menuBiz.deleteBatch(ids);
		return resultData;
	}
	
	/*
	 * 后台，根据当前登录用户展示左边导航栏
	 * countType，1管理员，2服务商
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByExample2")
	public ResultData selectByExample2(Integer countType, MenuVO menuVO,Page<MenuVO> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Map<String, Object> map = new HashMap<>();
		
		if(countType == 1){
			User user = (User)httpSession.getAttribute("admin");
			if(user == null){
				return Constant.noLogin(resultData);
			}
			map.put("roleId", user.getRoleId());
		} else if(countType == 2){
			Server server = (Server)httpSession.getAttribute("server");
			if(server == null){
				return Constant.noLogin(resultData);
			}
			map.put("roleId", server.getRoleId());
		}
		
		map.put("sort", "rank");
		map.put("sortUp", "asc");
		map.put("start", page.getStart());
		map.put("limit", Integer.MAX_VALUE);
		List<MenuVO> selectByExample = menuBiz.selectByUserRole(map);
		
		resultData.setData(selectByExample);
		return resultData;
	}
	
}