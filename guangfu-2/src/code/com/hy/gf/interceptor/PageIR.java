package com.hy.gf.interceptor;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hy.gf.biz.ServerBiz;
import com.hy.gf.biz.UserBiz;
import com.hy.gf.model.Server;
import com.hy.gf.model.User;
import com.hy.gf.util.BeanHelper;
import com.hy.gf.vo.UserVO;

public class PageIR extends HandlerInterceptorAdapter {
	private final Logger log = Logger.getLogger(PageIR.class);
	public static final String LAST_PAGE = "com.alibaba.lastPage";


	String[] ips = { "10.1.1.237" };
	
	@Resource
	UserBiz userBiz;
	@Resource
	ServerBiz serverBiz;
	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		
//		defaultUser(session, "13028837999"); // 设置默认普通用户
//		defaultServer(session, "13450699433"); // 设置默认服务商
//		defaultAdmin(session, "13028837999"); // 设置默认管理员
		
		
		User user = (User)session.getAttribute("user");
		Server server = (Server)session.getAttribute("server");
		User admin = (User)session.getAttribute("admin");
		
		String requestUri = request.getRequestURI();
		if (!requestUri.contains("/manage/") || requestUri.contains("/manage/login.html") || requestUri.contains("/manage/static")) {
			return true;
		}
		
		/*String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());*/
		
		if(server != null){
			return true;
		} else if(admin != null){
			return true;
		} else if(user != null && user.getRoleId() == 6){
			String path = request.getRequestURL().toString().replace(request.getRequestURI(), "");
			response.sendRedirect(path + "/guangfu/younen/index.html");
			return false;
		} else if(server == null || user == null) {
			String path = request.getRequestURL().toString().replace(request.getRequestURI(), "");
			response.sendRedirect(path + "/guangfu/younen/html/login/login.html");
			return false;
		}
		
		return true;
	}
	
	// 设置默认用户
	private void defaultUser(HttpSession session, String phone) {
		User u = new User();
		u.setPhone(phone);
		User userLocal = userBiz.selectOneByExample(u);
		session.setAttribute("user", userLocal);
	}
	// 设置默认管理员
	private void defaultAdmin(HttpSession session, String phone) {
		User u = new User();
		u.setPhone(phone);
		User userLocal = userBiz.selectOneByExample(u);
		session.setAttribute("admin", userLocal);
	}
	// 设置默认服务商
	private void defaultServer(HttpSession session, String phone) {
		Server server = new Server();
		server.setPhone(phone);
		Server serverLocal = serverBiz.selectOneByExample(server);
		session.setAttribute("server", serverLocal);
	}
	
	public String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	/**
	 * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
