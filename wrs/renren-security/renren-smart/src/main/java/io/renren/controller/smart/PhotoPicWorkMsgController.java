package io.renren.controller.smart;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.constant.ControllerConstant;
import io.renren.entity.smart.PhotoPicWorkMsgEntity;
import io.renren.service.smart.PhotoPicWorkMsgService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-18 13:12:52
 */
@RestController
@RequestMapping("/smart/photopicworkmsg")
public class PhotoPicWorkMsgController {
	@Autowired
	private PhotoPicWorkMsgService photoPicWorkMsgService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photopicworkmsg:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoPicWorkMsgEntity> photoPicWorkMsgList = photoPicWorkMsgService.queryList(query);
		int total = photoPicWorkMsgService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoPicWorkMsgList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil).put("url", ControllerConstant.CDN_URL);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photopicworkmsg:info")
	public R info(@PathVariable("id") Long id){
		PhotoPicWorkMsgEntity photoPicWorkMsg = photoPicWorkMsgService.queryObject(id);
		
		return R.ok().put("photoPicWorkMsg", photoPicWorkMsg);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photopicworkmsg:save")
	public R save(@RequestBody PhotoPicWorkMsgEntity photoPicWorkMsg){
		photoPicWorkMsgService.save(photoPicWorkMsg);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photopicworkmsg:update")
	public R update(@RequestBody PhotoPicWorkMsgEntity photoPicWorkMsg){
		photoPicWorkMsgService.update(photoPicWorkMsg);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photopicworkmsg:delete")
	public R delete(@RequestBody Long[] ids){
		photoPicWorkMsgService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
