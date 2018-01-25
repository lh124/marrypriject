package io.renren.controller.smart;

import io.renren.entity.smart.SmartProposalEntity;
import io.renren.service.smart.SmartProposalService;
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
 * @date 2018-01-25 15:47:28
 */
@RestController
@RequestMapping("smartproposal")
public class SmartProposalController {
	@Autowired
	private SmartProposalService smartProposalService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("smartproposal:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SmartProposalEntity> smartProposalList = smartProposalService.queryList(query);
		int total = smartProposalService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(smartProposalList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("smartproposal:info")
	public R info(@PathVariable("id") Integer id){
		SmartProposalEntity smartProposal = smartProposalService.queryObject(id);
		
		return R.ok().put("smartProposal", smartProposal);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("smartproposal:save")
	public R save(@RequestBody SmartProposalEntity smartProposal){
		smartProposalService.save(smartProposal);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("smartproposal:update")
	public R update(@RequestBody SmartProposalEntity smartProposal){
		smartProposalService.update(smartProposal);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("smartproposal:delete")
	public R delete(@RequestBody Integer[] ids){
		smartProposalService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
