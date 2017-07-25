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
import com.hy.gf.biz.BankCardBiz;
import com.hy.gf.model.BankCard;
import com.hy.gf.model.Page;
import com.hy.gf.model.Server;
import com.hy.gf.model.User;
import com.hy.gf.util.Constant;
import com.hy.gf.vo.ResultData;

@Controller
@RequestMapping(value = "/bankCard")
public class BankCardController {
	@Resource
	BankCardBiz bankCardBiz;

	@ResponseBody
	@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		bankCardBiz.delete(id);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/insert")
	public ResultData insert(BankCard bankCard, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		bankCardBiz.insert(bankCard);
		resultData.setData(bankCard);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		BankCard bankCard = bankCardBiz.select(id);
		resultData.setData(bankCard);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/update")
	public ResultData update(BankCard bankCard, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		bankCardBiz.update(bankCard);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(BankCard bankCard, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		BankCard selectByExample = bankCardBiz.selectOneByExample(bankCard);
		resultData.setData(selectByExample);
		return resultData;
	}

	@ResponseBody
	@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(BankCard bankCard, Page<BankCard> page, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample() == null) {
			page.setExample(bankCard);
		}
		Page<BankCard> selectByExample = bankCardBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();
		List<BankCard> list = JSON.parseArray(listJson.getData(), BankCard.class);
		bankCardBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}

	// @ResponseBody
	// @RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		bankCardBiz.deleteBatch(ids);
		return resultData;
	}

	/**
	 * 根据用户id查找用户银行卡
	 */
	@ResponseBody
	@RequestMapping(value = "/find")
	public ResultData find(BankCard bankCard, Page<BankCard> page, HttpSession httpSession) {
		ResultData resultData = new ResultData();

		User sessionUser = (User) httpSession.getAttribute("user");
		if (sessionUser == null) {
			return Constant.noLogin(resultData);
		}

		bankCard.setUserId(sessionUser.getId().intValue());
		if (page.getExample() == null) {
			page.setExample(bankCard);
		}
		Page<BankCard> selectByExample = bankCardBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}

	/**
	 * 用户添加银行卡
	 */
	@ResponseBody
	@RequestMapping(value = "/add")
	public ResultData add(BankCard bankCard, HttpServletRequest request, HttpServletResponse response,
			HttpSession httpSession) {
		ResultData resultData = new ResultData();

		User user = (User) httpSession.getAttribute("user");
		if (user == null) {
			return Constant.noLogin(resultData);
		}

		BankCard nb = bankCardBiz.selectOneByExample(bankCard);
		if (nb != null) {
			resultData.setCode(505);
			resultData.setMsg("该银行卡已添加，无法再次添加");
			return resultData;
		}

		bankCard.setUserId(user.getId().intValue());
		bankCardBiz.insert(bankCard);
		resultData.setData(bankCard);
		return resultData;
	}

	/**
	 * ***************************************************************
	 * 针对后台，服务商添加或修改银行卡 type=1绑定银行卡，type=2修改已经绑定的银行卡，type=3获取session服务商的银行卡信息
	 */
	@ResponseBody
	@RequestMapping(value = "/serverBC")
	public ResultData serverBC(Integer type, BankCard bankCard, HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();

		Object attribute = httpSession.getAttribute("server");
		if (attribute == null) {
			return Constant.noLogin(resultData);
		}

		Server server = (Server) attribute;

		// 绑定银行卡
		if (type == 1) {
			BankCard bc = new BankCard();
			bc.setServerId(server.getId().intValue());
			BankCard sobe = bankCardBiz.selectOneByExample(bc);
			if (sobe != null) {
				resultData.setCode(600);
				resultData.setMsg("已绑定银行卡，无法继续绑定");
				resultData.setSuccess(false);
				return resultData;
			}
			bankCard.setServerId(server.getId().intValue());
			bankCardBiz.insert(bankCard);
			resultData.setMsg("绑定银行卡成功");
			return resultData;
		}

		// 修改已绑定的银行卡
		if (type == 2) {
			int total = bankCardBiz.update(bankCard);
			if (total == 1) {
				resultData.setMsg("修改银行卡成功");
			}
		}

		// 根据session获取服务商的银行卡
		if (type == 3) {
			BankCard bc = new BankCard();
			bc.setServerId(server.getId().intValue());
			BankCard sobe = bankCardBiz.selectOneByExample(bc);
			resultData.setData(sobe);
			return resultData;
		}

		return resultData;
	}

}