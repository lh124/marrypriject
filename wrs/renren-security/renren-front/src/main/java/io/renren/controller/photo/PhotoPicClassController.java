package io.renren.controller.photo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;

import io.renren.annotation.CheckAuth;
import io.renren.annotation.IgnoreAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.PhotoFrontUserEntity;
import io.renren.entity.PhotoPicClassEntity;
import io.renren.entity.PhotoTypeEntity;
import io.renren.entity.PhotoUserClassEntity;
import io.renren.enums.ClassRoleEnum;
import io.renren.service.PhotoPicClassService;
import io.renren.service.PhotoTypeService;
import io.renren.service.PhotoUserClassService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
@RestController("frontPhotoPicClassController")
@RequestMapping("/front/photopicclass")
public class PhotoPicClassController {
	@Autowired
	private PhotoPicClassService photoPicClassService;
	
	@Autowired
	private PhotoUserClassService photoUserClassService;
	
	@Autowired
	private PhotoTypeService photoTypeService;
	
	/**
	 * 默认分页开始页
	 */
	public static final Integer DEFAULT_PAGE = 1;
	
	/**
	 * 默认分页大小
	 */
	public static final Integer DEFAULT_SIZE = 10;
	
	
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@IgnoreAuth
	public R list(Long typeId){ 
		
		PhotoPicClassEntity ppce = new PhotoPicClassEntity();
		ppce.setTypeId(typeId);
		EntityWrapper<PhotoPicClassEntity> wrapper = new EntityWrapper<PhotoPicClassEntity>(ppce);
		wrapper.orderBy("id", false);
		List<PhotoPicClassEntity> list = this.photoPicClassService.selectList(wrapper);
		
		return R.ok().put("list", list).put("url", PostObjectPolicyController.CDN_URL);
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/listByPage")
	@CheckAuth(needAuth="graduate:all")
	public R listByPage(Long typeId, Integer page, Integer size){ 
		
		PhotoPicClassEntity ppce = new PhotoPicClassEntity();
		ppce.setTypeId(typeId);
		EntityWrapper<PhotoPicClassEntity> wrapper = new EntityWrapper<PhotoPicClassEntity>(ppce);
		
		if (page ==  null) 
			page = DEFAULT_PAGE;
		if (size ==  null) 
			size = DEFAULT_SIZE;
		
		Page<PhotoPicClassEntity> pagee = new Page<PhotoPicClassEntity>(page,size);
		
		wrapper.orderBy("id", false);
		pagee = this.photoPicClassService.selectPage(pagee, wrapper);
		
		return R.ok().put("page", pagee).put("url", PostObjectPolicyController.CDN_URL);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photopicclass:info")
	public R info(@PathVariable("id") Long id){
		PhotoPicClassEntity photoPicClass = photoPicClassService.queryObject(id);
		
		return R.ok().put("photoPicClass", photoPicClass);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photopicclass:save")
	public R save(@RequestBody PhotoPicClassEntity photoPicClass){
		photoPicClassService.save(photoPicClass);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photopicclass:update")
	public R update(@RequestBody PhotoPicClassEntity photoPicClass){
		photoPicClassService.update(photoPicClass);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@CheckAuth(needAuth="graduate:all")
	public R delete(Long id, HttpServletRequest request){
		// 获取班级图片
		PhotoPicClassEntity ppc = photoPicClassService.selectById(id);
		if (ppc == null)
			return R.error("记录不存在");
		
		PhotoTypeEntity pte = this.photoTypeService.selectById(ppc.getTypeId());
		if (pte == null)
			return R.error("所属类型不存在");
		
		// 获取登录用户
		PhotoFrontUserEntity user = (PhotoFrontUserEntity) request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
		
		// 用户验证,防止其他用户非法操作
		PhotoUserClassEntity puce = new PhotoUserClassEntity();
		puce.setClassId(pte.getRelatedId());
		puce.setFrontUserId(user.getId());
		puce.setClassRoleType(ClassRoleEnum.CLASS_ADMIN.getTypeValue());
		EntityWrapper<PhotoUserClassEntity> wrapper = new EntityWrapper<PhotoUserClassEntity>(puce);
		int count = this.photoUserClassService.selectCount(wrapper);
		if ( count < 1) 
			return R.error("没有操作权限,非法操作");

		// 删除记录
		photoPicClassService.delete(id);
		
		return R.ok();
	}
	
}
