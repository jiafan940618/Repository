package com.hy.gf.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hy.gf.biz.BankCardBiz;
import com.hy.gf.biz.WalletBiz;
import com.hy.gf.biz.WithdrawBiz;
import com.hy.gf.model.BankCard;
import com.hy.gf.model.Page;
import com.hy.gf.model.Server;
import com.hy.gf.model.User;
import com.hy.gf.model.Wallet;
import com.hy.gf.model.Withdraw;
import com.hy.gf.util.Constant;
import com.hy.gf.util.RongLianSMS;
import com.hy.gf.vo.ResultData;
import com.hy.gf.vo.WithdrawVO;


@Controller
@RequestMapping(value = "/withdraw")
public class WithdrawController {
@Resource
WithdrawBiz withdrawBiz;
@Resource
WalletBiz walletBiz;
@Resource
BankCardBiz bankCardBiz;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}


//@ResponseBody
//@RequestMapping(value = "/delete")
	public ResultData delete(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
	withdrawBiz.delete(id);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insert")
	public ResultData insert(Withdraw withdraw, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		withdrawBiz.insert(withdraw);
		resultData.setData(withdraw);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/select")
	public ResultData select(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Withdraw withdraw = withdrawBiz.select(id);
		resultData.setData(withdraw);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/update")
	public ResultData update(Withdraw withdraw, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		withdrawBiz.update(withdraw);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/selectOneByExample")
	public ResultData selectOneByExample(Withdraw withdraw, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		Withdraw selectByExample =withdrawBiz.selectOneByExample(withdraw);
		resultData.setData(selectByExample);
		return resultData;
	}
@ResponseBody
@RequestMapping(value = "/selectByExample")
	public ResultData selectByExample(WithdrawVO withdrawVO, Page<Withdraw> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		if (page.getExample()==null) {
				page.setExample(withdrawVO);
			}
		Page<Withdraw> selectByExample = withdrawBiz.selectByExample(page);
		resultData.setData(selectByExample);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/insertBatch")
	public ResultData insertBatch(ResultData<String> listJson, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
				ResultData resultData = new ResultData();
List<Withdraw> list = JSON.parseArray(listJson.getData(), Withdraw.class);
		withdrawBiz.insertBatch(list);
		resultData.setData(list);
		return resultData;
	}
//@ResponseBody
//@RequestMapping(value = "/deleteBatch")
	public ResultData deleteBatch(Page<Long> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
List<Long> ids = page.getList();
		ResultData resultData = new ResultData();
		withdrawBiz.deleteBatch(ids);
		return resultData;
	}
	
	/**
	 * 服务商提现时-发送验证码和告知
	 */
	@ResponseBody
	@RequestMapping(value = "/code4serverTixian")
	public ResultData<Object> code4serverTixian(Double money, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData<Object>();
		
		Server server = (Server)httpSession.getAttribute("server");
		if(server == null){
			return Constant.noLogin(resultData);
		}
		if(money != null && money >0 ){
//			String code = "123456";
			String code = RongLianSMS.getCode();
			String msg1 = code+"(验证码)";
			String msg2 = money + "元";
			
			RongLianSMS.sendMsg4ServerTiXian(server.getPhone(), msg1, msg2);
			httpSession.setAttribute("code4serverTixian", code);
			httpSession.setAttribute("code4serverTixianTime",System.currentTimeMillis());
			return resultData;	
		}else{
			resultData.setMsg("请输入提现金额");
			resultData.setCode(600);
			resultData.setSuccess(false);
			return resultData;
		}
	}
	
	/**
	 * 服务商申请提现
	 */
	@ResponseBody
	@RequestMapping(value = "/serverDixian")
	public ResultData serverDixian(String code, Withdraw withdraw, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		 
		Object attribute = httpSession.getAttribute("server");
		if(attribute == null){
			return Constant.noLogin(resultData);
		}
		Server server = (Server)attribute;
		
		String code4serverTixian = (String)httpSession.getAttribute("code4serverTixian");
		// 检测短信验证码是否过期
		ResultData<Object> resultData2 = RongLianSMS.checkMsgTimeOut("code4serverTixianTime", httpSession);
		if(!resultData2.getSuccess()){
			return resultData2;
		}
		if(StringUtils.isEmpty(code4serverTixian)){
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("请先发送验证码");
			return resultData;
		}
		if(!code4serverTixian.equals(code)){
			resultData.setCode(403);
			resultData.setSuccess(false);
			resultData.setMsg("验证码错误");
			return resultData;
		}
		
		Wallet wallet = new Wallet();
		wallet.setServerId(server.getId());
		Wallet sobe = walletBiz.selectOneByExample(wallet);
		
		BankCard card = new BankCard();
		card.setServerId(server.getId().intValue());
		card = bankCardBiz.selectOneByExample(card);
		if(card == null){
			resultData.setCode(600);
			resultData.setMsg("服务商还未填写银行卡");
			resultData.setSuccess(false);
			return resultData;
		}
		
		if(sobe.getStatus() == 1){
			resultData.setCode(606);
			resultData.setMsg("钱包已经被冻结，无法申请提现");
			resultData.setSuccess(false);
			return resultData;
		}
		
		if(withdraw.getMoney() > sobe.getMoney()){
			resultData.setCode(606);
			resultData.setMsg("提现金额大于钱包余额");
			resultData.setSuccess(false);
			return resultData;
		}
		// 插入提现记录
		withdraw.setServerId(server.getId().intValue());
		withdraw.setType(1);
		withdraw.setCardId(card.getId().intValue());
		withdraw.setCardNum(card.getBankNum());
		withdraw.setCardUserName(card.getUsername());
		
		withdrawBiz.insert(withdraw);
		// 更新钱余额
		sobe.setMoney(sobe.getMoney() - withdraw.getMoney());
		walletBiz.update(sobe);
		
		return resultData;
	}
	
	/**
	 * 管理员确认提现
	 */
	@ResponseBody
	@RequestMapping(value = "/confirmDixian")
	public ResultData confirmDixian(Withdraw withdraw, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Object attribute = httpSession.getAttribute("admin");
		if(attribute == null){
			return Constant.noLogin(resultData);
		}
		
		if(((User)attribute).getRoleId() != 1){
			resultData.setCode(606);
			resultData.setMsg("您不是管理员，无法操作");
			resultData.setSuccess(false);
			return resultData;
		}
		
		Withdraw selectById = withdrawBiz.select(withdraw.getId());
		selectById.setStatus(1);
		withdrawBiz.update(selectById);
		
		return resultData;
	}
	
	/**
	 * 管理员和服务商--条件筛选查看提现记录
	 * countType，1管理员，2服务商
	 */
	@ResponseBody
	@RequestMapping(value = "/dixianFilter")
	public ResultData dixianFilter(Integer countType, Withdraw withdraw, String query, Date timeFrom, Date timeTo, Page<Withdraw> page, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		if(countType == 1){
			Map<String, Object> map = new HashMap<>();
			map.put("query", query);
			map.put("status", withdraw.getStatus());
			map.put("timeFrom", timeFrom);
			map.put("timeTo", timeTo);
			map.put("start", page.getStart());
			map.put("limit", page.getLimit());
			List<Withdraw> list = withdrawBiz.dixianFilter(map);
			int num = withdrawBiz.dixianFilterCount(map);
			
			page.setTotal(num);
			page.setList(list);
			
			resultData.setData(page);
			return resultData;
		} else if(countType == 2){
			Server server = (Server)httpSession.getAttribute("server");
			
			Map<String, Object> map = new HashMap<>();
			map.put("query", query);
			map.put("serverId", server.getId());
			map.put("status", withdraw.getStatus());
			map.put("timeFrom", timeFrom);
			map.put("timeTo", timeTo);
			map.put("start", page.getStart());
			map.put("limit", page.getLimit());
			List<Withdraw> list = withdrawBiz.dixianFilter(map);
			int num = withdrawBiz.dixianFilterCount(map);
			
			page.setTotal(num);
			page.setList(list);
			
			resultData.setData(page);
			return resultData;
		}
		
		return resultData;
	}
	
	/**
	 * 用户 申请退款（未完成）
	 */
	@ResponseBody
	@RequestMapping(value = "/applyRefund")
	public ResultData applyRefund(Withdraw withdraw, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		ResultData resultData = new ResultData();
		
		Object attribute = httpSession.getAttribute("user");
		if(attribute == null){
			return Constant.noLogin(resultData);
		}
		User user = (User)attribute;
		
		
		
		return resultData;
	}
	
}