package io.renren.controller.married;

import io.renren.entity.married.MarryPhotoEntity;
import io.renren.service.married.MarryPhotoService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-02 10:37:17
 */
@RestController
@RequestMapping("marryphoto")
public class MarryPhotoController {
	@Autowired
	private MarryPhotoService marryPhotoService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("marryphoto:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MarryPhotoEntity> marryPhotoList = marryPhotoService.queryList(query);
		int total = marryPhotoService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(marryPhotoList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("marryphoto:info")
	public R info(@PathVariable("id") Integer id){
		MarryPhotoEntity marryPhoto = marryPhotoService.queryObject(id);
		
		return R.ok().put("marryPhoto", marryPhoto);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("marryphoto:save")
	public R save(@RequestBody MarryPhotoEntity marryPhoto){
		marryPhotoService.save(marryPhoto);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("marryphoto:update")
	public R update(@RequestBody MarryPhotoEntity marryPhoto){
		marryPhotoService.update(marryPhoto);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("marryphoto:delete")
	public R delete(@RequestBody Integer[] ids){
		marryPhotoService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
