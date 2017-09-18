package io.renren.controller.photo;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.entity.PhotoFamilyUserEntity;
import io.renren.service.PhotoFamilyUserService;
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
@RequestMapping("photofamilyuser")
public class PhotoFamilyUserController {
	@Autowired
	private PhotoFamilyUserService photoFamilyUserService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photofamilyuser:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoFamilyUserEntity> photoFamilyUserList = photoFamilyUserService.queryList(query);
		int total = photoFamilyUserService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoFamilyUserList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photofamilyuser:info")
	public R info(@PathVariable("id") Long id){
		PhotoFamilyUserEntity photoFamilyUser = photoFamilyUserService.queryObject(id);
		
		return R.ok().put("photoFamilyUser", photoFamilyUser);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photofamilyuser:save")
	public R save(@RequestBody PhotoFamilyUserEntity photoFamilyUser){
		photoFamilyUserService.save(photoFamilyUser);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photofamilyuser:update")
	public R update(@RequestBody PhotoFamilyUserEntity photoFamilyUser){
		photoFamilyUserService.update(photoFamilyUser);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photofamilyuser:delete")
	public R delete(@RequestBody Long[] ids){
		photoFamilyUserService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
