package io.renren.controller.smart;

import io.renren.entity.smart.SmartTeacherMessageEntity;
import io.renren.service.smart.SmartTeacherMessageService;
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
 * @date 2018-01-30 09:33:09
 */
@RestController
@RequestMapping("smartteachermessage")
public class SmartTeacherMessageController {
	@Autowired
	private SmartTeacherMessageService smartTeacherMessageService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("smartteachermessage:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SmartTeacherMessageEntity> smartTeacherMessageList = smartTeacherMessageService.queryList(query);
		int total = smartTeacherMessageService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(smartTeacherMessageList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("smartteachermessage:info")
	public R info(@PathVariable("id") Integer id){
		SmartTeacherMessageEntity smartTeacherMessage = smartTeacherMessageService.queryObject(id);
		
		return R.ok().put("smartTeacherMessage", smartTeacherMessage);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("smartteachermessage:save")
	public R save(@RequestBody SmartTeacherMessageEntity smartTeacherMessage){
		smartTeacherMessageService.save(smartTeacherMessage);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("smartteachermessage:update")
	public R update(@RequestBody SmartTeacherMessageEntity smartTeacherMessage){
		smartTeacherMessageService.update(smartTeacherMessage);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("smartteachermessage:delete")
	public R delete(@RequestBody Integer[] ids){
		smartTeacherMessageService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
