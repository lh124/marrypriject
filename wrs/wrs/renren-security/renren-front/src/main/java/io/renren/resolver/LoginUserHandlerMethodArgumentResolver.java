package io.renren.resolver;


import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 22:02
 */
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter arg0,
			ModelAndViewContainer arg1, NativeWebRequest arg2,
			WebDataBinderFactory arg3) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean supportsParameter(MethodParameter arg0) {
		// TODO Auto-generated method stub
		return false;
	}
    
}
