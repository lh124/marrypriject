package io.renren.controller.photoWeb;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.entity.PhotoTypeEntity;
import io.renren.service.PhotoTypeService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.validator.ValidatorUtils;
import io.renren.validator.group.AddGroup;


/**
 * 相册分类表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
@RestController
@RequestMapping("phototype")
public class PhotoTypeController {
	@Autowired
	private PhotoTypeService photoTypeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("phototype:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        
        Integer type =  (params.containsKey("type") ? Integer.parseInt(params.get("type").toString()) : null);
        Long classId =  (params.containsKey("classId") ? Long.parseLong(params.get("classId").toString()) : null);
        
        if ( type != null && classId != null ) {
        	query.put("type", type);
        	query.put("classId", classId);
        }
        
		List<PhotoTypeEntity> photoTypeList = photoTypeService.queryList(query);
		int total = photoTypeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoTypeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("phototype:info")
	public R info(@PathVariable("id") Long id){
		PhotoTypeEntity photoType = photoTypeService.queryObject(id);
		
		return R.ok().put("photoType", photoType);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("phototype:save")
	public R save(@RequestBody PhotoTypeEntity photoType){
		
		ValidatorUtils.validateEntity(photoType, AddGroup.class);
		
		photoType.setGtmCreate(new Date());
		photoType.setGtmModified(new Date());
		photoTypeService.save(photoType);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("phototype:update")
	public R update(@RequestBody PhotoTypeEntity photoType){
		photoTypeService.update(photoType);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("phototype:delete")
	public R delete(@RequestBody Long[] ids){
		photoTypeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
