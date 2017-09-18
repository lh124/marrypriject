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

import io.renren.entity.PhotoMemberRoleEntity;
import io.renren.service.PhotoMemberRoleService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 10:25:52
 */
@RestController
@RequestMapping("photomemberrole")
public class PhotoMemberRoleController {
	@Autowired
	private PhotoMemberRoleService photoMemberRoleService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photomemberrole:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoMemberRoleEntity> photoMemberRoleList = photoMemberRoleService.queryList(query);
		int total = photoMemberRoleService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoMemberRoleList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photomemberrole:info")
	public R info(@PathVariable("id") Integer id){
		PhotoMemberRoleEntity photoMemberRole = photoMemberRoleService.queryObject(id);
		
		return R.ok().put("photoMemberRole", photoMemberRole);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photomemberrole:save")
	public R save(@RequestBody PhotoMemberRoleEntity photoMemberRole){
		photoMemberRoleService.save(photoMemberRole);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photomemberrole:update")
	public R update(@RequestBody PhotoMemberRoleEntity photoMemberRole){
		photoMemberRoleService.update(photoMemberRole);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photomemberrole:delete")
	public R delete(@RequestBody Integer[] ids){
		photoMemberRoleService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
