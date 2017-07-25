package com.hy.gf.util;

import com.hy.gf.vo.ResultData;

public class Constant {
	
	public final static String NO_LOGIN = "未登录";
	public final static String EXIST_THIS_USER = "用户已存在";
	public final static String NO_THIS_USER = "用户不存在";
	public final static String PASSWORD_ERROR = "密码不正确";
	public final static String CODE_ERROR = "验证码不正确";
	public final static String LONG_TIME_OUT = "登录已过期";
	
	public final static String NO_THIS_ADDRESS = "地址不存在";
	public final static String PARAMS_ERROR = "参数错误";
	public final static String NO_PERMISSION = "权限不足";
	
	public static <T> ResultData<T> noLogin(ResultData<T> resultData) {
		resultData.setCode(444);
		resultData.setSuccess(false);
		resultData.setMsg(Constant.NO_LOGIN);
		return resultData;
	}
	
	public static <T> ResultData<T> error(ResultData<T> resultData, int code, String Msg) {
        resultData.setMsg(Msg);
        resultData.setCode(code);
        return resultData;
    }
	
}
