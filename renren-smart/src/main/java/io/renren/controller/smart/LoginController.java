package io.renren.controller.smart;

import io.renren.annotation.IgnoreAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.smart.StudentEntity;
import io.renren.model.json.ResponseDTJson;
import io.renren.service.smart.StudentService;
import io.renren.utils.HttpsClient;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;
import io.renren.validator.Assert;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RestController("LoginControllerSmart")
@RequestMapping("/smart")
public class LoginController{
    
	
	// 日志操作
	private Logger logger = LoggerFactory.getLogger(this.getClass());
		
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
	private Producer producer;
	
	@Autowired
	private StudentService studentService;
	
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
    @RequestMapping("/userLogin")
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
     // 智能校服
		StudentEntity user = new StudentEntity();
		user.setStudentNo(mobile);
		EntityWrapper<StudentEntity> wrapper = new EntityWrapper<StudentEntity>(user);
		// 改变查询数据库
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		user = this.studentService.selectOne(wrapper);
		// 切换为默认查询数据库
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		
		if ( user == null){
        	return R.error("用户不存在").put("status", ResponseDTJson.FAIL);
        }
        
        if (user.getPasswordd() != null && user.getPasswordd().equals(new Sha256Hash(password).toHex())) {
        	
        	map.put("status", ResponseDTJson.SUCCESS);
        	map.put("msg","登录成功");
    		
        	request.getSession().setAttribute(ControllerConstant.SESSION_SMART_USER_KEY, user);
        } else {
        	
        	return R.error("密码错误").put("status", ResponseDTJson.FAIL);
        }
        
        return R.ok(map);
    }
    
    /**
     * 微信绑定
     * @throws Exception 
     */
    @RequestMapping("/user/weChatBinding")
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
        	StudentEntity user = (StudentEntity) request.getSession().getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
        	
        	StudentEntity userNew = new StudentEntity();
        	userNew.setId(user.getId());
        	userNew.setOpenId(openid);
        	DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        	this.studentService.update(userNew);
        	DbContextHolder.setDbType(DBTypeEnum.MYSQL);
        	
        	return R.ok("绑定成功");
        } else {
        	return R.error().put("status", "oppenid无法获取");
        }
        
        
    }
    
    /**
     * 微信绑定
     * @throws Exception 
     */
    @RequestMapping("/user/cancelWeChatBinding")
    public R cancelWeChatBinding(HttpServletRequest request, String code) throws Exception{
    	StudentEntity user = (StudentEntity) request.getSession().getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
    	
    	DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
    	this.studentService.updateOpenId(user.getId().longValue());
    	DbContextHolder.setDbType(DBTypeEnum.MYSQL);
    	
    	return R.ok("取消绑定绑定成功");
    }
    
    /**
     * 微信登录
     * @throws Exception 
     */
    @RequestMapping("/user/weChatuserLogin")
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
        	
        	StudentEntity userNew = new StudentEntity();
        	userNew.setOpenId(openid);
        	EntityWrapper<StudentEntity> wrapper = new EntityWrapper<StudentEntity>(userNew);
        	
        	DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        	userNew = this.studentService.selectOne(wrapper);
        	DbContextHolder.setDbType(DBTypeEnum.MYSQL);
        	
        	if ( userNew != null && userNew.getStudentNo() != null ) {
        		
        		request.getSession().setAttribute(ControllerConstant.SESSION_SMART_USER_KEY, userNew);
        		
        		return R.ok("登录成功");
        	} else {
        		return R.error("未绑定微信").put("status", ResponseDTJson.FAIL);
        	}
        } else {
        	return R.error().put("status", "oppenid无法获取");
        }
        
        
    }
    
    
    @RequestMapping("/user/setUserPosition")
    public R setUserPosition(HttpServletRequest request, String latitude, String longitude) throws Exception{
    	
    	Assert.isBlank(latitude, "纬度不能为空");
    	Assert.isBlank(longitude, "经度不能为空");
  		
    	logger.error(latitude + " " + longitude);
    	
    	StudentEntity user = (StudentEntity) request.getSession().getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
    	
    	StudentEntity userNew = new StudentEntity();
    	userNew.setId(user.getId());
    	userNew.setLatitude(latitude);
    	userNew.setLongitude(longitude);
    	
    	DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
    	this.studentService.update(userNew);
    	DbContextHolder.setDbType(DBTypeEnum.MYSQL);
  		return R.ok().put("status", ResponseDTJson.SUCCESS);
    }
    
    @RequestMapping("/user/userInfo")
    public R userInfo(HttpSession session){
    	
    	DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
    	StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
    	student = this.studentService.selectById(student.getId());
    	DbContextHolder.setDbType(DBTypeEnum.MYSQL);
    	student.setPasswordd("");
    	
    	return R.ok("查询成功").put("info", student);
    }
    
    @RequestMapping("/user/loginOut")
	public R loginOut(HttpServletRequest request){
		HttpSession session = request.getSession();
		StudentEntity user = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		
		if (user != null) {
			session.removeAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
			return R.ok("注销成功");
		} else {
			return R.error("未登录");
		}
		
	}
    
    
}
