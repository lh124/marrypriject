package io.renren.controller.photo;

import io.renren.annotation.CheckAuth;
import io.renren.annotation.IgnoreAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.PhotoFrontUserEntity;
import io.renren.entity.PhotoFunctionModulesEntity;
import io.renren.entity.PhotoUserFunctionEntity;
import io.renren.enums.WeChatEnum;
import io.renren.model.json.ResponseDTJson;
import io.renren.service.PhotoFrontUserService;
import io.renren.service.PhotoFunctionModulesService;
import io.renren.service.PhotoUserFunctionService;
import io.renren.utils.HttpsClient;
import io.renren.utils.MD5;
import io.renren.utils.R;
import io.renren.utils.Sign;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;
import io.renren.validator.Assert;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.code.kaptcha.Producer;

/**
 * API登录授权
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:31
 */
@RestController
@RequestMapping("/front")
public class FrontLoginController{
    
	public static String GET_TOCKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
	public static String WECHAT_APPID = "wxb9072ff1ebcf745c";
	public static String WECHAT_SECRTE = "b298e38e02eb3d45ca5cc22c68e9bae5";
	
	// 用户类型 
	/**
	 * 相册系统用户
	 */
	public static String STATE_PHOTO_SYSTEM_USER = "1";
	/**
	 * 智能校服用户
	 */
	public static String STATE_SMART_SYSTEM_USER = "2";
	
	@Autowired
	private PhotoFrontUserService photoFrontUserService ;
	@Autowired
	private Producer producer;
	@Autowired
	private PhotoFunctionModulesService photoFunctionModulesService;
	@Autowired
	private PhotoUserFunctionService photoUserFunctionService;
	
