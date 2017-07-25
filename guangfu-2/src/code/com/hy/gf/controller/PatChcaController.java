package com.hy.gf.controller;

import java.awt.Color;
import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hy.gf.util.ValidateCodeUtil.ValidateCode;
import com.hy.gf.util.patchca.color.ColorFactory;
import com.hy.gf.util.patchca.filter.predefined.CurvesRippleFilterFactory;
import com.hy.gf.util.patchca.service.ConfigurableCaptchaService;
import com.hy.gf.util.patchca.utils.encoder.EncoderHelper;
import com.hy.gf.util.patchca.word.RandomWordFactory;
import com.hy.gf.vo.ResultData;
/**
 * patchca生成多彩验证码
 *
 */
@Controller
@RequestMapping("/patChca2")
public class PatChcaController {
	
    private static ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
    private static Random random = new Random();
    
    static {
//        cs.setColorFactory(new SingleColorFactory(new Color(25, 60, 170)));
        cs.setColorFactory(new ColorFactory() {
            @Override
            public Color getColor(int x) {
                int[] c = new int[3];
                int i = random.nextInt(c.length);
                for (int fi = 0; fi < c.length; fi++) {
                    if (fi == i) {
                        c[fi] = random.nextInt(71);
                    } else {
                        c[fi] = random.nextInt(256);
                    }
                }
                return new Color(c[0], c[1], c[2]);
            }
        });
        
        
    	CurvesRippleFilterFactory crff = new CurvesRippleFilterFactory(cs.getColorFactory()); // 干扰线
        cs.setFilterFactory(crff);
        
        RandomWordFactory wf = new RandomWordFactory();
        wf.setCharacters("23456789abcdefghigkmnpqrstuvwxyzABCDEFGHIGKLMNPQRSTUVWXYZ");
        wf.setMaxLength(5);
        wf.setMinLength(5);
        cs.setWordFactory(wf);
    }
    
    
    @RequestMapping("/pcrimg2")
    public void pcrimg2(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) throws IOException {
        setResponseHeaders(response);
        String token = EncoderHelper.getChallangeAndWriteImage(cs, "png", response.getOutputStream());
        httpSession.setAttribute("captchaToken", token);
        System.out.println("当前的SessionID=" + httpSession.getId() + "，验证码=" + token);
    }
    
    
    @RequestMapping("/pcrimg")
    public void crimg(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) throws IOException {
    	// 设置响应的类型格式为图片格式  
        response.setContentType("image/jpeg");  
        //禁止图像缓存。  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
          
        ValidateCode vCode = new ValidateCode(120, 40, 4, 100);
        String code = vCode.getCode();
        httpSession.setAttribute("captchaToken", code);  
        System.out.println("当前的SessionID=" + httpSession.getId() + "，验证码=" + code);
        vCode.write(response.getOutputStream());  
    }
    
    
    protected void setResponseHeaders(HttpServletResponse response) {
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        long time = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", time);
        response.setDateHeader("Date", time);
        response.setDateHeader("Expires", time);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    @ResponseBody
    @RequestMapping("/validateCode")
    public ResultData validateCode(String code, String phoneCode, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession){
    	ResultData resultData = new ResultData();
    	
    	if(StringUtils.isBlank(code)){
    		resultData.setMsg("请输入图形验证码");
    		resultData.setSuccess(false);
    		return resultData;
    	}
    	if(StringUtils.isBlank(phoneCode)){
    		resultData.setMsg("请输入您获取到的手机验证码");
    		resultData.setSuccess(false);
    		return resultData;
    	}
    	String code4test = (String)httpSession.getAttribute("code4test");
    	if(StringUtils.isBlank(code4test)){
    		resultData.setMsg("请发送短信验证码");
    		resultData.setSuccess(false);
    		return resultData;
    	}
    	String sessionCode = (String)httpSession.getAttribute("captchaToken");
    	if(!sessionCode.equalsIgnoreCase(code)){
    		resultData.setMsg("输入的图形验证码错误");
    		resultData.setSuccess(false);
    		return resultData;
    	}
    	if(!code4test.equals(phoneCode)){
    		resultData.setMsg("输入的手机验证码错误");
    		resultData.setSuccess(false);
    		return resultData;
    	}
    	
    	resultData.setMsg("核对通过");
    	return resultData;
    }
    
    @ResponseBody
    @RequestMapping("/code4test")
    public ResultData validateCode2(String graphicCode, String phone, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession){
    	ResultData resultData = new ResultData();
    	
    	if(StringUtils.isBlank(phone)){
    		resultData.setMsg("请输入手机号");
    		resultData.setSuccess(false);
    		return resultData;
    	}
    	if(StringUtils.isBlank(graphicCode)){
    		resultData.setMsg("请输入图形验证码");
    		resultData.setSuccess(false);
    		return resultData;
    	}
    	String sessionCode = (String)httpSession.getAttribute("captchaToken");
    	if(!sessionCode.equalsIgnoreCase(graphicCode)){
    		resultData.setMsg("输入的图形验证码错误");
    		resultData.setSuccess(false);
    		return resultData;
    	}
    	
    	String code = "123456";
//    	String code = RongLianSMS.sendCode(phone);
    	if(code == null){
    		resultData.setMsg("短信验证码获取失败");
    		resultData.setSuccess(false);
    		return resultData;
    	}
    	
		httpSession.setAttribute("code4test", code);
		resultData.setMsg("短信验证码已发送成功");
		return resultData;
    }
}