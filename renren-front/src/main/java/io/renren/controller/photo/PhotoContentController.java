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

import io.renren.entity.PhotoContentEntity;
import io.renren.service.PhotoContentService;
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
@RequestMapping("photocontent")
public class PhotoContentController {
	@Autowired
	private PhotoContentService photoContentService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photocontent:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoContentEntity> photoContentList = photoContentService.queryList(query);
		int total = photoContentService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoContentList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photocontent:info")
	public R info(@PathVariable("id") Long id){
		PhotoContentEntity photoContent = photoContentService.queryObject(id);
		
		return R.ok().put("photoContent", photoContent);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photocontent:save")
	public R save(@RequestBody PhotoContentEntity photoContent){
		photoContentService.save(photoContent);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photocontent:update")
	public R update(@RequestBody PhotoContentEntity photoContent){
		photoContentService.update(photoContent);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photocontent:delete")
	public R delete(@RequestBody Long[] ids){
		photoContentService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
