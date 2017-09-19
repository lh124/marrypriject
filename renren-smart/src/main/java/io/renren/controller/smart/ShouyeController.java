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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-08-28 12:02:26
 */
@RestController("shouyeControllerSmart")
@RequestMapping("/smart/shouye")
public class ShouyeController {
	@Autowired
	private ClassService classService;
	@Autowired
	private SchoolNoticeService schoolNoticeService;
	
	/**
	 * 学校通知列表
	 */
	@RequestMapping("/list_6")
	public R list(@RequestParam Map<String, Object> params, HttpSession session){
		//查询列表数据
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
        StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		ClassEntity cla = this.classService.selectById(student.getClassId());
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		params.put("schoolid", cla.getSchoolId());
		Query query = new Query(params);
		List<SchoolNoticeEntity> schoolNoticeList = schoolNoticeService.queryList(query);
		int total = schoolNoticeService.queryTotal(query);
		PageUtils pageUtil = new PageUtils(schoolNoticeList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/schoolNoticeInfo")
	public R info(@RequestParam("id") Integer id){
		SchoolNoticeEntity schoolnotice = this.schoolNoticeService.selectById(id);
		return R.ok().put("schoolnotice", schoolnotice);
	}
	
}
