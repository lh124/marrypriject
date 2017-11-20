package io.renren.controller.tombstone;

import io.renren.entity.tombstone.BombstonePicEntity;
import io.renren.service.tombstone.BombstonePicService;
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
 * @date 2017-10-21 17:05:56
 */
@RestController
@RequestMapping("bombstonepic")
public class BombstonePicController {
	@Autowired
	private BombstonePicService bombstonePicService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("bombstonepic:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<BombstonePicEntity> bombstonePicList = bombstonePicService.queryList(query);
		int total = bombstonePicService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(bombstonePicList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("bombstonepic:info")
	public R info(@PathVariable("id") Integer id){
		BombstonePicEntity bombstonePic = bombstonePicService.queryObject(id);
		
		return R.ok().put("bombstonePic", bombstonePic);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("bombstonepic:save")
	public R save(@RequestBody BombstonePicEntity bombstonePic){
		bombstonePicService.save(bombstonePic);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("bombstonepic:update")
	public R update(@RequestBody BombstonePicEntity bombstonePic){
		bombstonePicService.update(bombstonePic);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("bombstonepic:delete")
	public R delete(@RequestBody Integer[] ids){
		bombstonePicService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
