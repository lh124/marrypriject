package io.renren.controller.photo;

import java.util.Date;
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

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import io.renren.annotation.CheckAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.PhotoClassmatesEntity;
import io.renren.entity.PhotoFrontUserEntity;
import io.renren.model.json.ResponseDTJson;
import io.renren.service.PhotoClassmatesService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.validator.ValidatorUtils;
import io.renren.validator.group.AddGroup;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
@RestController
@RequestMapping("front/photoclassmates")
public class PhotoClassmatesController {
	@Autowired
	private PhotoClassmatesService photoClassmatesService;
	
	//注入user
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
	@CheckAuth(needAuth="graduate:all")
	public R list(@RequestParam Map<String, Object> params, @ModelAttribute("frontUser") PhotoFrontUserEntity user){
		//查询列表数据
        Query query = new Query(params);
        query.put("userId", user.getId());

		List<PhotoClassmatesEntity> photoClassmatesList = photoClassmatesService.queryList(query);
		int total = photoClassmatesService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoClassmatesList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil).put("url", PostObjectPolicyController.CDN_URL);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@CheckAuth(needAuth="graduate:all")
	public R info(@PathVariable("id") Long id){
		PhotoClassmatesEntity photoClassmates = photoClassmatesService.queryObject(id);
		
		return R.ok().put("photoClassmates", photoClassmates);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@CheckAuth(needAuth="graduate:all")
	public R save( PhotoClassmatesEntity photoClassmates, @ModelAttribute("frontUser") PhotoFrontUserEntity user){
		ValidatorUtils.validateEntity(photoClassmates, AddGroup.class);
		// 验证是否已经发布过同学录了
		PhotoClassmatesEntity pce = new PhotoClassmatesEntity();
		pce.setClassId(photoClassmates.getClassId());
		pce.setUserGetId(photoClassmates.getUserGetId());
		pce.setUserId(user.getId());
		
		EntityWrapper<PhotoClassmatesEntity> wrapper = new EntityWrapper<PhotoClassmatesEntity>(pce);
		pce = this.photoClassmatesService.selectOne(wrapper);
		
		if (pce != null)
			return R.error("已经发布过同学录").put("status", ResponseDTJson.FAIL);
		
		photoClassmates.setGmtCreate(new Date());
		photoClassmates.setGmtModified(new Date());
		photoClassmates.setUserId(user.getId());
		
		photoClassmatesService.save(photoClassmates);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photoclassmates:update")
	public R update(PhotoClassmatesEntity photoClassmates){
		ValidatorUtils.validateEntity(photoClassmates, AddGroup.class);
		photoClassmatesService.update(photoClassmates);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photoclassmates:delete")
	public R delete(@RequestBody Long[] ids){
		photoClassmatesService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
