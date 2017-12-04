package io.renren.controller.photoWeb;

import io.renren.controller.AbstractController;
import io.renren.entity.PhotoClassEntity;
import io.renren.entity.SysAdminSchoolEntity;
import io.renren.enums.ClassClassifyEnum;
import io.renren.enums.ClassStatusEnum;
import io.renren.model.json.ResponseDTJson;
import io.renren.service.PhotoClassService;
import io.renren.service.PhotoSchoolService;
import io.renren.service.SysAdminSchoolService;
import io.renren.service.smart.ClassService;
import io.renren.service.smart.SchoolService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.ZXingCodeUtil;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;
import io.renren.validator.ValidatorUtils;
import io.renren.validator.group.AddGroup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
 * @date 2017-05-04 15:07:49
 */
@RestController
@RequestMapping("/sys/photoclass")
public class PhotoClassController extends AbstractController{
	@Autowired
	private PhotoClassService photoClassService;
	
	@Autowired
	private SysAdminSchoolService sysAdminSchoolService;
	
	@Autowired
	private PhotoSchoolService photoSchoolService;
	
	@Autowired
	private SchoolService schoolService;
	
	@Autowired
	private ClassService classService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photoclass:list")
	public R list(@RequestParam Map<String, Object> params){
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		//查询列表数据
        Query query = new Query(params);
        
        Integer classify = params.containsKey("classify") ? Integer.parseInt(params.get("classify").toString()) : null;
        Long schoolId = params.containsKey("schoolId") ? Long.parseLong(params.get("schoolId").toString()) : null;
        Integer status = params.containsKey("status") ? Integer.parseInt(params.get("status").toString()) : null;
        Long collegeId = params.containsKey("collegeId") ? Long.parseLong(params.get("collegeId").toString()) : null;
        Long graduationTimeId = params.containsKey("graduationTimeId") ? Long.parseLong(params.get("graduationTimeId").toString()) : null;
        
        
        if ( classify == null ) {
        	query.put("classify", ClassClassifyEnum.GRADUATION_PHOTO.getValue());
        } else {
        	query.put("classify", classify);
        }
        if ( status == null ) {
        	query.put("status", ClassStatusEnum.STATUS_COMMON.getValue());
        } else {
        	query.put("status", status);
        }
        
        if ( schoolId != null ) {
        	query.put("schoolId", schoolId);
        }
        
        if ( graduationTimeId != null ) {
        	query.put("graduationTimeId", graduationTimeId);
        }
        
        if ( collegeId != null ) {
        	query.put("collegeId", collegeId);
        }
        
		List<PhotoClassEntity> photoClassList = photoClassService.queryList(query);
		int total = photoClassService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoClassList, total, query.getLimit(), query.getPage());
		
		/*List<HashMap<String, Object>> list = this.photoClassService.queryMapObject();
		System.out.println("--------------> size :" + list.size());
		for(HashMap<String, Object> hashmap : list){
			Set<Entry<String, Object>> set = hashmap.entrySet();
			
			for(Entry<String, Object> entry : set) {
				System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
			}
		}*/
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 学校管理员查询班级
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/adminList")
	@RequiresPermissions("photoclass:adminList")
	public R adminList(@RequestParam Map<String, Object> params){
		//查询列表数据
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
        Query query = new Query(params);
        
        Integer classify = params.containsKey("classify") ? Integer.parseInt(params.get("classify").toString()) : null;
        Long schoolId = params.containsKey("schoolId") ? Long.parseLong(params.get("schoolId").toString()) : null;
        Integer status = params.containsKey("status") ? Integer.parseInt(params.get("status").toString()) : null;
        Long collegeId = params.containsKey("collegeId") ? Long.parseLong(params.get("collegeId").toString()) : null;
        Long graduationTimeId = params.containsKey("graduationTimeId") ? Long.parseLong(params.get("graduationTimeId").toString()) : null;
        
        //查询管理学校
        Long adminId = this.getUserId();
		SysAdminSchoolEntity sase = new SysAdminSchoolEntity();
		sase.setAdminId(adminId);
		EntityWrapper<SysAdminSchoolEntity> wrapper = new EntityWrapper<SysAdminSchoolEntity>(sase);
		SysAdminSchoolEntity sasea = this.sysAdminSchoolService.selectOne(wrapper);
        
		
        if ( classify == null ) {
        	query.put("classify", ClassClassifyEnum.SMART_UNIFORM.getValue());
        } else {
        	query.put("classify", classify);
        }
        if ( status == null ) {
        	query.put("status", ClassStatusEnum.STATUS_COMMON.getValue());
        } else {
        	query.put("status", status);
        }
        
        if ( sasea != null ) {
        	query.put("schoolId", sasea.getShoolId());
        }
        
        if ( graduationTimeId != null ) {
        	query.put("graduationTimeId", graduationTimeId);
        }
        
        if ( collegeId != null ) {
        	query.put("collegeId", collegeId);
        }
        
		List<PhotoClassEntity> photoClassList = photoClassService.queryList(query);
		int total = photoClassService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoClassList, total, query.getLimit(), query.getPage());
		
