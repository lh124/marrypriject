package io.renren.interceptor;

import java.util.List;

import io.renren.annotation.CheckAuth;
import io.renren.annotation.IgnoreAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.PhotoFrontUserEntity;
import io.renren.entity.PhotoFunctionModulesEntity;
import io.renren.service.PhotoFrontUserService;
import io.renren.util.InterceptorUrlUtil;
import io.renren.utils.RRException;

import org.springframework.beans.factory.annotation.Autowired;
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
@Component(value="useraAuthorizationInterceptor")
public class UserAuthorizationInterceptor extends HandlerInterceptorAdapter {
	
	public String LOGIN_URL = "/front/login.html"; 
	@Autowired
    private PhotoFrontUserService photoFrontUserService;
	

    

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	//判断是页面请求还是ajax请求
    	String url = request.getRequestURI();
    	
    	RRException  e = null;
    	HttpSession session = request.getSession();
    	
    	// controller方法上的注解
    	IgnoreAuth annotation;
    	CheckAuth authAnnotation;
    	
    	// 当不存在此请求时，没有对应的action或者HTML页面时
        if(!(handler instanceof HandlerMethod)){
        	return errorTurnHandler(url,request,response,"请求地址错误");
		}
        
        // 获取注解对象
        annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        authAnnotation = ((HandlerMethod) handler).getMethodAnnotation(CheckAuth.class);
        
        //如果有@IgnoreAuth注解，则不验证登录
        if(annotation != null){
            return true;
        }
        
        // 判断不过滤的路径
        if( InterceptorUrlUtil.authCheck(url)) 
    		return true;
        
        // 获取登录用户类型
        Object frontUser = session.getAttribute(ControllerConstant.USER_SESSION_KEY);;
        
        
        if (frontUser == null) {
        	return errorTurnHandler(url,request,response,"请先登录");
        }
        
        // 已登录,进行权限认证
        if (authAnnotation == null || authAnnotation.needAuth().equals("")) {
        	// 无需权限认证
        	return true;
        } 
        
        // 进行权限认证
        String auth = authAnnotation.needAuth();
        List<PhotoFunctionModulesEntity> authList = null;
        
        PhotoFrontUserEntity fu = (PhotoFrontUserEntity)frontUser;
    	authList = fu.getAuthList();
        
        // 匹配是否有权限
        for (PhotoFunctionModulesEntity ufe : authList) {
        	if(auth.equals(ufe.getPerm()))
        		return true;
        }
        
        e = new RRException("没有访问权限");
        throw e;
      
    }
    
    /**
     * 
     * @param url 获取地址
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public boolean errorTurnHandler(String url, HttpServletRequest request, HttpServletResponse response, String errorMessage) throws Exception{
    	boolean isPage = url.indexOf(".html") >= 0;
    	
    	if (isPage) {
    		// 如果是页面的话就跳转到登录页面
    		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + LOGIN_URL;
    		response.sendRedirect(basePath);
    		return false;
    	} else {
    		// 如果是action就返回json
    		RRException e = new RRException(errorMessage);
			e.setCode(4);
	        throw e;
    	}
    }
}
