package io.renren.controller.married;

import io.renren.entity.married.MarryParticipateEntity;
import io.renren.service.married.MarryParticipateService;
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
 * @date 2017-12-18 10:07:37
 */
@RestController
@RequestMapping("marryparticipate")
public class MarryParticipateController {
	@Autowired
	private MarryParticipateService marryParticipateService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("marryparticipate:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<MarryParticipateEntity> marryParticipateList = marryParticipateService.queryList(query);
		int total = marryParticipateService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(marryParticipateList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("marryparticipate:info")
	public R info(@PathVariable("id") Integer id){
		MarryParticipateEntity marryParticipate = marryParticipateService.queryObject(id);
		
		return R.ok().put("marryParticipate", marryParticipate);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("marryparticipate:save")
	public R save(@RequestBody MarryParticipateEntity marryParticipate){
		marryParticipateService.save(marryParticipate);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("marryparticipate:update")
	public R update(@RequestBody MarryParticipateEntity marryParticipate){
		marryParticipateService.update(marryParticipate);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("marryparticipate:delete")
	public R delete(@RequestBody Integer[] ids){
		marryParticipateService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
