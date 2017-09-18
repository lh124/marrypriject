package io.renren.controller.smartWeb;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.annotation.CheckAuth;
import io.renren.entity.smart.PhotoExaminationEntity;
import io.renren.service.smart.PhotoExaminationService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.validator.ValidatorUtils;
import io.renren.validator.group.UniformGroup;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-27 16:53:07
 */
@RestController("sysPhotoExaminationController")
public class PhotoExaminationController {
	@Autowired
	private PhotoExaminationService photoExaminationService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/photoexamination/list")
	@RequiresPermissions("photoexamination:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        
        Long classId = params.containsKey("classId") ? Long.parseLong(params.get("classId").toString()) : null;
        
        if (classId != null) 
        	query.put("classId", classId);

		List<PhotoExaminationEntity> photoExaminationList = photoExaminationService.queryList(query);
		int total = photoExaminationService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoExaminationList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/photoexamination/info/{id}")
	@RequiresPermissions("photoexamination:info")
	public R info(@PathVariable("id") Long id){
		PhotoExaminationEntity photoExamination = photoExaminationService.queryObject(id);
		
		return R.ok().put("photoExamination", photoExamination);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/photoexamination/save")
	@RequiresPermissions("photoexamination:save")
	public R save(@RequestBody PhotoExaminationEntity photoExamination){
		ValidatorUtils.validateEntity(photoExamination, UniformGroup.class);
		
		photoExaminationService.save(photoExamination);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/photoexamination/update")
	@RequiresPermissions("photoexamination:update")
	public R update(@RequestBody PhotoExaminationEntity photoExamination){
		photoExaminationService.update(photoExamination);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/photoexamination/delete")
	@RequiresPermissions("photoexamination:delete")
	public R delete(@RequestBody Long[] ids){
		photoExaminationService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