	@IgnoreAuth
	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到 session 
        request.getSession().setAttribute(ControllerConstant.CHECK_CODE_SESSION_KEY, text);
        
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
	}
	
    /**
     * 登录
     */
    @IgnoreAuth
    @RequestMapping("userLogin")
    public R login(HttpServletRequest request,String mobile, String password, String checkCode, String state){
    	Assert.isBlank(checkCode, "验证码不能为空");
        Assert.isBlank(mobile, "账号能为空");
        Assert.isBlank(password, "密码不能为空");
        
        // 验证验证码
        Map<String, Object> map = new HashMap<String, Object>();
        String code = (String) request.getSession().getAttribute(ControllerConstant.CHECK_CODE_SESSION_KEY);
        
        if (!StringUtils.isBlank(checkCode) && !StringUtils.isBlank(code)) {
        	if (!code.equals(checkCode)) {
        		map.put("status", ResponseDTJson.FAIL);
            	map.put("msg","验证码错误");
            	return R.ok(map);
        	}
        	
        } else {
        	map.put("status", ResponseDTJson.FAIL);
        	map.put("msg","验证码错误");
        	return R.ok(map);
        }
        
        //进行用户验证
        map = this.swipUser(request, mobile, password, state);
        
        return R.ok(map);
    }
    
    /**
     * 绑定微信
     * @throws Exception 
     */
    @RequestMapping("weChatBinding")
    @CheckAuth(needAuth="user:logined")
    public R weChatBinding(HttpServletRequest request, String code) throws Exception{
    	Assert.isBlank(code, "code不能为空");
        
        StringBuffer str = new StringBuffer(GET_TOCKEN_URL);
        str.append("?");
        str.append("appid=" + WECHAT_APPID);
        str.append("&secret=" + WECHAT_SECRTE);
        str.append("&code=" + code);
        str.append("&grant_type=authorization_code");
        
        String back = HttpsClient.httpsGet(str.toString());
        System.out.println(back);
        JSONObject json = JSONObject.fromObject(back);
        String openid = json.containsKey("openid") ? json.getString("openid") : null;
        
        if (openid != null) {
        	PhotoFrontUserEntity user = (PhotoFrontUserEntity) request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
        	
        	PhotoFrontUserEntity userNew = new PhotoFrontUserEntity();
        	userNew.setId(user.getId());
        	userNew.setOpenid(openid);
        	this.photoFrontUserService.update(userNew);
        	
        	return R.ok();
        } else {
        	return R.error().put("status", "oppenid无法获取");
        }
        
        
    }
    
    /**
     * 登录
     * @throws Exception 
     */
    @RequestMapping("weChatuserLogin")
    @IgnoreAuth
    public R weChatuserLogin(HttpServletRequest request,String code) throws Exception{
    	Assert.isBlank(code, "code不能为空");
        
        StringBuffer str = new StringBuffer(GET_TOCKEN_URL);
        str.append("?");
        str.append("appid=" + WECHAT_APPID);
        str.append("&secret=" + WECHAT_SECRTE);
        str.append("&code=" + code);
        str.append("&grant_type=authorization_code");
        
        String back = HttpsClient.httpsGet(str.toString());
        System.out.println(back);
        JSONObject json = JSONObject.fromObject(back);
        String openid = json.containsKey("openid") ? json.getString("openid") : null;
        
        if (openid != null) {
        	
        	PhotoFrontUserEntity userNew = new PhotoFrontUserEntity();
        	userNew.setOpenid(openid);
        	EntityWrapper<PhotoFrontUserEntity> wrapper = new EntityWrapper<PhotoFrontUserEntity>(userNew);
        	
        	userNew = this.photoFrontUserService.selectOne(wrapper);
        	
        	if ( userNew != null && userNew.getAccount() != null ) {
        		userNew = this.photoFrontUserService.queryByAccount(userNew.getAccount());
        		request.getSession().setAttribute(ControllerConstant.USER_SESSION_KEY, userNew);
        		
        		return R.ok("微信绑定成功,可在个人中心取消绑定");
        	} else {
        		
        		return R.error("未绑定微信").put("status", ResponseDTJson.FAIL);
        	}
        } else {
        	return R.error().put("status", "oppenid无法获取");
        }
        
        
    }
    
    @RequestMapping("getWeChatSign")
    @CheckAuth(needAuth="user:logined")
    public Map<String, String> getWeChatSign(HttpServletRequest request, String url) throws Exception{
    	
    	ServletContext application = request.getSession().getServletContext();
    	
    	String httpUrl = request.getParameter("url");
  		String tickets = "ticket";
  		String access_tocken = "access_token";
  		
  		Map<String, String > map = null;
  		String ticket = (String)application.getAttribute(tickets);
  		
  		
  		if(ticket != null && !ticket.equals(""))
  		{
  			Long timeStamp = (Long)application.getAttribute("time");
  			if(timeStamp == null)
  			{
  				timeStamp = 3l;
  			}
  			Long cha = (System.currentTimeMillis()-timeStamp)/(1000*60*60);
  			
  			if(cha > 2)
  			{
  				
  				// 重新生成ticket  
  				String back = HttpsClient.httpsGet(WeChatEnum.WEICHAT_URL_TOCHEN.getValue());
				String back2 = HttpsClient.httpsGet(WeChatEnum.WEICHAT_URL_TICHET.getValue() + JSONObject.fromObject(back).getString(access_tocken));
				application.setAttribute(tickets, JSONObject.fromObject(back2).getString(tickets));
				application.setAttribute("time", System.currentTimeMillis());
				map = Sign.sign(JSONObject.fromObject(back2).getString(tickets), httpUrl);
				
				// 系统输出
				System.out.println(back);
				System.out.println(back2);
  			}else{
  				map = Sign.sign(ticket, httpUrl);
  			}
  		}else{
  			// 重新生成ticket  
 		    String back = HttpsClient.httpsGet(WeChatEnum.WEICHAT_URL_TOCHEN.getValue());
			String back2 = HttpsClient.httpsGet(WeChatEnum.WEICHAT_URL_TICHET.getValue() + JSONObject.fromObject(back).getString(access_tocken));
			
			application.setAttribute(tickets, JSONObject.fromObject(back2).getString(tickets));
			application.setAttribute("time", System.currentTimeMillis());
			map = Sign.sign(JSONObject.fromObject(back2).getString(tickets), httpUrl);
			// 系统输出
			System.out.println(back);
			System.out.println(back2);
  		}
  		
  		return map;
    }
    
    @RequestMapping("setUserPosition")
    @CheckAuth(needAuth="user:logined")
    public R setUserPosition(HttpServletRequest request, String latitude, String longitude) throws Exception{
    	
    	Assert.isBlank(latitude, "纬度不能为空");
    	Assert.isBlank(longitude, "经度不能为空");
  		
    	PhotoFrontUserEntity user = (PhotoFrontUserEntity) request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
    	
    	PhotoFrontUserEntity userNew = new PhotoFrontUserEntity();
    	userNew.setId(user.getId());
    	userNew.setLatitude(latitude);
    	userNew.setLongitude(longitude);
    	
    	
    	this.photoFrontUserService.update(userNew);
    	
  		return R.ok().put("status", ResponseDTJson.SUCCESS);
    }
    
    @RequestMapping("/loginOut")
    @CheckAuth(needAuth="user:logined")
	public R loginOut(HttpServletRequest request){
		HttpSession session = request.getSession();
		PhotoFrontUserEntity user = (PhotoFrontUserEntity) session.getAttribute(ControllerConstant.USER_SESSION_KEY);
		List<PhotoFunctionModulesEntity> authList = user.getAuthList();
		String[] permArray = new String[authList.size()];
		for(int i=0; i < authList.size(); i++){
			permArray[i] = authList.get(i).getPerm();
		}
		
		session.removeAttribute(ControllerConstant.USER_SESSION_KEY);
		
		return R.ok("注销成功").put("permArray", permArray);
	}
	
    /**
     * 根据state的值不同判断用户类型，并保存到
     * @return
     */
    private Map<String, Object> swipUser(HttpServletRequest request,String account, String password, String state){
    	//初始化返回数据对象
    	Map<String, Object > map = new HashMap<String, Object>();
    	
    	// 相册系统用户
		PhotoFrontUserEntity user =  this.photoFrontUserService.queryByAccount(account);
		
		if ( user == null){
        	map.put("status", ResponseDTJson.FAIL);
        	map.put("msg","用户不存在");
        	
        	return map;
        }
        
        //  兼容老版本登录,老版本加密使用的是MD5的
        if (user.getPassword().equals(new Sha256Hash(password).toHex()) || 
        		user.getPassword().equals(MD5.md5(password)) ) {
        	
        	map.put("status", ResponseDTJson.SUCCESS);
        	map.put("msg","登录成功");
        	
        	request.getSession().setAttribute(ControllerConstant.USER_SESSION_KEY, user);
        	
        } else {
        	map.put("status", ResponseDTJson.FAIL);
        	map.put("msg","密码错误");
        }
    	
    	return map;
    }

}
