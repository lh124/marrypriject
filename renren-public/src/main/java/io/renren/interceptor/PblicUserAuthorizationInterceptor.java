package io.renren.interceptor;


import io.renren.annotation.IgnoreAuth;
import io.renren.constant.ControllerConstant;
import io.renren.util.PublicInterceptorUrlUtil;
import io.renren.utils.RRException;
import io.renren.utils.ShiroUtil;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 权限(Token)验证
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:38
 */
@Component(value="pblicUseraAuthorizationInterceptor")
public class PblicUserAuthorizationInterceptor extends HandlerInterceptorAdapter {
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	//判断是页面请求还是ajax请求
    	String url = request.getRequestURI();
    	RRException  e = null;
    	
    	// controller方法上的注解
    	IgnoreAuth annotation;
    	
    	// 当不存在此请求时，没有对应的action或者HTML页面时
        if(!(handler instanceof HandlerMethod)){
        	e = new RRException("请求地址错误");
        	throw e;
		}
        
        // 获取注解对象
        annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        
        //如果有@IgnoreAuth注解，则不验证登录
        if(annotation != null){
            return true;
        }
        
        // 判断不过滤的路径
        if( PublicInterceptorUrlUtil.authCheck(url)) 
    		return true;
       
        // 获取登录用户
       return userIsOnLine(request.getSession());
      
    }
    /**
     * 判断用户是否登录
     * @param session
     * @return
     */
    public boolean userIsOnLine(HttpSession session){
    	
    	// 登录智能校服
    	if (session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY) != null)
    		return true;
    	// 登录墓碑二维码
    	if (session.getAttribute(ControllerConstant.SESSION_TOMBSTONE_USER_KEY) != null)
    		return true;
    	// 学生相册系统
    	if (session.getAttribute(ControllerConstant.USER_SESSION_KEY) != null)
    		return true;
    	//登录管理系统
    	if (ShiroUtil.isLogin())
    		return true;
    	
    	RRException e = new RRException("请先登录");
    	throw e;
    }
}
