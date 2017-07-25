package com.hy.gf.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class ShareCodeUtil {
	/** 自定义进制(0,1没有加入,容易与o,l混淆) */
    private static final char[] r=new char[]{'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z', 'x', '9', 'c', '7', 'p', '5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g', 'h'};
 
    /** (不能与自定义进制有重复) */
    private static final char b='o';
 
    /** 进制长度 */
    private static final int binLen=r.length;
 
    
    /**
     * 根据ID生成六位随机码
     * @param id ID
     * @return 随机码
     */
    public static String toSerialCode(long id) {
    	/** 序列最小长度 */
        return toSerialCode(id,9);
    }
    
    /**
     * 根据ID生成六位随机码
     * @param id ID
     * @return 随机码
     */
    public static String toSerialCode(long id,int s) {
        char[] buf=new char[32];
        int charPos=32;
 
        while((id / binLen) > 0) {
            int ind=(int)(id % binLen);
            // System.out.println(num + "-->" + ind);
            buf[--charPos]=r[ind];
            id /= binLen;
        }
        buf[--charPos]=r[(int)(id % binLen)];
        // System.out.println(num + "-->" + num % binLen);
        String str=new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        if(str.length() < s) {
            StringBuilder sb=new StringBuilder();
            sb.append(b);
            Random rnd=new Random();
            for(int i=1; i < s - str.length(); i++) {
            sb.append(r[rnd.nextInt(binLen)]);
            }
            str+=sb.toString();
        }
        return str;
    }
 
    public static long codeToId(String code) {
        char chs[]=code.toCharArray();
        long res=0L;
        for(int i=0; i < chs.length; i++) {
            int ind=0;
            for(int j=0; j < binLen; j++) {
                if(chs[i] == r[j]) {
                    ind=j;
                    break;
                }
            }
            if(chs[i] == b) {
                break;
            }
            if(i > 0) {
                res=res * binLen + ind;
            } else {
                res=ind;
            }
            // System.out.println(ind + "-->" + res);
        }
        return res;
    }
    
    /**
     * 获取订单编号
     * @param id
     * @return
     */
   /*public static String getOderNumber(long id) {
    	String serialCode = toSerialCode(id,4);
    	OrderJob orderJob = (OrderJob)BeanHelper.getBean("orderJob");
		String orderCount = String.valueOf(orderJob.getOrderCount());
		int i =5-orderCount.length();
		for (int j = 0; j < i; j++) {
			orderCount="0"+orderCount;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
		return serialCode+sdf.format(new Date())+orderCount;
	}*/
    
//    public static void main(String[] args) {
//    	Calendar now = Calendar.getInstance();
////    	int i = now.get(Calendar.MILLISECOND);
////    	System.out.println(i);
//    	int j = now.get(Calendar.MONDAY);
//    	String year = toSerialCode(now.getTimeInMillis(),1);
//    	System.out.println(year);
//    	System.out.println(now.getTimeInMillis());
//    	String month = toSerialCode(now.get(Calendar.MONTH),1);
//    	System.out.println(month);
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
//    	System.out.println(sdf.format(now.getTime()));
//    	System.out.println(j);
////		now.set(Calendar., hour);
////		now.set(Calendar.MINUTE, 0);
////		now.set(Calendar.SECOND, 0);
//	}
}
