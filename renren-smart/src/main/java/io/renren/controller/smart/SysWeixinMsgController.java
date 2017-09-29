package io.renren.controller.smart;

import io.renren.entity.smart.SysWeixinEntity;
import io.renren.entity.smart.SysWeixinMsgEntity;
import io.renren.service.smart.SysWeixinMsgService;
import io.renren.service.smart.SysWeixinService;
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
 * @date 2017-09-28 14:47:08
 */
@RestController
@RequestMapping("sysweixinmsg")
public class SysWeixinMsgController {
	@Autowired
	private SysWeixinMsgService sysWeixinMsgService;
	@Autowired
	private SysWeixinService sysWeixinService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sysweixinmsg:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Integer id = Integer.parseInt(params.get("weixinid").toString());
		SysWeixinEntity sysWeixin = sysWeixinService.selectById(id);
		params.put("weixinid", "where weixinid = '" +sysWeixin.getOrigiid() + "'");
        Query query = new Query(params);
		List<SysWeixinMsgEntity> sysWeixinMsgList = sysWeixinMsgService.queryList(query);
		int total = sysWeixinMsgService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(sysWeixinMsgList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sysweixinmsg:info")
	public R info(@PathVariable("id") Integer id){
		SysWeixinMsgEntity sysWeixinMsg = sysWeixinMsgService.queryObject(id);
		return R.ok().put("sysWeixinMsg", sysWeixinMsg);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sysweixinmsg:save")
	public R save(@RequestBody SysWeixinMsgEntity sysWeixinMsg){
		Integer id = Integer.parseInt(sysWeixinMsg.getWeixinid());
		SysWeixinEntity sysWeixin = sysWeixinService.selectById(id);
		sysWeixinMsg.setWeixinid(sysWeixin.getOrigiid());
		sysWeixinMsgService.save(sysWeixinMsg);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sysweixinmsg:update")
	public R update(@RequestBody SysWeixinMsgEntity sysWeixinMsg){
		sysWeixinMsgService.update(sysWeixinMsg);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sysweixinmsg:delete")
	public R delete(@RequestBody Integer[] ids){
		sysWeixinMsgService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
