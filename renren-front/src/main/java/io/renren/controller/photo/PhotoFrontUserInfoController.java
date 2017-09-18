package io.renren.controller.photo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import io.renren.annotation.CheckAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.PhotoFrontUserEntity;
import io.renren.entity.PhotoFrontUserInfoEntity;
import io.renren.service.PhotoFrontUserInfoService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;


/**
 * 用户信息表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
@RestController
@RequestMapping("front/photofrontuserinfo")
public class PhotoFrontUserInfoController {
	@Autowired
	private PhotoFrontUserInfoService photoFrontUserInfoService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photofrontuserinfo:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoFrontUserInfoEntity> photoFrontUserInfoList = photoFrontUserInfoService.queryList(query);
		int total = photoFrontUserInfoService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoFrontUserInfoList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photofrontuserinfo:info")
	public R info(@PathVariable("id") Long id){
		PhotoFrontUserInfoEntity photoFrontUserInfo = photoFrontUserInfoService.queryObject(id);
		
		return R.ok().put("photoFrontUserInfo", photoFrontUserInfo);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photofrontuserinfo:save")
	public R save(@RequestBody PhotoFrontUserInfoEntity photoFrontUserInfo){
		photoFrontUserInfoService.save(photoFrontUserInfo);
		
		return R.ok();
	}
	
	/**
	 * 获取班级相册查看权限，是否需要密码
	 */
	@RequestMapping("/secretInfo")
	@CheckAuth(needAuth="user:logined")
	public R secretInfo(HttpServletRequest request){
		// 获取登录用户
		PhotoFrontUserEntity user = (PhotoFrontUserEntity) request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
		// 查询用户信息记录
		PhotoFrontUserInfoEntity pfuie = new PhotoFrontUserInfoEntity();
		pfuie.setFrontUserId(user.getId());
		EntityWrapper<PhotoFrontUserInfoEntity> wrapper = new EntityWrapper<PhotoFrontUserInfoEntity>();
		pfuie = this.photoFrontUserInfoService.selectOne(wrapper);
		
		if ( pfuie != null ) {
			
			if ( pfuie.getPerm() != null ) {
				return R.ok().put("perm", pfuie.getPerm());
			} else {
				return R.ok().put("perm", ControllerConstant.CLASS_PHOTO_PERM_NO);
			}
			
		} 
		
		return R.ok().put("perm", ControllerConstant.CLASS_PHOTO_PERM_NO);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@CheckAuth(needAuth="user:logined")
	public R update(PhotoFrontUserInfoEntity photoFrontUserInfo, HttpServletRequest request){
		// 获取登录用户
		PhotoFrontUserEntity user = (PhotoFrontUserEntity) request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
		// 查询用户信息记录
		PhotoFrontUserInfoEntity pfuie = new PhotoFrontUserInfoEntity();
		pfuie.setFrontUserId(user.getId());
		EntityWrapper<PhotoFrontUserInfoEntity> wrapper = new EntityWrapper<PhotoFrontUserInfoEntity>();
		pfuie = this.photoFrontUserInfoService.selectOne(wrapper);
		
		if ( photoFrontUserInfo != null && photoFrontUserInfo.getPassword() != null && photoFrontUserInfo.getPassword() != "") {
			// 密码加密
			photoFrontUserInfo.setPassword(new Sha256Hash(photoFrontUserInfo.getPassword()).toHex());
		}
		
		if (pfuie != null) {
			photoFrontUserInfo.setId(pfuie.getId());
			this.photoFrontUserInfoService.update(photoFrontUserInfo);
		} else {
			// 如果不存在就保存记录
			photoFrontUserInfo.setFrontUserId(user.getId());
			photoFrontUserInfo.setGtmCreate(new Date());
			// 设置默认值
			if (photoFrontUserInfo.getPerm() == null)
				photoFrontUserInfo.setPerm(ControllerConstant.CLASS_PHOTO_PERM_NO);
			
			if (photoFrontUserInfo.getPassword() == null)
				photoFrontUserInfo.setPassword(new Sha256Hash("000000").toHex());
			
			this.photoFrontUserInfoService.save(photoFrontUserInfo);
		}
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photofrontuserinfo:delete")
	public R delete(@RequestBody Long[] ids){
		photoFrontUserInfoService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
