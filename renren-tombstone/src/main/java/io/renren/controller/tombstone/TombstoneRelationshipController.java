package io.renren.controller.tombstone;

import io.renren.entity.tombstone.TombstoneRelationshipEntity;
import io.renren.service.tombstone.TombstoneRelationshipService;
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
 * @date 2017-10-23 13:06:48
 */
@RestController
@RequestMapping("tombstonerelationship")
public class TombstoneRelationshipController {
	@Autowired
	private TombstoneRelationshipService tombstoneRelationshipService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("tombstonerelationship:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<TombstoneRelationshipEntity> tombstoneRelationshipList = tombstoneRelationshipService.queryList(query);
		int total = tombstoneRelationshipService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(tombstoneRelationshipList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tombstonerelationship:info")
	public R info(@PathVariable("id") Integer id){
		TombstoneRelationshipEntity tombstoneRelationship = tombstoneRelationshipService.queryObject(id);
		
		return R.ok().put("tombstoneRelationship", tombstoneRelationship);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("tombstonerelationship:save")
	public R save(@RequestBody TombstoneRelationshipEntity tombstoneRelationship){
		tombstoneRelationshipService.save(tombstoneRelationship);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("tombstonerelationship:update")
	public R update(@RequestBody TombstoneRelationshipEntity tombstoneRelationship){
		tombstoneRelationshipService.update(tombstoneRelationship);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("tombstonerelationship:delete")
	public R delete(@RequestBody Integer[] ids){
		tombstoneRelationshipService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
