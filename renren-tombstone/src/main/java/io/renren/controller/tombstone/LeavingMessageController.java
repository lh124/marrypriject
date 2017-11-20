package io.renren.controller.tombstone;

import io.renren.entity.tombstone.LeavingMessageEntity;
import io.renren.service.tombstone.LeavingMessageService;
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
 * @date 2017-11-04 15:11:17
 */
@RestController
@RequestMapping("leavingmessage")
public class LeavingMessageController {
	@Autowired
	private LeavingMessageService leavingMessageService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("leavingmessage:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<LeavingMessageEntity> leavingMessageList = leavingMessageService.queryList(query);
		int total = leavingMessageService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(leavingMessageList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("leavingmessage:info")
	public R info(@PathVariable("id") Integer id){
		LeavingMessageEntity leavingMessage = leavingMessageService.queryObject(id);
		
		return R.ok().put("leavingMessage", leavingMessage);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("leavingmessage:save")
	public R save(@RequestBody LeavingMessageEntity leavingMessage){
		leavingMessageService.save(leavingMessage);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("leavingmessage:update")
	public R update(@RequestBody LeavingMessageEntity leavingMessage){
		leavingMessageService.update(leavingMessage);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("leavingmessage:delete")
	public R delete(@RequestBody Integer[] ids){
		leavingMessageService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
