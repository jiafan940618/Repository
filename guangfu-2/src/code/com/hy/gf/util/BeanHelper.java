package com.hy.gf.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class BeanHelper implements BeanFactoryAware{
	private static BeanFactory beanFactory;

	public static Object getBean(String id){
		return beanFactory.getBean(id);
	}

	/**
	 * 根据接口名获取实例对象
	 * @author yanyong
	 * @date 2015年11月19日 下午19:11:37
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz){
		return clazz.cast(beanFactory.getBean(
				ClassNameUtil.fromInterfaceToService(clazz)));
	}

	public void setBeanFactory(BeanFactory beanFactory)
			throws BeansException {
		this.beanFactory=beanFactory;
	}
}
