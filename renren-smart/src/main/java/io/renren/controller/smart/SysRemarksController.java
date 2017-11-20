package io.renren.controller.smart;

import io.renren.entity.SysUserEntity;
import io.renren.entity.smart.SysRemarksEntity;
import io.renren.service.smart.SysRemarksService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.ShiroUtil;

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
 * @date 2017-11-20 12:02:46
 */
@RestController
@RequestMapping("sysremarks")
public class SysRemarksController {
	@Autowired
	private SysRemarksService sysRemarksService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sysremarks:list")
	public R list(@RequestParam Map<String, Object> params){
		SysUserEntity user = (SysUserEntity) ShiroUtil.getUserEntity();
		params.put("userId", user.getUserId());
		//查询列表数据
        Query query = new Query(params);
		List<SysRemarksEntity> sysRemarksList = sysRemarksService.queryList(query);
		int total = sysRemarksService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(sysRemarksList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sysremarks:info")
	public R info(@PathVariable("id") Integer id){
		SysRemarksEntity sysRemarks = sysRemarksService.queryObject(id);
		return R.ok().put("sysRemarks", sysRemarks);
	}
	
	/**
	 * 保存
	 */
	/**
	 * @param sysRemarks
	 * @return
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sysremarks:save")
	public R save(@RequestBody SysRemarksEntity sysRemarks){
		SysUserEntity user = (SysUserEntity) ShiroUtil.getUserEntity();
		sysRemarks.setSolveIf(0);
		sysRemarks.setUserId(Integer.parseInt(user.getUserId().toString()));
		sysRemarks.setCreatetime(new Date());
		sysRemarksService.save(sysRemarks);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sysremarks:update")
	public R update(@RequestBody SysRemarksEntity sysRemarks){
		sysRemarksService.update(sysRemarks);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sysremarks:delete")
	public R delete(@RequestBody Integer[] ids){
		sysRemarksService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
