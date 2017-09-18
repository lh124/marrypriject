package io.renren.controller.photo;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.entity.PhotoMessageEntity;
import io.renren.service.PhotoMessageService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
@RestController
@RequestMapping("photomessage")
public class PhotoMessageController {
	@Autowired
	private PhotoMessageService photoMessageService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photomessage:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoMessageEntity> photoMessageList = photoMessageService.queryList(query);
		int total = photoMessageService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoMessageList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photomessage:info")
	public R info(@PathVariable("id") Long id){
		PhotoMessageEntity photoMessage = photoMessageService.queryObject(id);
		
		return R.ok().put("photoMessage", photoMessage);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photomessage:save")
	public R save(@RequestBody PhotoMessageEntity photoMessage){
		photoMessageService.save(photoMessage);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photomessage:update")
	public R update(@RequestBody PhotoMessageEntity photoMessage){
		photoMessageService.update(photoMessage);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photomessage:delete")
	public R delete(@RequestBody Long[] ids){
		photoMessageService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
