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
	PHOTO_SMART_NOTICE_PIC(14, "smart_notice_pic_", "智能校服头像图片", "智能校服头像图片上传成功", "智能校服头像图片上传失败", 6, "smart_notice_pic/",
			"http://wrs.gykjewm.com/smart/callBack/msgPic"),
	PHOTO_SMART_CLASSNOTICE_PIC(15, "smart_notice_pic_", "智能校服通知图片", "智能校服通知图片上传成功", "智能校服通知图片上传失败", 6, "smart_notice_pic/",
			"http://wrs.gykjewm.com/smart/callBack/msgPic"),
	PHOTO_SMART_FRESHMANGUIDE_PIC(16, "smart_freshmanguide_pic_", "智能校服新生指南图片", "智能校服新生指南图片上传成功", "智能校服新生指南图片上传失败", 6, "smart_freshmanguide_pic/",
			"http://wrs.gykjewm.com/smart/callBack/msgPic" ),
	PHOTO_SMART_PSYCHOLOGICAL_PIC(17, "smart_psychological_pic_", "智能校服心理咨询图片", "智能校服心理咨询图片上传成功", "智能校服心理咨询图片上传失败", 6, "smart_psychological_pic/",
			"http://wrs.gykjewm.com/smart/callBack/msgPic" ),
	PHOTO_SMART_ACTIVITE_PIC(18, "smart_activity_pic_", "智能校服竞技活动图片", "智能校服竞技活动图片上传成功", "智能校服竞技活动图片上传失败", 6, "smart_activity_pic/",
			"http://wrs.gykjewm.com/smart/callBack/msgPic" ),
	PHOTO_SMART_WORK_PIC(19, "smart_work_pic_", "智能校服作业图片", "智能校服作业图片上传成功", "智能校服作业图片上传失败", 6, "smart_work_pic/",
			"http://wrs.gykjewm.com/smart/callBack/msgPic" ),
	PHOTO_SMART_COURSEWARE_PIC(20, "smart_courseware_pic_", "智能校服随堂课件", "智能校服随堂课件上传成功", "智能校服随堂课件上传失败", 6, "smart_courseware_pic/",
			"http://wrs.gykjewm.com/smart/callBack/msgPic" ),
	PHOTO_SMART_VIDEO_PIC(21, "smart_vodeo_pic_", "智能校服语音", "智能校服语音上传成功", "智能校服语音上传失败", 6, "smart_vodeo_pic/",
			"http://wrs.gykjewm.com/smart/callBack/msgPic" ),
	PHOTO_SMART_PHONE_NOTICE_PIC(22, "smart_notice_pic_", "智能校服班级通知", "智能校服班级通知上传成功", "智能校服班级通知上传失败", 6, "smart_notice_pic/",
			"http://wrs.gykjewm.com/smart/callBack/msgPic" ),
	TOMBSTONE_USER_PIC(23, "tombstone_user_pic_", "墓碑二维码用户头像", "墓碑二维码用户头像上传成功", "墓碑二维码用户头像上传失败", 6, "tombstone_user_pic/",
			"http://wrs.gykjewm.com/tombstone/callBack/msgPic" ),
	TOMBSTONE_DEAD_PIC(24, "tombstone_dead_pic_", "墓碑二维码家谱图片", "墓碑二维码家谱图片上传成功", "墓碑二维码家谱图片上传失败", 6, "tombstone_dead_pic/",
			"http://wrs.gykjewm.com/tombstone/callBack/msgPic" ),
	TOMBSTONE_DEADUSER_PIC(25, "tombstone_deaduser_pic_", "墓碑二维码个人头像", "墓碑二维码个人图片头像成功", "墓碑二维码个人图片头像失败", 6, "tombstone_deaduser_pic/",
			"http://wrs.gykjewm.com/tombstone/callBack/msgPic" );
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
