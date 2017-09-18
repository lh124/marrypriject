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

import io.renren.entity.PhotoFamilyEntity;
import io.renren.service.PhotoFamilyService;
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
@RequestMapping("photofamily")
public class PhotoFamilyController {
	@Autowired
	private PhotoFamilyService photoFamilyService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photofamily:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoFamilyEntity> photoFamilyList = photoFamilyService.queryList(query);
		int total = photoFamilyService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoFamilyList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photofamily:info")
	public R info(@PathVariable("id") Long id){
		PhotoFamilyEntity photoFamily = photoFamilyService.queryObject(id);
		
		return R.ok().put("photoFamily", photoFamily);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photofamily:save")
	public R save(@RequestBody PhotoFamilyEntity photoFamily){
		photoFamilyService.save(photoFamily);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photofamily:update")
	public R update(@RequestBody PhotoFamilyEntity photoFamily){
		photoFamilyService.update(photoFamily);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photofamily:delete")
	public R delete(@RequestBody Long[] ids){
		photoFamilyService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
