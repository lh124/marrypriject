package io.renren.controller.app;

import io.renren.controller.AbstractController;
import io.renren.entity.app.WorkMainEntity;
import io.renren.service.SysUserService;
import io.renren.service.app.WorkMainService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

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
 * @date 2018-01-23 11:24:25
 */
@RestController
@RequestMapping("appInterface")
public class WorkMainController extends AbstractController{
	@Autowired
	private WorkMainService workMainService;
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("/main")
	public R main(HttpServletRequest request) throws Exception{
		String key = request.getParameter("key").replace("&quot;", "\"");
		JSONObject json = JSONObject.fromObject(key);
		System.out.println(json);
		String type = json.getString("type");
		if("getAllUser".equals(type)){
			//获取所有当前用户
			String roleName = json.getString("roleName");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("role_name", roleName);
			return R.ok().put("data", sysUserService.queryListtongji(map));
		}else if("getWorkList".equals(type)){
			//根据用户id获取其本周的任务列表
			Integer userId = json.getInt("userId");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("fatherId", "0");
			return R.ok().put("data", workMainService.queryList(map));
		}else if("getWorkDetail".equals(type)){
			//根据任务id获取其任务详情和子任务列表
			Integer id = json.getInt("id");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fatherId", id);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("list", workMainService.queryList(map));
			m.put("obj", workMainService.queryObject(id));
			return R.ok().put("data", m);
		}else if("updateHandleStates".equals(type)){
			//处理任务
			Integer id = json.getInt("id");
			Integer hadleStates = json.getInt("hadleStates");
			String hadleContent = json.getString("hadleContent");
			WorkMainEntity workMain = new WorkMainEntity();
			workMain.setId(id);
			workMain.setHandleContent(hadleContent);
			workMain.setHandleStates(hadleStates);
			workMainService.update(workMain);
			return R.ok();
		}
		return null;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("workmain:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		if(getUser().getUserId() != 1){
			params.put("userId", getUser().getUserId());
		}
        Query query = new Query(params);
		List<WorkMainEntity> workMainList = workMainService.queryList(query);
		int total = workMainService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(workMainList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("workmain:info")
	public R info(@PathVariable("id") Integer id){
		WorkMainEntity workMain = workMainService.queryObject(id);
		return R.ok().put("workMain", workMain);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("workmain:save")
	public R save(@RequestBody WorkMainEntity workMain){
		workMain.setUserId(Integer.parseInt(getUser().getUserId().toString()));
		workMain.setUserName(getUser().getName());
		workMain.setStates(0);
		workMain.setGmtModifiedtime(new Date());
		workMain.setHandleStates(2);
		workMainService.insert(workMain);
		return R.ok().put("id", workMain.getId());
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("workmain:update")
	public R update(@RequestBody WorkMainEntity workMain){
		workMainService.update(workMain);
		return R.ok().put("id", workMain.getId());
	}
	
	/**
	 * 修改任务状态
	 * @param workMain
	 * @return
	 */
	@RequestMapping("/updatestates")
	public R updatestates(HttpServletRequest request){
		WorkMainEntity workMain = new WorkMainEntity();
		workMain.setId(Integer.parseInt(request.getParameter("id")));
		workMain.setStates(1);
		workMain.setEndTime(new Date());
		workMainService.update(workMain);
		return R.ok();
	}
	
	/**
	 * 任务处理
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateHandle")
	public R updateHandle(HttpServletRequest request){
		WorkMainEntity workMain = new WorkMainEntity();
		workMain.setId(Integer.parseInt(request.getParameter("id")));
		workMain.setHandleStates(Integer.parseInt(request.getParameter("handleStates")));
		workMain.setHandleContent(request.getParameter("handleContent"));
		workMainService.update(workMain);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("workmain:delete")
	public R delete(@RequestBody Integer[] ids){
		workMainService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
