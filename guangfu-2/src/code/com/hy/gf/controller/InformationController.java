package com.hy.gf.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hy.gf.biz.InformationBiz;
import com.hy.gf.model.Information;
import com.hy.gf.model.Page;
import com.hy.gf.util.ResponseUtil;
import com.hy.gf.vo.ResultData;

@Controller
@RequestMapping(value = "/information")
public class InformationController {
	@Resource
	InformationBiz informationBiz;

	 @ResponseBody
	 @RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		informationBiz.delete(id);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/insert")
	public ResultData insert(Information information, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		informationBiz.insert(information);
		resultData.setData(information);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Information information = informationBiz.select(id);
		resultData.setData(information);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/update")
	public ResultData update(Information information, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		informationBiz.update(information);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Information information, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Information selectByExample = informationBiz.selectOneByExample(information);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(Information information, Page<Information> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample() == null) {
			page.setExample(information);
		}
		Page<Information> selectByExample = informationBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		List<Information> list = JSON.parseArray(listJson.getData(), Information.class);
		informationBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		informationBiz.deleteBatch(ids);
		return resultData;
	}

	/**
	 * 移动端 根据id生成html
	 */
	@RequestMapping(value = "/infoHtml")
	public void JspToHtmlFile(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Information info = informationBiz.select(id);
		String title = info.getTitle();
		String time = sdf.format(info.getCreateDtm());
		String content = info.getContent();

		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<title>资讯详情</title>");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		sb.append("<meta name=\"viewport\" id=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		sb.append("<style type=\"text/css\">");
		sb.append("TABLE{border-collapse:collapse;border-left:solid 1 #000000; border-top:solid 1 #000000;padding:5px;}");
		sb.append("TH{border-right:solid 1 #000000;border-bottom:solid 1 #000000;}");
		sb.append("TD{font:normal;border-right:solid 1 #000000;border-bottom:solid 1 #000000;}");
		sb.append("</style></head>");
//		sb.append("<body bgcolor=\"#FFF8DC\">");
		sb.append("<body bgcolor=\"#FFF\">");
		sb.append("<div style='width:100%'>");
		sb.append(content);
		sb.append("</div></body></html>");
		ResponseUtil.write(response, sb);
	}

}