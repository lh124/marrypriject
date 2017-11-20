package io.renren.controller.tombstone;

import io.renren.entity.tombstone.TombstoneUserEntity;
import io.renren.service.tombstone.TombstoneUserService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.validator.ValidatorUtils;
import io.renren.validator.group.AddGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
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
 * @date 2017-10-19 16:59:26
 */
@RestController
@RequestMapping("tombstoneuser")
public class TombstoneUserController {
	@Autowired
	private TombstoneUserService tombstoneUserService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("tombstoneuser:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<TombstoneUserEntity> tombstoneUserList = tombstoneUserService.queryList(query);
		int total = tombstoneUserService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(tombstoneUserList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tombstoneuser:info")
	public R info(@PathVariable("id") Integer id){
		TombstoneUserEntity tombstoneUser = tombstoneUserService.queryObject(id);
		
		return R.ok().put("tombstoneUser", tombstoneUser);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("tombstoneuser:save")
	public R save(@RequestBody TombstoneUserEntity tombstoneUser){
		TombstoneUserEntity tbu = tombstoneUserService.queryObjectName(tombstoneUser.getName());
		if(tbu != null){
			return R.error("账号："+tombstoneUser.getName() + "已存在,请重新输入登录账号");
		}else{
			ValidatorUtils.validateEntity(tombstoneUser, AddGroup.class);
			if(tombstoneUser.getPassword() == null || tombstoneUser.getPassword().equals("")){
				tombstoneUser.setPassword(new Sha256Hash("000000").toHex());
			}else{
				tombstoneUser.setPassword(new Sha256Hash(tombstoneUser.getPassword()).toHex());
			}
			tombstoneUser.setCreatetime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			tombstoneUserService.insert(tombstoneUser);
			return R.ok().put("id", tombstoneUser.getId());
		}
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("tombstoneuser:update")
	public R update(@RequestBody TombstoneUserEntity tombstoneUser){
		if(tombstoneUser.getPassword().length() < 30 ){
			tombstoneUser.setPassword(new Sha256Hash(tombstoneUser.getPassword()).toHex());
		}
		tombstoneUserService.update(tombstoneUser);
		return R.ok().put("id", tombstoneUser.getId());
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("tombstoneuser:delete")
	public R delete(@RequestBody Integer[] ids){
		tombstoneUserService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
