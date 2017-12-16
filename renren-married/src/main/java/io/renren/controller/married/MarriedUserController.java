package io.renren.controller.married;

import io.renren.entity.married.MarriedUserEntity;
import io.renren.service.married.MarriedUserService;
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
 * @date 2017-12-01 13:50:42
 */
@RestController
@RequestMapping("marrieduser")
public class MarriedUserController {
	@Autowired
	private MarriedUserService marriedUserService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("marrieduser:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		params.put("openId", null);
        Query query = new Query(params);
		List<MarriedUserEntity> marriedUserList = marriedUserService.queryList(query);
		int total = marriedUserService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(marriedUserList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("marrieduser:info")
	public R info(@PathVariable("id") Integer id){
		MarriedUserEntity marriedUser = marriedUserService.queryObject(id);
		
		return R.ok().put("marriedUser", marriedUser);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("marrieduser:save")
	public R save(@RequestBody MarriedUserEntity marriedUser){
		marriedUserService.save(marriedUser);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("marrieduser:update")
	public R update(@RequestBody MarriedUserEntity marriedUser){
		marriedUserService.update(marriedUser);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("marrieduser:delete")
	public R delete(@RequestBody Integer[] ids){
		marriedUserService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
