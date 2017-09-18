package io.renren.controller.smart;

import io.renren.constant.ControllerConstant;
import io.renren.entity.smart.ClassEntity;
import io.renren.entity.smart.SchoolNoticeEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.service.smart.ClassService;
import io.renren.service.smart.SchoolNoticeService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-09-15 14:54:41
 */
@RestController("shouyeControllerSmart")
@RequestMapping("/smart/shouye")
public class SchoolNoticeSmartController {
	@Autowired
	private SchoolNoticeService schoolNoticeService;
	@Autowired
	private ClassService classService;
	
	/**
	 * 微信端学校通知列表
	 */
	@RequestMapping("/schoollist")
	public R schoollist(@RequestParam Map<String, Object> params, HttpSession session){
		//查询列表数据
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        //查询当前登录用户
        StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
        //查询当前用户所在班级
        ClassEntity cla = this.classService.selectById(student.getClassId());
        System.out.println(cla.getSchoolId());
        //将当前班级所在学校的id查询出来
        params.put("schoolId", cla.getSchoolId());
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		params.put("page", 1);
		params.put("limit", 100);
		params.put("sidx", "");
		params.put("order", "");
		Query query = new Query(params);
		List<SchoolNoticeEntity> schoolNoticeList = schoolNoticeService.queryList(query);
		int total = schoolNoticeService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(schoolNoticeList, total, query.getLimit(), query.getPage());
		return R.ok().put("schoolNotice", pageUtil);
	}
	
	/**
	 * 微信端学校通知详情
	 */
	@RequestMapping("/schoolnoticedetail")
	public R schoolnoticedetail(@RequestParam("id") Integer id){
		SchoolNoticeEntity schoolNotice = schoolNoticeService.queryObject(id);
		return R.ok().put("schoolNotice", schoolNotice);
	}
	
}
