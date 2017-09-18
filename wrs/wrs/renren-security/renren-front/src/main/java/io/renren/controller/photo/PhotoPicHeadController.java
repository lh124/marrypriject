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

import io.renren.entity.PhotoPicHeadEntity;
import io.renren.service.PhotoPicHeadService;
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
@RequestMapping("photopichead")
public class PhotoPicHeadController {
	@Autowired
	private PhotoPicHeadService photoPicHeadService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photopichead:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoPicHeadEntity> photoPicHeadList = photoPicHeadService.queryList(query);
		int total = photoPicHeadService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoPicHeadList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photopichead:info")
	public R info(@PathVariable("id") Long id){
		PhotoPicHeadEntity photoPicHead = photoPicHeadService.queryObject(id);
		
		return R.ok().put("photoPicHead", photoPicHead);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photopichead:save")
	public R save(@RequestBody PhotoPicHeadEntity photoPicHead){
		photoPicHeadService.save(photoPicHead);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photopichead:update")
	public R update(@RequestBody PhotoPicHeadEntity photoPicHead){
		photoPicHeadService.update(photoPicHead);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photopichead:delete")
	public R delete(@RequestBody Long[] ids){
		photoPicHeadService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
