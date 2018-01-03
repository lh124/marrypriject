package io.renren.controller.smart;

import io.renren.entity.TokenEntity;
import io.renren.entity.app.SmartNewsEntity;
import io.renren.entity.smart.ClassEntity;
import io.renren.entity.smart.SchoolNoticeEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.service.TokenService;
import io.renren.service.smart.ClassService;
import io.renren.service.smart.SchoolNoticeService;
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

import jiguangtuisong.JpushClientUtil;
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
 * @date 2017-09-19 09:12:53
 */
@RestController
@RequestMapping("schoolnotice")
public class SchoolNoticeController {
	@Autowired
	private SchoolNoticeService schoolNoticeService;
	@Autowired
	private ClassService classService;
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
	@RequiresPermissions("schoolnotice:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SchoolNoticeEntity> schoolNoticeList = schoolNoticeService.queryList(query);
		int total = schoolNoticeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(schoolNoticeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("schoolnotice:info")
	public R info(@PathVariable("id") Integer id){
		SchoolNoticeEntity schoolNotice = schoolNoticeService.queryObject(id);
		
		return R.ok().put("schoolNotice", schoolNotice);
	}
	
	/**
	 * 保存
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/save")
	@RequiresPermissions("schoolnotice:save")
	public R save(@RequestBody SchoolNoticeEntity schoolNotice){
		schoolNotice.setCreatetime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		schoolNoticeService.insert(schoolNotice);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", 1);
		map.put("schoolId", schoolNotice.getSchoolid());
		map.put("begin", 0);
		map.put("limit", 100);
		List<ClassEntity> classList = classService.queryList(map);//通过学校id查询所有的班级
		for (Iterator iterator = classList.iterator(); iterator.hasNext();) {
			ClassEntity classEntity = (ClassEntity) iterator.next();
			map.put("classId", classEntity.getId());
			map.put("schoolId", null);
			map.put("userType", 1);
			List<StudentEntity> studentlist = studentService.queryList(map);
			for (Iterator iterator2 = studentlist.iterator(); iterator2.hasNext();) {
				StudentEntity studentEntity = (StudentEntity) iterator2.next();
				SmartNewsEntity sne = new SmartNewsEntity();
				sne.setNewsid(schoolNotice.getId());
				sne.setStates(1);
				sne.setNewstype(0);
				sne.setUserId(studentEntity.getId());
				smartNewsService.insert(sne);
				TokenEntity token = tokenService.queryByUserId(new Long(studentEntity.getId()));
				if(token != null){
					JpushClientUtil.sendToRegistrationId(studentEntity.getId().toString(), "学校通知", schoolNotice.getTitle(),
							schoolNotice.getContent(), JSONObject.fromObject(map).toString());
				}
			}
		}
		
		//发送给学校老师
		map.put("classId", null);
		map.put("schoolId", schoolNotice.getSchoolid());
		map.put("userType", 2);
		List<StudentEntity> studentlist = studentService.queryList(map);
		for (Iterator iterator2 = studentlist.iterator(); iterator2.hasNext();) {
			StudentEntity studentEntity = (StudentEntity) iterator2.next();
			SmartNewsEntity sne = new SmartNewsEntity();
			sne.setNewsid(schoolNotice.getId());
			sne.setStates(1);
			sne.setNewstype(0);
			sne.setUserId(studentEntity.getId());
			smartNewsService.insert(sne);
			TokenEntity token = tokenService.queryByUserId(new Long(studentEntity.getId()));
			if(token != null){
				JpushClientUtil.sendToRegistrationId(studentEntity.getId().toString(), "学校通知", schoolNotice.getTitle(),
						schoolNotice.getContent(), JSONObject.fromObject(map).toString());
			}
		}
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("schoolnotice:update")
	public R update(@RequestBody SchoolNoticeEntity schoolNotice){
		schoolNoticeService.update(schoolNotice);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("schoolnotice:delete")
	public R delete(@RequestBody Integer[] ids){
		schoolNoticeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
