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

import io.renren.entity.PhotoUserClassEntity;
import io.renren.service.PhotoUserClassService;
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
@RequestMapping("photouserclass")
public class PhotoUserClassController {
	@Autowired
	private PhotoUserClassService photoUserClassService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photouserclass:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoUserClassEntity> photoUserClassList = photoUserClassService.queryList(query);
		int total = photoUserClassService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoUserClassList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photouserclass:info")
	public R info(@PathVariable("id") Long id){
		PhotoUserClassEntity photoUserClass = photoUserClassService.queryObject(id);
		
		return R.ok().put("photoUserClass", photoUserClass);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photouserclass:save")
	public R save(@RequestBody PhotoUserClassEntity photoUserClass){
		photoUserClassService.save(photoUserClass);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photouserclass:update")
	public R update(@RequestBody PhotoUserClassEntity photoUserClass){
		photoUserClassService.update(photoUserClass);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photouserclass:delete")
	public R delete(@RequestBody Long[] ids){
		photoUserClassService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
