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

import io.renren.entity.PhotoClassWallEntity;
import io.renren.service.PhotoClassWallService;
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
@RequestMapping("photoclasswall")
public class PhotoClassWallController {
	@Autowired
	private PhotoClassWallService photoClassWallService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photoclasswall:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoClassWallEntity> photoClassWallList = photoClassWallService.queryList(query);
		int total = photoClassWallService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoClassWallList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photoclasswall:info")
	public R info(@PathVariable("id") Long id){
		PhotoClassWallEntity photoClassWall = photoClassWallService.queryObject(id);
		
		return R.ok().put("photoClassWall", photoClassWall);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photoclasswall:save")
	public R save(@RequestBody PhotoClassWallEntity photoClassWall){
		photoClassWallService.save(photoClassWall);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photoclasswall:update")
	public R update(@RequestBody PhotoClassWallEntity photoClassWall){
		photoClassWallService.update(photoClassWall);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photoclasswall:delete")
	public R delete(@RequestBody Long[] ids){
		photoClassWallService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