		/*List<HashMap<String, Object>> list = this.photoClassService.queryMapObject();
		System.out.println("--------------> size :" + list.size());
		for(HashMap<String, Object> hashmap : list){
			Set<Entry<String, Object>> set = hashmap.entrySet();
			
			for(Entry<String, Object> entry : set) {
				System.out.println("key: " + entry.getKey() + " value: " + entry.getValue());
			}
		}*/
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 列表
	 */
	@RequestMapping("/theSameShchoolclasses")
	@RequiresPermissions("photofrontuser:saveMoreRelation")
	public R theSameShchoolclasses(Long classId){
		//查询列表数据
        
		PhotoClassEntity pc = this.photoClassService.queryObject(classId);
		
		if(pc == null)
			return R.error("班级不存在").put("status", ResponseDTJson.FAIL);
		
		PhotoClassEntity entiry = new PhotoClassEntity();
		entiry.setSchoolId(pc.getSchoolId());
		entiry.setClassify(ClassClassifyEnum.GRADUATION_PHOTO.getValue());
		EntityWrapper<PhotoClassEntity> wrapper = new EntityWrapper<PhotoClassEntity>(entiry);
		List<PhotoClassEntity> list = this.photoClassService.selectList(wrapper);
		
		return R.ok().put("list", list);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photoclass:info")
	public R info(@PathVariable("id") Long id){
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		PhotoClassEntity photoClass = photoClassService.queryObject(id);
		
		return R.ok().put("photoClass", photoClass);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("photoclass:save")
	public R save(@RequestBody PhotoClassEntity photoClass){
		ValidatorUtils.validateEntity(photoClass, AddGroup.class);
		
		photoClass.setGmtCreate(new Date());
		photoClass.setGmtModified(new Date());
		photoClass.setClassify(ClassClassifyEnum.GRADUATION_PHOTO.getValue());
		photoClass.setStatus(ClassStatusEnum.STATUS_COMMON.getValue());
		
		photoClassService.save(photoClass);
		
		return R.ok();
	}
	
	/**
	 * 保存
	 */
	/*@RequestMapping("/uniformSave")
	@RequiresPermissions("photoclass:uniformSave")
	public R uniformSave(@RequestBody PhotoClassEntity photoClass){
		ValidatorUtils.validateEntity(photoClass, UniformGroup.class);
		
		//查询管理学校
        Long adminId = this.getUserId();
		SysAdminSchoolEntity sase = new SysAdminSchoolEntity();
		sase.setAdminId(adminId);
		EntityWrapper<SysAdminSchoolEntity> wrapper = new EntityWrapper<SysAdminSchoolEntity>(sase);
		SysAdminSchoolEntity sasea = this.sysAdminSchoolService.selectOne(wrapper);
		
		if ( sasea == null) {
			return R.error("此用户未绑定学校").put("status", ResponseDTJson.FAIL);
		}
		
		// 同步到sqlserver 数据库中
		// 先查询学校是否有这个学校
		PhotoSchoolEntity schoolLocal = this.photoSchoolService.selectById(sasea.getShoolId());
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		School school = this.schoolService.selectById(sasea.getShoolId());
		if (school == null) {
			School insertSchool = new School();
			insertSchool.setId(sasea.getShoolId());
			insertSchool.setPw("123456");
			insertSchool.setSchoolName(schoolLocal.getName());
			
			this.schoolService.insert(insertSchool);
		}
		
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		// 本地系统插入班级
		photoClass.setSchoolId(sasea.getShoolId());
		photoClass.setGmtCreate(new Date());
		photoClass.setGmtModified(new Date());
		photoClass.setClassify(ClassClassifyEnum.SMART_UNIFORM.getValue());
		photoClass.setStatus(ClassStatusEnum.STATUS_COMMON.getValue());
		
		photoClassService.insert(photoClass);
		
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		//sqlserver 插入班级
		Classes cla = new Classes();
		cla.setId(photoClass.getId());
		cla.setClassName(photoClass.getName());
		cla.setSchoolId(photoClass.getSchoolId());
		
		this.classService.insert(cla);
		
		return R.ok();
	}*/
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photoclass:update")
	public R update(@RequestBody PhotoClassEntity photoClass){
		photoClassService.update(photoClass);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photoclass:delete")
	public R delete(@RequestBody Long[] ids){
		photoClassService.deleteBatch(ids);
		
		return R.ok();
	}
	
	
	@RequestMapping("/dowloadClassQrCode")
	@RequiresPermissions("photoclass:info")
	public void dowloadClassQrCode(Long classId, HttpServletResponse response) throws IOException{
		
		String url = "http://wrs.gykjewm.com/front/classes/classHomePage.html?classId=" + classId;
		ByteArrayOutputStream baos = ZXingCodeUtil.getQRCodeByteArray(url);
		byte[] data = baos.toByteArray();
		
		response.reset();  
        response.setHeader("Content-Disposition", "attachment; filename=\"class" + classId  + ".png\"");  
        response.addHeader("Content-Length", "" + data.length);  
        response.setContentType("application/octet-stream; charset=UTF-8");  
        
        IOUtils.write(data, response.getOutputStream());
        baos.close();
	}
	
	@SuppressWarnings("restriction")
	@RequestMapping("/showClassQrCode")
	@RequiresPermissions("photoclass:info")
	public R showClassQrCode(Long classId) throws IOException{
		
		String url = "http://wrs.gykjewm.com/front/classes/classHomePage.html?classId=" + classId;
		ByteArrayOutputStream baos = ZXingCodeUtil.getQRCodeByteArray(url);
		
		String imageBase64QRCode =  new sun.misc.BASE64Encoder().encodeBuffer(baos.toByteArray());
		baos.close();
		
		return R.ok("查询成功").put("qr_code", imageBase64QRCode);
		
	}
}
