package io.renren.controller.photoWeb;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.controller.photo.PostObjectPolicyController;
import io.renren.entity.PhotoPicClassEntity;
import io.renren.service.PhotoPicClassService;
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
@RequestMapping("photopicclass")
public class PhotoPicClassController {
	@Autowired
	private PhotoPicClassService photoPicClassService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photopicclass:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        Long classTypeId = params.containsKey("classTypeId") ? Long.parseLong(params.get("classTypeId").toString()) : null;
        
        
        if (classTypeId != null) {
        	query.put("classTypeId", classTypeId);
        }
        
		List<PhotoPicClassEntity> photoPicClassList = photoPicClassService.queryList(query);
		int total = photoPicClassService.queryTotal(query);
		
		for (PhotoPicClassEntity ppe : photoPicClassList) {
			ppe.setPicPath(PostObjectPolicyController.CDN_URL + "/");
			ppe.setName(ppe.getName());
		}
		
		PageUtils pageUtil = new PageUtils(photoPicClassList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
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
	@RequiresPermissions("photopicclass:delete")
	public R delete(@RequestBody Long[] ids){
		photoPicClassService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
