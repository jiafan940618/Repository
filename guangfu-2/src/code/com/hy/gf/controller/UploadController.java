package com.hy.gf.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hy.gf.biz.OSSBiz;
import com.hy.gf.biz.UserBiz;
import com.hy.gf.model.User;
import com.hy.gf.util.Constant;
import com.hy.gf.vo.ResultData;


@Controller
@RequestMapping("/file")
public class UploadController {
	@Resource
	OSSBiz oosBiz;
	@Resource
	UserBiz userBiz;
	/**
	 * 批量上传文件上传图片
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upload")
	public ResultData<Object> upload(MultipartHttpServletRequest request) {
		ResultData<Object> data = new ResultData<>();
		String[] saveToOSSs = oosBiz.uploadFilesData(request);
		data.setData(saveToOSSs);
		return data;
	}
	
	class img{
		String url;
		String src;

		public String getSrc() {
			return src;
		}

		public void setSrc(String src) {
			this.src = src;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
		
	}
	/**
	 * 批量上传文件上传图片
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upload2")
	public ResultData<Object> upload2(MultipartHttpServletRequest request) {
		ResultData<Object> data = new ResultData<>();
		String[] saveToOSSs = oosBiz.uploadFilesData(request);
		img img = new img();
		img.setUrl(saveToOSSs[0]);
		img.setSrc(saveToOSSs[0]);
		data.setData(saveToOSSs[0]);
		return data;
	}
	
	/**
	 * web 用户修改头像
	 */
	@ResponseBody
	@RequestMapping(value = "/upload3")
	public Map<String,Object> upload3(MultipartHttpServletRequest request, HttpSession httpSession) throws IOException {
		Map<String,Object> rm = new HashMap<>();
		
		User sessionUser = (User)httpSession.getAttribute("user");
		if(sessionUser == null) {
			rm.put("success", false);
			rm.put("code", "444");
			rm.put("msg", "用户未登录");
			return rm;
		}
		String saveToOSSs = oosBiz.userUploadFilesData(request);
		if(saveToOSSs.equals("false")){
			rm.put("success", false);
			rm.put("msg", "上传失败");
			return rm;
		}
		User user = userBiz.select(sessionUser.getId());
		user.setImgUrl(saveToOSSs);
		userBiz.update(user);
		
		rm.put("imgUrl", saveToOSSs);
		rm.put("success", true);
		return rm;
	}
	
}
