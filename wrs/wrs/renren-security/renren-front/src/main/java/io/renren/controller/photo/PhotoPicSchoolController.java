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

import io.renren.entity.PhotoPicSchoolEntity;
import io.renren.service.PhotoPicSchoolService;
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
@RequestMapping("photopicschool")
public class PhotoPicSchoolController {
	@Autowired
	private PhotoPicSchoolService photoPicSchoolService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photopicschool:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoPicSchoolEntity> photoPicSchoolList = photoPicSchoolService.queryList(query);
		int total = photoPicSchoolService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoPicSchoolList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photopicschool:info")
	public R info(@PathVariable("id") Long id){
		PhotoPicSchoolEntity photoPicSchool = photoPicSchoolService.queryObject(id);
		
		return R.ok().put("photoPicSchool", photoPicSchool);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photopicschool:save")
	public R save(@RequestBody PhotoPicSchoolEntity photoPicSchool){
		photoPicSchoolService.save(photoPicSchool);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photopicschool:update")
	public R update(@RequestBody PhotoPicSchoolEntity photoPicSchool){
		photoPicSchoolService.update(photoPicSchool);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photopicschool:delete")
	public R delete(@RequestBody Long[] ids){
		photoPicSchoolService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
