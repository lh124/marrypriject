package io.renren.interceptor;

import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component("dbTypeIntecepter")
public class DbTypeIntecepter extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {    
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		System.out.println("-----------pre----------");
		return true;    
    }
	
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {    
    	DbContextHolder.setDbType(DBTypeEnum.MYSQL);
    	System.out.println("-----------post----------");
    }
	
}
