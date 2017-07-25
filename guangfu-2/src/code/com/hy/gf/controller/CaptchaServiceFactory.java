package com.hy.gf.controller;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hy.gf.util.patchca.color.ColorFactory;
import com.hy.gf.util.patchca.color.SingleColorFactory;
import com.hy.gf.util.patchca.filter.predefined.CurvesRippleFilterFactory;
import com.hy.gf.util.patchca.font.RandomFontFactory;
import com.hy.gf.util.patchca.service.Captcha;
import com.hy.gf.util.patchca.service.CaptchaService;
import com.hy.gf.util.patchca.service.ConfigurableCaptchaService;
import com.hy.gf.util.patchca.utils.encoder.EncoderHelper;
import com.hy.gf.util.patchca.word.RandomWordFactory;

/**
 * patchca生成多彩验证码
 *
 */
@Controller
@RequestMapping("/patChca")
public class CaptchaServiceFactory {
	private static final String DEFAULT_CHARACTERS = "23456789abcdefghigkmnpqrstuvwxyzABCDEFGHIGKLMNPQRSTUVWXYZ"; // 自己设置！
	private static int DEFAULT_FONT_SIZE = 25; // 验证码字体大小
	private static int DEFAULT_WORD_LENGTH = 4; // 验证码字体个数
	private static int DEFAULT_WIDTH = 120; // 验证码图片宽度
	private static int DEFAULT_HEIGHT = 40; // 验证码图片高度

	private CaptchaServiceFactory() {
		
	}

	public static CaptchaService create(int fontSize, int wordLength, String characters, int width, int height) {
		ConfigurableCaptchaService service = null;
		// 字体大小设置
		RandomFontFactory ff = new RandomFontFactory();
		ff.setMinSize(fontSize);
		ff.setMaxSize(fontSize);
		// 生成的单词设置
		RandomWordFactory rwf = new RandomWordFactory();
		rwf.setCharacters(characters);
		rwf.setMinLength(wordLength);
		rwf.setMaxLength(wordLength);
		// 干扰线波纹
		CurvesRippleFilterFactory crff = new CurvesRippleFilterFactory();
		// 处理器
		service = new ExConfigurableCaptchaService();
		service.setFontFactory(ff);
		service.setWordFactory(rwf);
		service.setFilterFactory(crff);
		// 生成图片大小（像素）
		service.setWidth(width);
		service.setHeight(height);
		return service;
	}

	public static CaptchaService create() {
		return create(DEFAULT_FONT_SIZE, DEFAULT_WORD_LENGTH, DEFAULT_CHARACTERS, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	// 随机变色处理
	static class ExConfigurableCaptchaService extends ConfigurableCaptchaService {
		private static final Random RANDOM = new Random();
		private List<ColorFactory> colorList = new ArrayList<ColorFactory>(); // 为了性能

		public ExConfigurableCaptchaService() {
			colorList.add(new SingleColorFactory(Color.blue));
			colorList.add(new SingleColorFactory(Color.black));
			colorList.add(new SingleColorFactory(Color.red));
			colorList.add(new SingleColorFactory(Color.pink));
			colorList.add(new SingleColorFactory(Color.orange));
			colorList.add(new SingleColorFactory(Color.green));
			colorList.add(new SingleColorFactory(Color.magenta));
			colorList.add(new SingleColorFactory(Color.cyan));
		}

		public ColorFactory nextColorFactory() {
			int index = RANDOM.nextInt(colorList.size());
			return colorList.get(index);
		}

		@Override
		public Captcha getCaptcha() {
			ColorFactory cf = nextColorFactory();
			CurvesRippleFilterFactory crff = (CurvesRippleFilterFactory) this.getFilterFactory();
			crff.setColorFactory(cf);
			this.setColorFactory(cf);
			return super.getCaptcha();
		}
	}

	
	@RequestMapping("/pcrimg")
    public void pcrimg2(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) throws IOException {
        setResponseHeaders(response);
        CaptchaService service = CaptchaServiceFactory.create();
        String token = EncoderHelper.getChallangeAndWriteImage(service, "png", response.getOutputStream());
        httpSession.setAttribute("captchaToken", token);
        System.out.println("当前的SessionID=" + httpSession.getId() + "，验证码=" + token);
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
}