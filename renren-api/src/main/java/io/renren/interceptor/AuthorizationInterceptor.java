package io.renren.interceptor;

import io.renren.annotation.IgnoreAuth;
import io.renren.entity.TokenEntity;
import io.renren.service.TokenService;
import io.renren.utils.RRException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 权限(Token)验证
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:38
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private TokenService tokenService;

    public static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

    @SuppressWarnings("unused")
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
    	/*BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
    	request = requestWrapper;*/
    	
    	IgnoreAuth annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        }else{
            return true;
        }
        
        String encoding = request.getCharacterEncoding();
        if (encoding == null) {
        	encoding = "UTF-8";
        }
        
        String json = IOUtils.toString(request.getInputStream(), encoding);
        
        System.out.println("intecepter: " + json.toString());
        JSONObject jsonO = JSONObject.fromObject(json);
        String tocken = "";
        
        if (jsonO.containsKey("tocken") && !jsonO.getString("tocken").equals("")) {
        	tocken = jsonO.getString("tocken");
        } else {
        	throw new RRException("tocken不能为空");
        }
        //如果有@IgnoreAuth注解，则不验证token
        
        if(annotation != null){
            return true;
        }

        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = request.getParameter("token");
        }

        //token为空
        if(StringUtils.isBlank(token)){
            throw new RRException("token不能为空");
        }

        //查询token信息
        TokenEntity tokenEntity = tokenService.queryByToken(token);
        if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
            throw new RRException("token失效，请重新登录");
        }

        //设置userId到request里，后续根据userId，获取用户信息
        request.setAttribute(LOGIN_USER_KEY, tokenEntity.getUserId());

        return true;
    }
}
