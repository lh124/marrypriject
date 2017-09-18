package io.renren.controller.photoWeb;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;

import io.renren.controller.AbstractController;
import io.renren.entity.PhotoSchoolEntity;
import io.renren.entity.SysAdminSchoolEntity;
import io.renren.entity.smart.SchoolEntity;
import io.renren.model.dto.SchoolDto;
import io.renren.service.PhotoSchoolService;
import io.renren.service.SysAdminSchoolService;
import io.renren.service.smart.SchoolService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.RRException;
import io.renren.utils.ZXingCodeUtil;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;
import io.renren.validator.ValidatorUtils;
import io.renren.validator.group.AddGroup;
import io.renren.xss.SQLFilter;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
@RestController
@RequestMapping("/sys/photoschool")
public class PhotoSchoolController extends AbstractController{
	
	/**
	 * 表示管理这所学校的管理员id
	 */
	public static final String ADMIN_ID = "adminId";
	
	@Autowired
	private PhotoSchoolService photoSchoolService;
	
	
	@Autowired
	private SysAdminSchoolService sysAdminSchoolService;
	
	@Autowired
	private SchoolService schoolService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photoschool:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        String cityId = params.containsKey("cityId") ? SQLFilter.sqlInject(params.get("cityId").toString()) : null;
        String schoolName = params.containsKey("schoolName") ? SQLFilter.sqlInject(params.get("schoolName").toString()) : null;
        
        if(schoolName != null && schoolName != ""){
        	query.put("schoolName", schoolName);
        }
        if(cityId != null && cityId != ""){
        	query.put("cityId", cityId);
        }
        

		List<PhotoSchoolEntity> photoSchoolList = photoSchoolService.queryList(query);
		int total = photoSchoolService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoSchoolList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/listAdminSchool")
	@RequiresPermissions("photoschool:listAdminSchool")
	public R listAdminSchool(@RequestParam Map<String, Object> params){
		
		//查询列表数据
		Long adminId = null;
		if (params.containsKey(ADMIN_ID) && params.get(ADMIN_ID) != null) {
			// 表示超级管理员查询管理员管理的学校
			adminId = Long.parseLong( params.get(ADMIN_ID).toString());
		} else {
			// 表示管理员查询自己管理的学校
			adminId = this.getUserId();
		}
        
		//  查询管理学校
		SysAdminSchoolEntity sase = new SysAdminSchoolEntity();
		sase.setAdminId(adminId);
		EntityWrapper<SysAdminSchoolEntity> wrapper = new EntityWrapper<SysAdminSchoolEntity>(sase);
		List<SysAdminSchoolEntity> saseaList = this.sysAdminSchoolService.selectList(wrapper);
		
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		SchoolEntity se = null;
		for(int i=0; i<(saseaList.size()); i++){
			se = this.schoolService.selectById(saseaList.get(i).getShoolId());
			saseaList.get(i).setSchool(se);
		}
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		
		int total = this.sysAdminSchoolService.selectCount(wrapper);
		
		PageUtils pageUtil = new PageUtils(saseaList, total, Integer.parseInt(params.get("limit").toString()), Integer.parseInt(params.get("page").toString()));
		
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photoschool:info")
	public R info(@PathVariable("id") Long id){
		PhotoSchoolEntity photoSchool = photoSchoolService.queryObject(id);
		
		
		return R.ok().put("photoSchool", photoSchool);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photoschool:save")
	public R save(@RequestBody SchoolDto photoSchool, HttpServletRequest request){
		
		ValidatorUtils.validateEntity(photoSchool, AddGroup.class);
		
		PhotoSchoolEntity school = new PhotoSchoolEntity();
		
		//System.out.println(photoSchool);
		
		
		try{
			// 拷贝属性值
			BeanUtils.copyProperties(school, photoSchool);
			school.setGmtCreate(new Date());
			school.setGmtModefied(new Date());
		}catch(Exception e){
			e.printStackTrace();
			RRException ee = new RRException("参数异常");
			throw ee;
		}
		
		//PhotoFrontUserEntity user = request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
		
		photoSchoolService.save(school);
		
		return R.ok().put("schoolId", school.getId());
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photoschool:update")
	public R update(@RequestBody PhotoSchoolEntity photoSchool){
		photoSchoolService.update(photoSchool);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photoschool:delete")
	public R delete(@RequestBody Long[] ids){
		photoSchoolService.deleteBatch(ids);
		
		return R.ok();
	}
	
	@RequestMapping("/dowloadSchoolQrCode")
	@RequiresPermissions("photoschool:list")
	public void dowloadClassQrCode(Long schoolId, HttpServletResponse response) throws IOException{
		
		String url = "http://wrs.gykjewm.com/front/school/schoolHomePage.html?schoolId=" + schoolId;
		ByteArrayOutputStream baos = ZXingCodeUtil.getQRCodeByteArray(url);
		byte[] data = baos.toByteArray();
		
		response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=\"school" + schoolId  + ".png\"");  
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
        
        IOUtils.write(data, response.getOutputStream());
        baos.close();
	}
	
}
