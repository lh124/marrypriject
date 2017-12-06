package io.renren.controller.smart;

import io.renren.constant.ControllerConstant;
import io.renren.entity.smart.IoEntity;
import io.renren.entity.smart.SmartLeaveEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.service.smart.IoService;
import io.renren.service.smart.SmartLeaveService;
import io.renren.service.smart.StudentService;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("signControllerSmart")
@RequestMapping("/smart/sign")
public class StudentSignController {
	
	@Autowired
	private StudentService studentService;
	@Autowired
	private IoService ioService;
	@Autowired
	private SmartLeaveService smartLeaveService;
	
	/**
	 * 根据请假条id修改状态
	 */
	@RequestMapping("/updateleave")
	public R updateleave(HttpServletRequest request){
		Integer id = Integer.parseInt(request.getParameter("id"));
		String states = request.getParameter("states");
		SmartLeaveEntity leave = new SmartLeaveEntity();
		leave.setId(id);
		leave.setStates(states);
		smartLeaveService.update(leave);
		return R.ok();
	}
	
	/**
	 * 根据请假条id查询详情
	 */
	@RequestMapping("/findleavedetail")
	public R findleavedetail(HttpServletRequest request){
		SmartLeaveEntity leave = smartLeaveService.queryObject(Integer.parseInt(request.getParameter("id")));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("leave", leave);
		map.put("student", studentService.queryObject(leave.getUserid()));
		return R.ok().put("data", map);
	}
	
	/**
	 * 登录学生查询自己的请假记录
	 */
	@RequestMapping("/findleavelist")
	public R findleavelist(HttpServletRequest request,HttpSession session){
		StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", student.getId());
		map.put("classId", null);
		if(request.getParameter("page") != null && !"".equals(request.getParameter("page"))){
			int page = Integer.parseInt(request.getParameter("page"));
			map.put("limit", page*10);
			map.put("offset", (page-1)*10);
		}else{
			map.put("limit", 10);
			map.put("offset", 0);
		}
		return R.ok().put("list", smartLeaveService.queryList(map));
	}
	
	/**
	 * 根据班级id查询学生信息
	 */
	@RequestMapping("/findstudent")
	public R findstudent(HttpServletRequest request){
		Integer classId = Integer.parseInt(request.getParameter("classId"));
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("classId", classId);
		m.put("begin", 0);
		m.put("limit", 200);
		m.put("userType", 1);
		Map<String, Object> map = new HashMap<String, Object>();
		List<StudentEntity> list = studentService.queryList(m);//班级总人数
		List<StudentEntity> zxlist = getStudentzaixiao(classId, 1);//在校
		List<StudentEntity> bzxlist = getStudentzaixiao(classId, 2);//不在校
		List<StudentEntity> leavelist = getClassIdLeave(classId);//班级请假的人数
		if("1".equals(request.getParameter("type"))){
			map.put("classStudent", list);//班级总人数
		}if("2".equals(request.getParameter("type"))){
			map.put("classStudent", zxlist);
		}if("3".equals(request.getParameter("type"))){
			map.put("classStudent", bzxlist);
		}if("4".equals(request.getParameter("type"))){
			map.put("classStudent", leavelist);
		}
		map.put("classTotal", list.size());
		map.put("zxlistTotal", zxlist.size());
		map.put("bzxlistTotal", bzxlist.size());
		map.put("classLeaveTotal", leavelist.size());
		return R.ok().put("data", map);
	}
	
	/**
	 * 根据用户id查询进出记录
	 */
	@RequestMapping("/findStudentIo")
	public R findStudentIo(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("studentId", request.getParameter("studentId"));
		if(request.getParameter("page") != null && !"".equals(request.getParameter("page"))){
			int page = Integer.parseInt(request.getParameter("page"));
			map.put("limit", page*10);
			map.put("offset", (page-1)*10);
		}
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER2);
		List<IoEntity> ioList = ioService.queryList(map);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return R.ok().put("data", ioList);
	}
	
	/**
	 * 请假的人
	 * @param json
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<StudentEntity> getClassIdLeave(Integer classId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("offset", 0);
		map.put("limit", 100);
		map.put("userId", null);
		map.put("classId", classId);
		List<StudentEntity> list = new ArrayList<StudentEntity>();
		List<SmartLeaveEntity> leaveList = smartLeaveService.queryList(map);
		for (Iterator iterator = leaveList.iterator(); iterator.hasNext();) {
			SmartLeaveEntity smartLeaveEntity = (SmartLeaveEntity) iterator.next();
			StudentEntity student = studentService.queryObject(smartLeaveEntity.getUserid());
			student.setId(smartLeaveEntity.getId());//将请假条的id设置给学生id便于后面查询请假条详情
			list.add(student);
		}
		return list;
	}
	/**
	 * 根据班级id查询到校或者未到校的人数
	 * @param json
	 * @return
	 */
	private List<StudentEntity> getStudentzaixiao(Integer classId,Integer type){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("classId", classId);
		if(type == 1){
			//在校学生
			map.put("ioType", "进");
		}else if(type == 2){
			//不在校
			map.put("ioType", "出");
		}
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER2);
		List<StudentEntity> list = studentService.queryListStudent(map);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		return list;
	}

}
