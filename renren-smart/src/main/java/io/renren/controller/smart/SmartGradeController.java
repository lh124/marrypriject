package io.renren.controller.smart;

import io.renren.entity.smart.SmartGradeEntity;
import io.renren.service.smart.SmartGradeService;
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
 * @date 2018-01-27 10:26:32
 */
@RestController
@RequestMapping("smartgrade")
public class SmartGradeController {
	@Autowired
	private SmartGradeService smartGradeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("smartgrade:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SmartGradeEntity> smartGradeList = smartGradeService.queryList(query);
		int total = smartGradeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(smartGradeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("smartgrade:info")
	public R info(@PathVariable("id") Integer id){
		SmartGradeEntity smartGrade = smartGradeService.queryObject(id);
		
		return R.ok().put("smartGrade", smartGrade);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("smartgrade:save")
	public R save(@RequestBody SmartGradeEntity smartGrade){
		smartGradeService.save(smartGrade);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("smartgrade:update")
	public R update(@RequestBody SmartGradeEntity smartGrade){
		smartGradeService.update(smartGrade);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("smartgrade:delete")
	public R delete(@RequestBody Integer[] ids){
		smartGradeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
