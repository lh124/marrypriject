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

import io.renren.entity.PhotoPicPublicEntity;
import io.renren.service.PhotoPicPublicService;
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
@RequestMapping("photopicpublic")
public class PhotoPicPublicController {
	@Autowired
	private PhotoPicPublicService photoPicPublicService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photopicpublic:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoPicPublicEntity> photoPicPublicList = photoPicPublicService.queryList(query);
		int total = photoPicPublicService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoPicPublicList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photopicpublic:info")
	public R info(@PathVariable("id") Long id){
		PhotoPicPublicEntity photoPicPublic = photoPicPublicService.queryObject(id);
		
		return R.ok().put("photoPicPublic", photoPicPublic);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photopicpublic:save")
	public R save(@RequestBody PhotoPicPublicEntity photoPicPublic){
		photoPicPublicService.save(photoPicPublic);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photopicpublic:update")
	public R update(@RequestBody PhotoPicPublicEntity photoPicPublic){
		photoPicPublicService.update(photoPicPublic);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photopicpublic:delete")
	public R delete(@RequestBody Long[] ids){
		photoPicPublicService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
