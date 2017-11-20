package io.renren.controller.tombstone;

import io.renren.entity.tombstone.TombstoneDeadEntity;
import io.renren.entity.tombstone.TombstoneUserEntity;
import io.renren.service.tombstone.TombstoneDeadService;
import io.renren.service.tombstone.TombstoneUserService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.ZXingCodeUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
 * @date 2017-10-23 10:54:33
 */
@RestController
@RequestMapping("tombstonedead")
public class TombstoneDeadController {
	@Autowired
	private TombstoneDeadService tombstoneDeadService;
	@Autowired
	private TombstoneUserService tombstoneUserService;
	
	/**
	 * 墓碑二维码下载二维码
	 */
	
	@RequestMapping("/dowloadClassQrCodemb")
	public void dowloadClassQrCodemb(Long classId, HttpServletResponse response) throws IOException{
		String url = "http://wrs.gykjewm.com/tombstone/weixin/dead.html?id=" + classId;
		ByteArrayOutputStream baos = ZXingCodeUtil.getQRCodeByteArray(url);
		byte[] data = baos.toByteArray();
		response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=\"id" + classId  + ".png\"");  
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
        IOUtils.write(data, response.getOutputStream());
        baos.close();
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/getlist")
	public R getlist(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", 0);
		map.put("order", "desc");
		map.put("sidx", null);
		map.put("limit", 100);
		map.put("userid", "parentid="+request.getParameter("parentid"));
		List<TombstoneDeadEntity> tombstoneDeadList = tombstoneDeadService.queryList(map);
		return R.ok().put("page", tombstoneDeadList);
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("tombstonedead:list")
	public R list(@RequestParam Map<String, Object> params){
		if(params.get("type").equals("1")){
			params.put("userid", "userid="+params.get("userid") + "  and parentid = 0");
		}else{
			params.put("userid", "parentid="+params.get("parentid"));
		}
		//查询列表数据
        Query query = new Query(params);

		List<TombstoneDeadEntity> tombstoneDeadList = tombstoneDeadService.queryList(query);
		int total = tombstoneDeadService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(tombstoneDeadList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("tombstonedead:info")
	public R info(@PathVariable("id") Integer id){
		TombstoneDeadEntity tombstoneDead = tombstoneDeadService.queryObject(id);
		
		return R.ok().put("tombstoneDead", tombstoneDead);
	}
	
	/**
	 * 基本信息和生平经历
	 */
	@RequestMapping("/saveorupdate")
	public R saveorupdate(HttpServletRequest request){
		TombstoneUserEntity tbuser = tombstoneUserService.queryObject(Integer.parseInt(request.getParameter("userid")));
		tbuser.setContent(request.getParameter("content"));
		tbuser.setExperience(request.getParameter("experience"));
		tombstoneUserService.update(tbuser);
		return R.ok();
	}
	
	/**
	 * 获取登录用户
	 */
	@RequestMapping("/getperson")
	public R getperson(HttpServletRequest request){
		TombstoneUserEntity tbuser = tombstoneUserService.queryObject(Integer.parseInt(request.getParameter("userid")));
		return R.ok().put("tbuser", tbuser);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("tombstonedead:save")
	public R save(@RequestBody TombstoneDeadEntity tombstoneDead){
		tombstoneDead.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		if(tombstoneDead.getUsertype() == null || tombstoneDead.getUsertype().equals("")){
			tombstoneDead.setUsertype("-1");
			tombstoneDead.setRelationtype("0");
		}else{
			if(tombstoneDead.getUsertype().equals("-1")){
				tombstoneDead.setRelationtype("0");
			}else{
				tombstoneDead.setRelationtype("1");
			}
		}
		tombstoneDeadService.insert(tombstoneDead);
		if(!tombstoneDead.getUsertype().equals("-1")){
			TombstoneDeadEntity tb = tombstoneDeadService.queryObject(Integer.parseInt(tombstoneDead.getUsertype()));
			tb.setUsertype(tombstoneDead.getId()+"");
			tombstoneDeadService.update(tb);
		}
		return R.ok().put("id", tombstoneDead.getId());
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("tombstonedead:update")
	public R update(@RequestBody TombstoneDeadEntity tombstoneDead){
		
		if(!tombstoneDead.getUsertype().equals("-1")){
			TombstoneDeadEntity tb = tombstoneDeadService.queryObject(Integer.parseInt(tombstoneDead.getUsertype()));
			tb.setUsertype("-1");
			tb.setRelationtype("0");
			tombstoneDeadService.update(tb);
			tombstoneDead.setRelationtype("1");
		}
		tombstoneDeadService.update(tombstoneDead);
		
		return R.ok().put("id", tombstoneDead.getId());
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("tombstonedead:delete")
	public R delete(@RequestBody Integer[] ids){
		tombstoneDeadService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
