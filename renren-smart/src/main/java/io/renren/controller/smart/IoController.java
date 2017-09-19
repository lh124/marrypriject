package io.renren.controller.smart;

import io.renren.constant.ControllerConstant;
import io.renren.entity.smart.IoEntity;
import io.renren.entity.smart.StudentEntity;
import io.renren.service.smart.IoService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
 * @date 2017-08-28 12:02:26
 */
@RestController("ioControllerSmart")
@RequestMapping("/smart/io")
public class IoController {
	@Autowired
	private IoService ioService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params, HttpSession session){
		//查询列表数据
        Query query = new Query(params);
        
        
        StudentEntity student = (StudentEntity) session.getAttribute(ControllerConstant.SESSION_SMART_USER_KEY);
        Integer studentId = student.getId();
        
        query.put("studentId", studentId);
        DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		List<IoEntity> ioList = ioService.queryList(query);
		int total = ioService.queryTotal(query);
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		PageUtils pageUtil = new PageUtils(ioList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("io:info")
	public R info(@PathVariable("id") Integer id){
		IoEntity io = ioService.queryObject(id);
		
		return R.ok().put("io", io);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("io:save")
	public R save(@RequestBody IoEntity io){
		ioService.save(io);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("io:update")
	public R update(@RequestBody IoEntity io){
		ioService.update(io);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("io:delete")
	public R delete(@RequestBody Integer[] ids){
		ioService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
