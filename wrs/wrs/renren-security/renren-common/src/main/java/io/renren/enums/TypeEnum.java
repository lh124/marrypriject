package io.renren.enums;

/**
 * 处理类型对应的照片操作
 * @author Administrator
 *
 */
public enum TypeEnum {

	PHOTO_USER_HEAD(1, "head_img_", "头像图标","头像上传成功","头像上传失败", 6, "head_img/","http://wrs.gykjewm.com/sys/aliOss/callBack"),
	PHOTO_SCHOOL(2, "school_", "学校照片", "学校照片上传成功", "学校照片上传失败", 6, "photo/","http://wrs.gykjewm.com/sys/aliOss/callBack"),
	PHOTO_CLASS(3, "class_", "班级照片", "班级照片上传成功", "班级照片上传失败", 6, "photo/","http://wrs.gykjewm.com/sys/aliOss/callBack"),
	PHOTO_HOME(4, "home_", "家庭照片", "家庭照片上传成功", "家庭照片上传失败", 6, "photo/","http://wrs.gykjewm.com/sys/aliOss/callBack"),
	PHOTO_USER(5, "user_", "个人照片", "个人照片上传成功", "个人照片上传失败", 6, "user/","http://wrs.gykjewm.com/sys/aliOss/callBack"),
	PHOTO_SCHOOL_MUSIC(6, "school_m_", "学校音乐", "学校音乐上传成功", "学校音乐上传失败", 6, "music/","http://wrs.gykjewm.com/sys/aliOss/callBack"),
	PHOTO_CLASS_MUSIC(7, "class_m_", "班级音乐", "班级音乐上传成功", "班级音乐上传失败", 6, "music/","http://wrs.gykjewm.com/sys/aliOss/callBack"),
	PHOTO_SCHOOL_VEDIO(8, "school_v_", "学校视频", "学校视频上传成功", "学校视频上传失败", 6, "video/","http://wrs.gykjewm.com/sys/aliOss/callBack"),
	PHOTO_CLASS_VEDIO(10, "class_v_", "班级视频", "班级视频上传成功", "班级视频上传失败", 6, "video/","http://wrs.gykjewm.com/sys/aliOss/callBack"),
	PHOTO_CLASS_WORK_MSG(11, "class_work_msg_", "班级视频", "班级视频上传成功", "班级视频上传失败", 6, "message/","http://wrs.gykjewm.com/sys/aliOss/callBack"),
	PHOTO_SMART_MSG_PIC(12, "smart_msg_pic_", "智能校服班级信息图片", "智能校服班级信息图片上传成功", "智能校服班级信息图片上传失败", 6, "smart_msg_pic/",
			"http://wrs.gykjewm.com/smart/callBack/msgPic"),
	PHOTO_SMART_HEAD_PIC(13, "smart_head_pic_", "智能校服头像图片", "智能校服头像图片上传成功", "智能校服头像图片上传失败", 6, "smart_head_pic/",
			"http://wrs.gykjewm.com/smart/callBack/msgPic"),
	PHOTO_SMART_NOTICE_PIC(14, "smart_notic_pic_", "智能校服通知图片", "智能校服通知图片上传成功", "智能校服通知图片上传失败", 6, "smart_notic_pic/",
			"http://wrs.gykjewm.com/smart/callBack/msgPic");

	private Integer type;
	private String dirPrefix;
	private String description;
	private String msgSuccess;
	private String msgFail;
	private Integer defaultOrder;
	private String document;
	private String callBackUrl;
	
	private TypeEnum(Integer type, String dirPrefix, String description,
			String msgSuccess, String msgFail, Integer defaultOrder, String document, String callBackUrl){
		this.type = type;
		this.dirPrefix = dirPrefix;
		this.description = description;
		this.msgSuccess = msgSuccess;
		this.msgFail = msgFail;
		this.defaultOrder = defaultOrder;
		this.document = document;
		this.callBackUrl = callBackUrl;
	}
	
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDirPrefix() {
		return dirPrefix;
	}
	public void setDirPrefix(String dirPrefix) {
		this.dirPrefix = dirPrefix;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMsgSuccess() {
		return msgSuccess;
	}
	public void setMsgSuccess(String msgSuccess) {
		this.msgSuccess = msgSuccess;
	}
	public String getMsgFail() {
		return msgFail;
	}
	public void setMsgFail(String msgFail) {
		this.msgFail = msgFail;
	}
	public Integer getDefaultOrder() {
		return defaultOrder;
	}
	public void setDefaultOrder(Integer defaultOrder) {
		this.defaultOrder = defaultOrder;
	}


	public String getDocument() {
		return document;
	}


	public void setDocument(String document) {
		this.document = document;
	}


	public String getCallBackUrl() {
		return callBackUrl;
	}


	public void setCallBackUrl(String callBackUrl) {
		this.callBackUrl = callBackUrl;
	}
	
	
}
