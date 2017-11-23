package io.renren.controller.smart;

import io.renren.entity.smart.WeixinFunctionEntity;
import io.renren.entity.smart.WeixinFunctionImgEntity;
import io.renren.service.smart.WeixinFunctionImgService;
import io.renren.service.smart.WeixinFunctionService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
 * @date 2017-11-23 10:34:44
 */
@RestController
@RequestMapping("weixinfunctionimg")
public class WeixinFunctionImgController {
	@Autowired
	private WeixinFunctionImgService weixinFunctionImgService;
	@Autowired
	private WeixinFunctionService weixinFunctionService;
	
	/**
	 * 列表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/list")
	@RequiresPermissions("weixinfunctionimg:list")
	public R list(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		List<WeixinFunctionEntity> weixinFunctionList = weixinFunctionService.queryList(query);
		List<WeixinFunctionEntity> list = new ArrayList<WeixinFunctionEntity>();
		for (Iterator iterator = weixinFunctionList.iterator(); iterator.hasNext();) {
			WeixinFunctionEntity weixinFunctionEntity = (WeixinFunctionEntity) iterator.next();
			params.put("functionId", weixinFunctionEntity.getId());
			List<WeixinFunctionImgEntity> weixinFunctionImgList = weixinFunctionImgService.queryList(params);
			if(weixinFunctionImgList.size() > 0){
				for (Iterator iterator2 = weixinFunctionImgList.iterator(); iterator2.hasNext();) {
					WeixinFunctionImgEntity weixinFunctionImgEntity = (WeixinFunctionImgEntity) iterator2.next();
					weixinFunctionEntity.setPic(weixinFunctionImgEntity.getPic());
				}
			}
			list.add(weixinFunctionEntity);
		}
		int total = weixinFunctionImgService.queryTotal(params);
		
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("weixinfunctionimg:info")
	public R info(@PathVariable("id") Integer id){
		WeixinFunctionImgEntity weixinFunctionImg = weixinFunctionImgService.queryObject(id);
		
		return R.ok().put("weixinFunctionImg", weixinFunctionImg);
	}
	
	/**
	 * 保存或者修改
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/saveorupdate")
	public R saveorupdate(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("schoolId", request.getParameter("schoolId"));
		map.put("functionId", request.getParameter("functionId"));
		List<WeixinFunctionImgEntity> weixinFunctionImgList = weixinFunctionImgService.queryList(map);
		if(weixinFunctionImgList.size() == 0){
			WeixinFunctionImgEntity weixinFunctionImg = new WeixinFunctionImgEntity();
			weixinFunctionImg.setFunctionId(Integer.parseInt(request.getParameter("functionId")));
			weixinFunctionImg.setSchoolId(Integer.parseInt(request.getParameter("schoolId")));
			weixinFunctionImgService.insert(weixinFunctionImg);
			return R.ok().put("id", weixinFunctionImg.getId());
		}else{
			int id = 0;
			for (Iterator iterator = weixinFunctionImgList.iterator(); iterator.hasNext();) {
				WeixinFunctionImgEntity weixinFunctionImgEntity = (WeixinFunctionImgEntity) iterator.next();
				id = weixinFunctionImgEntity.getId();
			}
			return R.ok().put("id", id);
		}
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public R delete(@RequestBody Integer[] ids){
		weixinFunctionImgService.deleteBatch(ids);
		return R.ok();
	}
	
}
