package com.hy.gf.util;

import com.tencent.xinge.Message;
import com.tencent.xinge.Style;
import com.tencent.xinge.XingeApp;

public class XGPush {
	
	private static final Long ACCESS_ID = 2100204719L;
	private static final String SECRET_KEY = "8c4c70915f14b9cca5c8c162f034a356";
	private static XingeApp push;
	
	static {
		push = new XingeApp(ACCESS_ID, SECRET_KEY);
	}
	
	private static Message newMessage(String title, String content) {
		Message msg;
		msg = new Message();
		msg.setStyle(new Style(0, 1, 1, 1, 0, 1, 0, 1));
		msg.setExpireTime(2 * 60 * 60);
		msg.setType(Message.TYPE_NOTIFICATION);
		msg.setTitle(title);
		msg.setContent(content);
		return msg;
	}
	
	/**
	 * 给单个用户推送
	 * @param accout
	 * @param title
	 * @param content
	 */
	public static void pushOne(String accout, String title, String content) {
		System.out.println(push.pushSingleAccount(0, accout, newMessage(title, content)));
	}
	
}
