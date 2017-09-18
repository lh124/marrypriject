package io.renren.controller.photo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.annotation.CheckAuth;
import io.renren.annotation.IgnoreAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.PhotoFrontUserEntity;
import io.renren.entity.PhotoPicUserEntity;
import io.renren.entity.PhotoTypeEntity;
import io.renren.enums.PhotoTypeEnum;
import io.renren.model.json.ResponseDTJson;
import io.renren.service.PhotoPicUserService;
import io.renren.service.PhotoTypeService;
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
@RestController
@RequestMapping("front/photopicuser")
public class PhotoPicUserController {
	@Autowired
	private PhotoPicUserService photoPicUserService;
	
	@Autowired
	private PhotoTypeService photoTypeService;
	
	@ModelAttribute("frontUser")
	public PhotoFrontUserEntity getSessionUser(HttpServletRequest request)
	{
		PhotoFrontUserEntity user = (PhotoFrontUserEntity) request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
		
		if (user == null) 
			return null;
		
		return user;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@IgnoreAuth
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        
        Long typeId = params.containsKey("id") ? Long.parseLong(params.get("id").toString()) : null;
        
        if (typeId == null)
        	return R.error("参数错误").put("status", ResponseDTJson.FAIL);
        
        query.put("typeId", typeId);
		List<PhotoPicUserEntity> photoPicUserList = photoPicUserService.queryList(query);
		int total = photoPicUserService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoPicUserList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil).put("url", PostObjectPolicyController.CDN_URL);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photopicuser:info")
	public R info(@PathVariable("id") Long id){
		PhotoPicUserEntity photoPicUser = photoPicUserService.queryObject(id);
		
		return R.ok().put("photoPicUser", photoPicUser);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photopicuser:save")
	public R save(@RequestBody PhotoPicUserEntity photoPicUser){
		photoPicUserService.save(photoPicUser);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photopicuser:update")
	public R update(@RequestBody PhotoPicUserEntity photoPicUser){
		photoPicUserService.update(photoPicUser);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photopicuser:delete")
	public R delete(@RequestBody Long[] ids){
		photoPicUserService.deleteBatch(ids);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/deleteById")
	@CheckAuth(needAuth = "user:logined")
	public R deleteById(@RequestParam("ida") Long ida, @ModelAttribute("frontUser") PhotoFrontUserEntity frontUser){
		
		PhotoPicUserEntity ppue = this.photoPicUserService.queryObject(ida);
		
		if (ppue == null )
			return R.error("记录不存在").put("status", ResponseDTJson.FAIL);
		
		// 以下是防止非法操作，删除他人的记录
		PhotoTypeEntity pte = this.photoTypeService.queryObject(ppue.getTypeId());
		if (pte == null) 
			return R.error("类型记录不存在").put("status", ResponseDTJson.FAIL);
		if ( !pte.getType().equals(PhotoTypeEnum.PHOTO_TYPE_PERSONAL.getTypeValue()) || 
				!pte.getRelatedId().equals(frontUser.getId()) ) 
			return R.error("非法操作，三次非法操作冻结账户！").put("status", ResponseDTJson.FAIL);
			
		photoPicUserService.delete(ida);
			
		return R.ok();
	}
	
}
