package io.renren.controller.smart;

import io.renren.entity.smart.ClassEntity;
import io.renren.entity.smart.ClassInfoEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.service.smart.ClassInfoService;
import io.renren.service.smart.ClassService;
import io.renren.service.smart.StudentService;
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

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;


/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-09-22 09:38:52
 */
@RestController
@RequestMapping("classinfo")
public class ClassInfoController {
	@Autowired
	private ClassInfoService classInfoService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ClassService classService;
	public final static String TEACHERAPPKEY = "aecc535fc376fcb112949ee6";
	public final static String TEACHERMASTERSECRET = "436824de608a150e7e4105ce";
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("classinfo:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ClassInfoEntity> classInfoList = classInfoService.queryList(query);
		int total = classInfoService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(classInfoList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("classinfo:info")
	public R info(@PathVariable("id") Integer id){
		ClassInfoEntity classInfo = classInfoService.queryObject(id);
		return R.ok().put("classInfo", classInfo);
	}
	
	public void touserJiguangs(Integer id){
		ClassInfoEntity classInfo = classInfoService.queryObject(id);
		if(classInfo.getType() == 1 || classInfo.getType() == 2){
			StudentEntity studentEntity = studentService.queryObject(classInfo.getUserId());
			ClassEntity classEntity = classService.queryObject(classInfo.getClassid());
			try {
				JMessageClient client = new JMessageClient(TEACHERAPPKEY, TEACHERMASTERSECRET);
				String[] addList = new String[1];
				addList[0] = studentEntity.getGusername();
				client.addOrRemoveMembers(classEntity.getGid(), addList, null);
			} catch (APIConnectionException e) {
				e.printStackTrace();
			} catch (APIRequestException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("classinfo:save")
	public R save(@RequestBody ClassInfoEntity classInfo){
		classInfoService.insert(classInfo);
		touserJiguangs(classInfo.getId());
		return R.ok();
	}
	 
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("classinfo:update")
	public R update(@RequestBody ClassInfoEntity classInfo){
		classInfoService.update(classInfo);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("classinfo:delete")
	public R delete(@RequestBody Integer[] ids){
		classInfoService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
