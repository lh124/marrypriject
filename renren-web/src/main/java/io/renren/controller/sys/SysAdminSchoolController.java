package io.renren.controller.sys;

import io.renren.entity.PhotoSchoolEntity;
import io.renren.entity.SysAdminSchoolEntity;
import io.renren.entity.SysPhotoAdminSchoolEntity;
import io.renren.entity.SysUserEntity;
import io.renren.entity.smart.SchoolEntity;
import io.renren.model.json.ResponseDTJson;
import io.renren.service.PhotoSchoolService;
import io.renren.service.SysAdminSchoolService;
import io.renren.service.SysPhotoAdminSchoolService;
import io.renren.service.SysUserService;
import io.renren.service.smart.SchoolService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;
import io.renren.validator.ValidatorUtils;
import io.renren.validator.group.AddGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-27 22:23:54
 */
@RestController
@RequestMapping("/sys/sysadminschool")
public class SysAdminSchoolController {
	@Autowired
	private SysAdminSchoolService sysAdminSchoolService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysPhotoAdminSchoolService sysPhotoAdminSchoolService;
	
	@Autowired
	private PhotoSchoolService photoSchoolService;
	
	@Autowired
	private SchoolService schoolService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sysadminschool:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysAdminSchoolEntity> sysAdminSchoolList = sysAdminSchoolService.queryList(query);
		int total = sysAdminSchoolService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(sysAdminSchoolList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sysadminschool:info")
	public R info(@PathVariable("id") Long id){
		SysAdminSchoolEntity sysAdminSchool = sysAdminSchoolService.queryObject(id);
		
		return R.ok().put("sysAdminSchool", sysAdminSchool);
	}
	
	/**
	 * 保存相册系统
	 */
	@RequestMapping("/savephoto")
	@RequiresPermissions("sysadminschool:save")
	public R savephoto(SysPhotoAdminSchoolEntity sysAdminSchool){
		ValidatorUtils.validateEntity(sysAdminSchool, AddGroup.class);
		System.out.println(sysAdminSchool.getSchoolId() + "---------------------");
		EntityWrapper<SysPhotoAdminSchoolEntity> wrapper = new EntityWrapper<SysPhotoAdminSchoolEntity>(sysAdminSchool);
		SysPhotoAdminSchoolEntity sas = this.sysPhotoAdminSchoolService.selectOne(wrapper);
		if (sas != null)
			return R.error("此学校已添加").put("status", ResponseDTJson.FAIL);
		
		SysUserEntity user = this.sysUserService.queryObject(Long.parseLong(sysAdminSchool.getAdminId().toString()));
		if (user == null) 
			return R.error("管理员不存在").put("status", ResponseDTJson.FAIL);
		PhotoSchoolEntity school = this.photoSchoolService.selectById(sysAdminSchool.getSchoolId());
		if (school == null) 
			return R.error("学校不存在").put("status", ResponseDTJson.FAIL);
		sysAdminSchool.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		sysPhotoAdminSchoolService.save(sysAdminSchool);
		
		return R.ok();
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sysadminschool:save")
	public R save(SysAdminSchoolEntity sysAdminSchool){
		ValidatorUtils.validateEntity(sysAdminSchool, AddGroup.class);
		
		EntityWrapper<SysAdminSchoolEntity> wrapper = new EntityWrapper<SysAdminSchoolEntity>(sysAdminSchool);
		SysAdminSchoolEntity sas = this.sysAdminSchoolService.selectOne(wrapper);
		if (sas != null)
			return R.error("此学校已添加").put("status", ResponseDTJson.FAIL);
		
		SysUserEntity user = this.sysUserService.queryObject(sysAdminSchool.getAdminId());
		if (user == null) 
			return R.error("管理员不存在").put("status", ResponseDTJson.FAIL);
		
		
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		SchoolEntity school = this.schoolService.selectById(sysAdminSchool.getShoolId());
		if (school == null) 
			return R.error("学校不存在").put("status", ResponseDTJson.FAIL);
		
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		
		sysAdminSchool.setGmtCreate(new Date());
		sysAdminSchoolService.save(sysAdminSchool);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sysadminschool:update")
	public R update(SysAdminSchoolEntity sysAdminSchool){
		ValidatorUtils.validateEntity(sysAdminSchool, AddGroup.class);
		
		sysAdminSchoolService.update(sysAdminSchool);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sysadminschool:delete")
	public R delete(@RequestBody Long[] ids){
		sysAdminSchoolService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
