package io.renren.constant;

public class ControllerConstant {

	/**  *前端用户在 */
	public static String USER_SESSION_KEY = "frontUser";
	/** *智能校服用户  */
	public static final String SESSION_SMART_USER_KEY = "user_smart";
	/** *墓碑二维码用户  */
	public static final String SESSION_TOMBSTONE_USER_KEY = "user_tombstone";
	/** *码上结婚用户  */
	public static final String SESSION_MARRIED_USER_KEY = "user_married";
	/**  *用户注册后默认密码  */
	public static String DEFAULT_PASSWORD = "000000";
	/**  *等待审核  */ 
	public static Integer USER_CLASS_STATUS_WAIT = 1; 
	/**  *审核通过  */
	public static Integer USER_CLASS_STATUS_NORMAL = 2;
	/**  *删除出班级  */
	public static Integer USER_CLASS_STATUS_FORBIDDEN = 3;
	/**  *图片样式分割符号  */
	public static String PIC_STYLE_SEPERATOR = "!";
	/**  *图片样式 --缩略图  */
	public static String PIC_STYLE_SUOLUE = PIC_STYLE_SEPERATOR + "suofang";
	/**  *验证码session key  */
	public static String CHECK_CODE_SESSION_KEY = "front_user_login_code";
	/**  *班级信息关联照片  */
	public static final int PIC_TYPE_CLASS_MSG = 1;
	
	/**
	 * 班级扫描查看不需要密码
	 */
	public static final int CLASS_PHOTO_PERM_NO = 1;
	
	/**
	 * 班级扫描查看需要密码
	 */
	public static final int CLASS_PHOTO_PERM_NEED = 2;
	/**
	 * session中用户类型保存的键
	 */
	public static final String USER_STATE = "user_state";
	/**
	 * 阿里云cdn加速地址
	 */
	public static String CDN_URL = "http://static.gykjewm.com/";
}
