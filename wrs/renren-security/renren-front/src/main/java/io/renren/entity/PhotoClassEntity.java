package io.renren.entity;

import io.renren.validator.group.AddGroup;
import io.renren.validator.group.UniformGroup;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-10 17:28:03
 */
 @TableName("photo_class")
public class PhotoClassEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//id
	@TableId(value="id",type=IdType.AUTO)
	private Long id;	
		
	//班级名
	@TableField(value="name")
	@NotBlank(message = "班级名不能为空", groups = {AddGroup.class, UniformGroup.class})
	private String name;
			
	//查看权限
	/**
	 * 1表示不需要密码，2表示需要密码
	 */
	@TableField(value="perm")
	@NotNull(message = "班级查看权限不能为空", groups = {AddGroup.class})
	private Integer perm;
			
	//班级描述
	@TableField(value="class_desc")
	private String classDesc;
			
	//背景音乐
	@TableField(value="music")
	private String music;
	
	//班级视频
	@TableField(value="video")
	private String video;
			
	//班级logo
	@TableField(value="logo")
	private String logo;
			
	//创建时间
	@TableField(value="gmt_create")
	private Date gmtCreate;
			
	//修改时间
	@TableField(value="gmt_modified")
	private Date gmtModified;
			
	//毕业时间
	@TableField(value="graduation_time_id")
	@NotNull(message="毕业时间不能为空", groups = {AddGroup.class, UniformGroup.class})
	private Long graduationTimeId;
			
	//所属学校
	@TableField(value="school_id")
	@NotNull(message="所属学校id不为空", groups = {AddGroup.class})
	private Long schoolId;
			
	//所属学院
	@TableField(value="college_id")
	private Long collegeId;
			
	//班级浏览密码
	@TableField(value="password")
	private String password;
	
	//班级状态
	@TableField(value="status")
	private Integer status;
	
	//班级使用分类
	@TableField(value="classify")
	private Integer classify;
			
	// 转载集合
	@TableField(exist=false)
	private List<PhotoTypeEntity> photoTypeList;
	@TableField(exist=false)
	private List<PhotoFrontUserEntity> userList;
	
	/**
	 * 设置：id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：班级名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：班级名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：查看权限
	 */
	public void setPerm(Integer perm) {
		this.perm = perm;
	}
	/**
	 * 获取：查看权限
	 */
	public Integer getPerm() {
		return perm;
	}
	/**
	 * 设置：班级描述
	 */
	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}
	/**
	 * 获取：班级描述
	 */
	public String getClassDesc() {
		return classDesc;
	}
	/**
	 * 设置：背景音乐
	 */
	public void setMusic(String music) {
		this.music = music;
	}
	/**
	 * 获取：背景音乐
	 */
	public String getMusic() {
		return music;
	}
	/**
	 * 设置：班级logo
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * 获取：班级logo
	 */
	public String getLogo() {
		return logo;
	}
	/**
	 * 设置：创建时间
	 */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getGmtCreate() {
		return gmtCreate;
	}
	/**
	 * 设置：修改时间
	 */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getGmtModified() {
		return gmtModified;
	}
	/**
	 * 设置：毕业时间
	 */
	public void setGraduationTimeId(Long graduationTimeId) {
		this.graduationTimeId = graduationTimeId;
	}
	/**
	 * 获取：毕业时间
	 */
	public Long getGraduationTimeId() {
		return graduationTimeId;
	}
	/**
	 * 设置：所属学校
	 */
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	/**
	 * 获取：所属学校
	 */
	public Long getSchoolId() {
		return schoolId;
	}
	/**
	 * 设置：所属学院
	 */
	public void setCollegeId(Long collegeId) {
		this.collegeId = collegeId;
	}
	/**
	 * 获取：所属学院
	 */
	public Long getCollegeId() {
		return collegeId;
	}
	/**
	 * 设置：班级浏览密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：班级浏览密码
	 */
	public String getPassword() {
		return password;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getClassify() {
		return classify;
	}
	public void setClassify(Integer classify) {
		this.classify = classify;
	}
	public List<PhotoTypeEntity> getPhotoTypeList() {
		return photoTypeList;
	}
	public void setPhotoTypeList(List<PhotoTypeEntity> photoTypeList) {
		this.photoTypeList = photoTypeList;
	}
	public List<PhotoFrontUserEntity> getUserList() {
		return userList;
	}
	public void setUserList(List<PhotoFrontUserEntity> userList) {
		this.userList = userList;
	}
}
