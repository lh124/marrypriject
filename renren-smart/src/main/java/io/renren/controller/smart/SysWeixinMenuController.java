package io.renren.controller.smart;

import io.renren.entity.smart.SysWeixinEntity;
import io.renren.entity.smart.SysWeixinMenuEntity;
import io.renren.service.smart.SysWeixinMenuService;
import io.renren.service.smart.SysWeixinService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.weixin.main.Button;
import io.renren.weixin.main.ComplexButton;
import io.renren.weixin.main.Menu;
import io.renren.weixin.main.ViewButton;
import io.renren.weixin.pojo.Token;
import io.renren.weixin.util.CommonUtil;
import io.renren.weixin.util.MenuUtil;

import java.util.HashMap;
import java.util.Iterator;
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
 * @date 2017-09-27 10:06:01
 */
@RestController
@RequestMapping("sysweixinmenu")
public class SysWeixinMenuController {
	@Autowired
	private SysWeixinMenuService sysWeixinMenuService;
	@Autowired
	private SysWeixinService sysWeixinService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sysweixinmenu:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		if(params.get("type").equals("1")){
			params.put("weixinid", "where weixinid=" + params.get("weixinid"));
		}else {
			params.put("weixinid", "where fatherid=" + params.get("fatherid"));
		}
        Query query = new Query(params);
		List<SysWeixinMenuEntity> sysWeixinMenuList = sysWeixinMenuService.queryList(query);
		int total = sysWeixinMenuService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(sysWeixinMenuList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sysweixinmenu:info")
	public R info(@PathVariable("id") Integer id){
		SysWeixinMenuEntity sysWeixinMenu = sysWeixinMenuService.queryObject(id);
		return R.ok().put("sysWeixinMenu", sysWeixinMenu);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sysweixinmenu:save")
	public R save(@RequestBody SysWeixinMenuEntity sysWeixinMenu){
		sysWeixinMenuService.save(sysWeixinMenu);
		if(sysWeixinMenu.getMenutype() == 2){
			SysWeixinMenuEntity father = sysWeixinMenuService.selectById(sysWeixinMenu.getFatherid());
			savemenu(father.getWeixinid());
		}
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sysweixinmenu:update")
	public R update(@RequestBody SysWeixinMenuEntity sysWeixinMenu){
		sysWeixinMenuService.update(sysWeixinMenu);
		if(sysWeixinMenu.getMenutype() == 2){
			SysWeixinMenuEntity father = sysWeixinMenuService.selectById(sysWeixinMenu.getFatherid());
			savemenu(father.getWeixinid());
		}
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sysweixinmenu:delete")
	public R delete(@RequestBody Integer[] ids){
		sysWeixinMenuService.deleteBatch(ids);
		return R.ok();
	}
	
	private void savemenu(int id){
		SysWeixinEntity sysWeixin = sysWeixinService.selectById(id);
		Token token = CommonUtil.getToken(sysWeixin.getAppid(), sysWeixin.getAppsecret());
		if (null != token) {
			// 创建菜单
			boolean result = MenuUtil.createMenu(getMenu(id), token.getAccessToken());
			// 判断菜单创建结果
			if (result)
				System.out.println("菜单创建成功！");
			else
			    System.out.println("菜单创建失败！");
		}
	}
	
	@SuppressWarnings("rawtypes")
	private Menu getMenu(int id){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("page", "1");
		params.put("limit", "100");
		params.put("sidx", "");
		params.put("order", "");
		params.put("weixinid", "where weixinid=" + id);
		Query query = new Query(params);
		Menu menu = new Menu();
		List<SysWeixinMenuEntity> father = sysWeixinMenuService.queryList(query);
		Button[] buttonfather = new Button[father.size()];
		int i = 0;
		for (Iterator iterator = father.iterator(); iterator.hasNext();) {
			SysWeixinMenuEntity sysWeixinMenu = (SysWeixinMenuEntity) iterator.next();
			ComplexButton mainBtn = new ComplexButton();
			mainBtn.setName(sysWeixinMenu.getName());
			params.put("weixinid", "where fatherid=" + sysWeixinMenu.getId());
			List<SysWeixinMenuEntity> son = sysWeixinMenuService.queryList(new Query(params));
			Button[] button = new Button[son.size()];
			int j = 0;
			for (Iterator iterator2 = son.iterator(); iterator2.hasNext();) {
				SysWeixinMenuEntity sysWeixinMenuEntity = (SysWeixinMenuEntity) iterator2.next();
				ViewButton btn = new ViewButton();
				btn.setName(sysWeixinMenuEntity.getName());
				btn.setType("view");
				btn.setUrl(sysWeixinMenuEntity.getUrl());
				button[j] = btn;
				j++;
			}
			mainBtn.setSub_button(button);
			buttonfather[i] = mainBtn;
			i++;
		}
		menu.setButton(buttonfather);
		return menu;
	}
	
}
