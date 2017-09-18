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

import io.renren.entity.PhotoPicFamilyEntity;
import io.renren.service.PhotoPicFamilyService;
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
@RequestMapping("photopicfamily")
public class PhotoPicFamilyController {
	@Autowired
	private PhotoPicFamilyService photoPicFamilyService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photopicfamily:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoPicFamilyEntity> photoPicFamilyList = photoPicFamilyService.queryList(query);
		int total = photoPicFamilyService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoPicFamilyList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photopicfamily:info")
	public R info(@PathVariable("id") Long id){
		PhotoPicFamilyEntity photoPicFamily = photoPicFamilyService.queryObject(id);
		
		return R.ok().put("photoPicFamily", photoPicFamily);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photopicfamily:save")
	public R save(@RequestBody PhotoPicFamilyEntity photoPicFamily){
		photoPicFamilyService.save(photoPicFamily);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photopicfamily:update")
	public R update(@RequestBody PhotoPicFamilyEntity photoPicFamily){
		photoPicFamilyService.update(photoPicFamily);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photopicfamily:delete")
	public R delete(@RequestBody Long[] ids){
		photoPicFamilyService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
