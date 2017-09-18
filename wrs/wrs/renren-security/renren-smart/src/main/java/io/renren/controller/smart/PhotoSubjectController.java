package io.renren.controller.smart;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.entity.smart.PhotoSubjectEntity;
import io.renren.service.smart.PhotoSubjectService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;


/**
 * 科目表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-27 16:53:07
 */
@RestController
@RequestMapping("/photosubject")
public class PhotoSubjectController {
	@Autowired
	private PhotoSubjectService photoSubjectService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photosubject:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoSubjectEntity> photoSubjectList = photoSubjectService.queryList(query);
		int total = photoSubjectService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoSubjectList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photosubject:info")
	public R info(@PathVariable("id") Long id){
		PhotoSubjectEntity photoSubject = photoSubjectService.queryObject(id);
		
		return R.ok().put("photoSubject", photoSubject);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photosubject:save")
	public R save(@RequestBody PhotoSubjectEntity photoSubject){
		photoSubjectService.save(photoSubject);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photosubject:update")
	public R update(@RequestBody PhotoSubjectEntity photoSubject){
		photoSubjectService.update(photoSubject);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photosubject:delete")
	public R delete(@RequestBody Long[] ids){
		photoSubjectService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
