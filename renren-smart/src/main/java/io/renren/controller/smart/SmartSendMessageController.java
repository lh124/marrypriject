package io.renren.controller.smart;

import io.renren.entity.smart.SmartSendMessageEntity;
import io.renren.service.smart.SmartSendMessageService;
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
 * @date 2018-02-01 09:17:27
 */
@RestController
@RequestMapping("smartsendmessage")
public class SmartSendMessageController { 
	@Autowired
	private SmartSendMessageService smartSendMessageService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("smartsendmessage:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SmartSendMessageEntity> smartSendMessageList = smartSendMessageService.queryList(query);
		int total = smartSendMessageService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(smartSendMessageList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("smartsendmessage:info")
	public R info(@PathVariable("id") Integer id){
		SmartSendMessageEntity smartSendMessage = smartSendMessageService.queryObject(id);
		
		return R.ok().put("smartSendMessage", smartSendMessage);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("smartsendmessage:save")
	public R save(@RequestBody SmartSendMessageEntity smartSendMessage){
		smartSendMessageService.save(smartSendMessage);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("smartsendmessage:update")
	public R update(@RequestBody SmartSendMessageEntity smartSendMessage){
		smartSendMessageService.update(smartSendMessage);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("smartsendmessage:delete")
	public R delete(@RequestBody Integer[] ids){
		smartSendMessageService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
