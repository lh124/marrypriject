package io.renren.controller.smart;

import io.renren.entity.smart.PsychologicalCounselingEntity;
import io.renren.service.smart.PsychologicalCounselingService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;

import java.util.Date;
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
 * @date 2017-09-21 09:34:56
 */
@RestController
@RequestMapping("psychologicalcounseling")
public class PsychologicalCounselingController {
	@Autowired
	private PsychologicalCounselingService psychologicalCounselingService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("psychologicalcounseling:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PsychologicalCounselingEntity> psychologicalCounselingList = psychologicalCounselingService.queryList(query);
		int total = psychologicalCounselingService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(psychologicalCounselingList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("psychologicalcounseling:info")
	public R info(@PathVariable("id") Integer id){
		PsychologicalCounselingEntity psychologicalCounseling = psychologicalCounselingService.queryObject(id);
		
		return R.ok().put("psychologicalCounseling", psychologicalCounseling);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("psychologicalcounseling:save")
	public R save(@RequestBody PsychologicalCounselingEntity psychologicalCounseling){
		psychologicalCounseling.setCreateTime(new Date());
		psychologicalCounselingService.save(psychologicalCounseling);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("psychologicalcounseling:update")
	public R update(@RequestBody PsychologicalCounselingEntity psychologicalCounseling){
		psychologicalCounselingService.update(psychologicalCounseling);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("psychologicalcounseling:delete")
	public R delete(@RequestBody Integer[] ids){
		psychologicalCounselingService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
