package io.renren.controller.smart;

import io.renren.entity.smart.GroupChatEntity;
import io.renren.service.smart.GroupChatService;
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
 * @date 2017-11-25 10:00:42
 */
@RestController
@RequestMapping("groupchat")
public class GroupChatController {
	@Autowired
	private GroupChatService groupChatService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("groupchat:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<GroupChatEntity> groupChatList = groupChatService.queryList(query);
		int total = groupChatService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(groupChatList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("groupchat:info")
	public R info(@PathVariable("id") Integer id){
		GroupChatEntity groupChat = groupChatService.queryObject(id);
		
		return R.ok().put("groupChat", groupChat);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("groupchat:save")
	public R save(@RequestBody GroupChatEntity groupChat){
		groupChatService.save(groupChat);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("groupchat:update")
	public R update(@RequestBody GroupChatEntity groupChat){
		groupChatService.update(groupChat);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("groupchat:delete")
	public R delete(@RequestBody Integer[] ids){
		groupChatService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
