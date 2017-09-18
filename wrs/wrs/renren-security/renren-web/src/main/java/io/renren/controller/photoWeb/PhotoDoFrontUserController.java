package io.renren.controller.photoWeb;

import io.renren.constant.ControllerConstant;
import io.renren.entity.PhotoClassEntity;
import io.renren.entity.PhotoClassRoleEntity;
import io.renren.entity.PhotoFrontUserEntity;
import io.renren.entity.PhotoUserClassEntity;
import io.renren.model.dto.FrontUserDto;
import io.renren.model.json.ResponseDTJson;
import io.renren.service.PhotoClassService;
import io.renren.service.PhotoFrontUserInfoService;
import io.renren.service.PhotoFrontUserService;
import io.renren.service.PhotoUserClassService;
import io.renren.validator.Assert;
import io.renren.validator.ValidatorUtils;
import io.renren.validator.group.AddGroup;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

@Controller
@RequestMapping("/sys/front")
public class PhotoDoFrontUserController {

	@Autowired
	private PhotoFrontUserService photoFrontUserService;
	@Autowired
	private PhotoClassService photoClassService;
	@Autowired
	private PhotoUserClassService photoUserClassService;
	@Autowired
	private PhotoFrontUserInfoService photoFrontUserInfoService;
	
	
	@ResponseBody
	@RequestMapping("/addUsers")
	@RequiresPermissions("sys:front:addUser")
	public ResponseDTJson<PhotoFrontUserEntity> addFrontUser(FrontUserDto user){
		
		ValidatorUtils.validateEntity(user, AddGroup.class);
		// 检查班级
		PhotoClassEntity pClass = this.photoClassService.queryObject(user.getClassId());
		Assert.isNull(pClass, "班级不存在");
		
		//检查角色类型
		PhotoClassRoleEntity role = new PhotoClassRoleEntity();
		role.setRoleType(user.getRoleType());
		EntityWrapper<PhotoClassRoleEntity> wrapper = new EntityWrapper<PhotoClassRoleEntity>(role);
		
		
		// 数据验证完成后进行数据插入
		PhotoFrontUserEntity frontUser = new PhotoFrontUserEntity();
		frontUser.setAccount(user.getAccount());
		frontUser.setNickname(user.getNickname());
		frontUser.setPhone(user.getPhone());
		frontUser.setSex(user.getSex());
		
		this.photoFrontUserService.save(frontUser);
		
		PhotoUserClassEntity puc = new PhotoUserClassEntity();
		puc.setClassId(user.getClassId());
		puc.setClassRoleType(user.getRoleType());
		puc.setFrontUserId(frontUser.getId());
		puc.setStatus(ControllerConstant.USER_CLASS_STATUS_NORMAL);
		
		this.photoUserClassService.save(puc);
		
		@SuppressWarnings("unchecked")
		ResponseDTJson<PhotoFrontUserEntity> response = ResponseDTJson.getResponseInstance();
		
		return response;
		
	}
	
	@ResponseBody
	@RequestMapping("/checkUnique")
	@RequiresPermissions("sys:front:addUser")
	public ResponseDTJson<PhotoFrontUserEntity> checkUnique(String account){
		
		return null;
	}
	
	
}
