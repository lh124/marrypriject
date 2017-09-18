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

import io.renren.entity.PhotoGraduationTimeEntity;
import io.renren.service.PhotoGraduationTimeService;
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
@RequestMapping("photograduationtime")
public class PhotoGraduationTimeController {
	@Autowired
	private PhotoGraduationTimeService photoGraduationTimeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photograduationtime:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoGraduationTimeEntity> photoGraduationTimeList = photoGraduationTimeService.queryList(query);
		int total = photoGraduationTimeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoGraduationTimeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photograduationtime:info")
	public R info(@PathVariable("id") Integer id){
		PhotoGraduationTimeEntity photoGraduationTime = photoGraduationTimeService.queryObject(id);
		
		return R.ok().put("photoGraduationTime", photoGraduationTime);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photograduationtime:save")
	public R save(@RequestBody PhotoGraduationTimeEntity photoGraduationTime){
		
		ValidatorUtils.validateEntity(photoGraduationTime, AddGroup.class);
		
		photoGraduationTime.setGmtCreate(new Date());
		photoGraduationTime.setGmtModified(new Date());
		photoGraduationTimeService.save(photoGraduationTime);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photograduationtime:update")
	public R update(@RequestBody PhotoGraduationTimeEntity photoGraduationTime){
		photoGraduationTimeService.update(photoGraduationTime);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photograduationtime:delete")
	public R delete(@RequestBody Integer[] ids){
		photoGraduationTimeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
