package io.renren.controller.photo;

import io.renren.annotation.CheckAuth;
import io.renren.annotation.IgnoreAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.PhotoClassEntity;
import io.renren.entity.PhotoFrontUserEntity;
import io.renren.entity.PhotoTypeEntity;
import io.renren.entity.PhotoUserClassEntity;
import io.renren.enums.ClassClassifyEnum;
import io.renren.enums.ClassRoleEnum;
import io.renren.enums.ClassStatusEnum;
import io.renren.enums.UserClasssStatusEnum;
import io.renren.service.PhotoClassService;
import io.renren.service.PhotoFrontUserService;
import io.renren.service.PhotoPicClassService;
import io.renren.service.PhotoTypeService;
import io.renren.service.PhotoUserClassService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
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
@RestController("frontPhotoClassController")
@RequestMapping("front/photoclass")
public class PhotoClassController {
	@Autowired
	private PhotoClassService photoClassService;
	
	@Autowired
	private PhotoPicClassService photoPicClassService;
	
	@Autowired
	private PhotoTypeService photoTypeService;
	
	@Autowired
	private PhotoFrontUserService photoFrontUserService;
	
	@Autowired
	private PhotoUserClassService photoUserClassService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@CheckAuth(needAuth="user:logined")
	public R list(@RequestParam Map<String, Object> params, HttpSession session){
		//查询列表数据
        Query query = new Query(params);
        
        Integer classify = params.containsKey("classify") ? Integer.parseInt(params.get("classify").toString()) : null;
        Long schoolId = params.containsKey("schoolId") ? Long.parseLong(params.get("schoolId").toString()) : null;
        Integer status = params.containsKey("schoolId") ? Integer.parseInt(params.get("schoolId").toString()) : null;
        Long collegeId = params.containsKey("schoolId") ? Long.parseLong(params.get("schoolId").toString()) : null;
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
        
        PhotoFrontUserEntity user = (PhotoFrontUserEntity) session.getAttribute(ControllerConstant.USER_SESSION_KEY);
        query.put("userId", user.getId());
        
		List<PhotoClassEntity> photoClassList = photoClassService.queryList(query);
		for(PhotoClassEntity pce : photoClassList){
			pce.setPassword("");
		}
		int total = photoClassService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoClassList, total, query.getLimit(), query.getPage());
		return R.ok().put("page", pageUtil);
	}
	
	
	@RequestMapping("/adminClass")
	@CheckAuth(needAuth="graduate:all")
	public R adminClass(HttpServletRequest request){
		
		PhotoFrontUserEntity user = (PhotoFrontUserEntity) request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
		List<PhotoClassEntity> classList = this.photoClassService.querAdminClass(user.getId(), ClassRoleEnum.CLASS_ADMIN.getTypeValue());
		return R.ok().put("classList", classList);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@IgnoreAuth
	public R info(@PathVariable("id") Long id , String password){
		PhotoClassEntity photoClass = photoClassService.queryObject(id);
		// 验证密码
		if ( photoClass.getPerm().equals(ControllerConstant.CLASS_PHOTO_PERM_NEED)) {
			if (password == null)
				return R.error("需要查看密码");
			String pass = new Sha256Hash(password).toHex();
			if (!photoClass.getPassword().equals(pass))
				return R.error("密码错误");
		}
		
		List<PhotoTypeEntity> typeList = this.photoTypeService.queryPhotoTypes(1, id, true);
		List<PhotoFrontUserEntity> userList = this.photoFrontUserService.queryUsersByClassId(id, UserClasssStatusEnum.STATUS_COMMON.getValue());
		
		photoClass.setPhotoTypeList(typeList);
		photoClass.setUserList(userList);
		/*for(PhotoTypeEntity pte : typeList){
			System.out.println(pte.toString());
		}*/
		return R.ok().put("photoClass", photoClass).put("url", PostObjectPolicyController.CDN_URL);
	}
	
	/**
	 * 获取班级相册查看权限，是否需要密码
	 */
	@RequestMapping("/secretInfo/{id}")
	@CheckAuth(needAuth="graduate:all")
	public R secretInfo(@PathVariable("id") Long id){
		PhotoClassEntity photoClass = photoClassService.queryObject(id);
		
		if ( photoClass != null ) {
			return R.ok().put("perm", photoClass.getPerm());
		}
		
		return R.error("查询记录不存在");
	}
	
	/**
	 * 查询班级描述信息
	 */
	@RequestMapping("/descInfo/{id}")
	@CheckAuth(needAuth="graduate:all")
	public R descInfo(@PathVariable("id") Long id){
		PhotoClassEntity photoClass = photoClassService.queryObject(id);
		
		if ( photoClass != null ) {
			return R.ok().put("description", photoClass.getClassDesc());
		}
		
		return R.error("查询记录不存在");
	}
	
	/**
	 * 用户修改班级信息
	 */
	@RequestMapping("/userUpdate")
	@CheckAuth(needAuth="graduate:all")
	public R userUpdate(HttpServletRequest request,PhotoClassEntity photoClass){
		PhotoFrontUserEntity user = (PhotoFrontUserEntity) request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
		
		PhotoUserClassEntity userClass = new PhotoUserClassEntity();
		userClass.setClassId(photoClass.getId());
		userClass.setFrontUserId(user.getId());
		userClass.setClassRoleType(ClassRoleEnum.CLASS_ADMIN.getTypeValue());
		
		EntityWrapper<PhotoUserClassEntity> wrapper = new EntityWrapper<PhotoUserClassEntity>(userClass);
		int count = this.photoUserClassService.selectCount(wrapper);
		
		if (count < 1) 
			return R.error("无权限操作");
		
		photoClass.setCollegeId(null);
		photoClass.setClassify(null);
		
		if (photoClass.getPassword() != null && photoClass.getPassword() != "") {
			// 密码加密
			photoClass.setPassword(new Sha256Hash(photoClass.getPassword()).toHex());
		}
		
		// 更新数据库
		photoClassService.update(photoClass);
		
		return R.ok();
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/queryByGruadtionTime")
	@IgnoreAuth
	public R queryByGruadtionTime(Long gradutionTimeId, Long schoolId, Long collegeId){
		
		List<PhotoClassEntity> classList = null;
		EntityWrapper<PhotoClassEntity> wrapper = new EntityWrapper<PhotoClassEntity>();
		PhotoClassEntity pce = new PhotoClassEntity();
		pce.setClassify(ClassClassifyEnum.GRADUATION_PHOTO.getValue());
		pce.setStatus(ClassStatusEnum.STATUS_COMMON.getValue());
		pce.setGraduationTimeId(gradutionTimeId);
		
		if ( schoolId != null) {
			pce.setSchoolId(schoolId);
			wrapper.setEntity(pce);
			classList = this.photoClassService.selectList(wrapper);
			
		} else {
			pce.setCollegeId(collegeId);
			wrapper.setEntity(pce);
			classList = this.photoClassService.selectList(wrapper);
		}
		
		
		return R.ok().put("photoClass", classList);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	public R save(@RequestBody PhotoClassEntity photoClass){
		photoClassService.save(photoClass);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
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
	
}
