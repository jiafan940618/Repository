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

import com.hy.gf.model.News;
import com.hy.gf.vo.NewsVO;
import com.hy.gf.model.Page;
import com.hy.gf.vo.ResultData;
import com.hy.gf.biz.NewsBiz;


@Controller
@RequestMapping(value = "/news")
public class NewsController {
@Resource
NewsBiz newsBiz;

@ResponseBody
@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
	newsBiz.delete(id);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/insert")
	public ResultData insert(News news, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		newsBiz.insert(news);
		resultData.setData(news);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		News news = newsBiz.select(id);
		resultData.setData(news);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/update")
	public ResultData update(News news, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		newsBiz.update(news);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(News news, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		News selectByExample =newsBiz.selectOneByExample(news);
		resultData.setData(selectByExample);
		return resultData;
	}
	@ResponseBody
	@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(News news, Page<News> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample()==null) {
				page.setExample(news);
			}
		Page<News> selectByExample = newsBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
				ResultData resultData = new ResultData();
List<News> list = JSON.parseArray(listJson.getData(), News.class);
		newsBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		newsBiz.deleteBatch(ids);
		return resultData;
	}
}