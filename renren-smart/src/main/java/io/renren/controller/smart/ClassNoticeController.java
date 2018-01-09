package io.renren.controller.smart;

import io.renren.entity.TokenEntity;
import io.renren.entity.app.SmartNewsEntity;
import io.renren.entity.smart.ClassNoticeEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.service.TokenService;
import io.renren.service.smart.ClassNoticeService;
import io.renren.service.smart.SmartNewsService;
import io.renren.service.smart.StudentService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jiguangtuisong.JpushClientUtil2;
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
 * @date 2017-09-20 10:04:06
 */
@RestController
@RequestMapping("classnotice")
public class ClassNoticeController {
	@Autowired
	private ClassNoticeService classNoticeService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private SmartNewsService smartNewsService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("classnotice:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ClassNoticeEntity> classNoticeList = classNoticeService.queryList(query);
		int total = classNoticeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(classNoticeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("classnotice:info")
	public R info(@PathVariable("id") Integer id){
		ClassNoticeEntity classNotice = classNoticeService.queryObject(id);
		
		return R.ok().put("classNotice", classNotice);
	}
	
	/**
	 * 保存
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/save")
	@RequiresPermissions("classnotice:save")
	public R save(@RequestBody ClassNoticeEntity classNotice){
		classNotice.setCreatetime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		classNoticeService.insert(classNotice);
		Map<String, Object> map = new HashMap<String, Object>();
		//发送给班级学生
		map.put("classId", classNotice.getClassId());
		map.put("schoolId", null);
		map.put("userType", 1);
		map.put("begin", 0);
		map.put("limit", 100);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("type", 2);
		List<StudentEntity> studentlist = studentService.queryList(map);
		for (Iterator iterator2 = studentlist.iterator(); iterator2.hasNext();) {
				StudentEntity studentEntity = (StudentEntity) iterator2.next();
				SmartNewsEntity sne = new SmartNewsEntity();
				sne.setNewsid(classNotice.getId());
				sne.setStates(2);
				sne.setNewstype(0);
				sne.setUserId(studentEntity.getId());
				smartNewsService.insert(sne);
				TokenEntity token = tokenService.queryByUserId(new Long(studentEntity.getId()));
				if(token != null){
					JpushClientUtil2.sendToRegistrationId(studentEntity.getId().toString(), "老师通知", classNotice.getTitle(),
						classNotice.getContent(),JSONObject.fromObject(m).toString());
				}
			}
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("classnotice:update")
	public R update(@RequestBody ClassNoticeEntity classNotice){
		classNoticeService.update(classNotice);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("classnotice:delete")
	public R delete(@RequestBody Integer[] ids){
		classNoticeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
