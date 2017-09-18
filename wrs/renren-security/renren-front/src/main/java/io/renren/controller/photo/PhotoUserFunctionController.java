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

import io.renren.entity.PhotoUserFunctionEntity;
import io.renren.service.PhotoUserFunctionService;
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
@RequestMapping("photouserfunction")
public class PhotoUserFunctionController {
	@Autowired
	private PhotoUserFunctionService photoUserFunctionService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photouserfunction:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoUserFunctionEntity> photoUserFunctionList = photoUserFunctionService.queryList(query);
		int total = photoUserFunctionService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoUserFunctionList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photouserfunction:info")
	public R info(@PathVariable("id") Long id){
		PhotoUserFunctionEntity photoUserFunction = photoUserFunctionService.queryObject(id);
		
		return R.ok().put("photoUserFunction", photoUserFunction);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photouserfunction:save")
	public R save(@RequestBody PhotoUserFunctionEntity photoUserFunction){
		photoUserFunctionService.save(photoUserFunction);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photouserfunction:update")
	public R update(@RequestBody PhotoUserFunctionEntity photoUserFunction){
		photoUserFunctionService.update(photoUserFunction);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photouserfunction:delete")
	public R delete(@RequestBody Long[] ids){
		photoUserFunctionService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
