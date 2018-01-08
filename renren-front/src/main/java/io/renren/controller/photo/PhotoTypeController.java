package io.renren.controller.photo;

import io.renren.annotation.CheckAuth;
import io.renren.annotation.IgnoreAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.PhotoFrontUserEntity;
import io.renren.entity.PhotoFrontUserInfoEntity;
import io.renren.entity.PhotoTypeEntity;
import io.renren.entity.PhotoUserClassEntity;
import io.renren.enums.ClassRoleEnum;
import io.renren.enums.PhotoTypeEnum;
import io.renren.model.json.ResponseDTJson;
import io.renren.service.PhotoFrontUserInfoService;
import io.renren.service.PhotoFrontUserService;
import io.renren.service.PhotoTypeService;
import io.renren.service.PhotoUserClassService;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.validator.ValidatorUtils;
import io.renren.validator.group.AddGroup;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;


/**
 * 相册分类表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
@RestController("frontPhotoTypeController")
@RequestMapping("/front/phototype")
public class PhotoTypeController {
	@Autowired
	private PhotoTypeService photoTypeService;
	
	@Autowired
	private PhotoFrontUserService photoFrontUserService;
	
	@Autowired
	private PhotoUserClassService photoUserClassService;
	
	@Autowired
	private PhotoFrontUserInfoService photoFrontUserInfoService;
	
	@ModelAttribute("frontUser")
	public PhotoFrontUserEntity getSessionUser(HttpServletRequest request)
	{
		PhotoFrontUserEntity user = (PhotoFrontUserEntity) request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
		
		if (user == null) 
			return null;
		
		return user;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("phototype:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoTypeEntity> photoTypeList = photoTypeService.queryList(query);
		int total = photoTypeService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoTypeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/listClassType")
	@IgnoreAuth
	public R listClassType(Long id, Integer type){
		//查询列表数据
		List<PhotoTypeEntity> typeList = this.photoTypeService.queryPhotoTypes(type, id, false);
		
		return R.ok().put("list", typeList).put("url", PostObjectPolicyController.CDN_URL);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("phototype:info")
	public R info(@PathVariable("id") Long id){
		PhotoTypeEntity photoType = photoTypeService.queryObject(id);
		
		return R.ok().put("photoType", photoType);
	}
	
	/**
	 * 查询用户相册
	 */
	@RequestMapping("/querUserType")
	@CheckAuth(needAuth="user:logined")
	public R querUserType(@ModelAttribute("frontUser") PhotoFrontUserEntity frontUser){
		
		PhotoTypeEntity pte = new PhotoTypeEntity();
		pte.setType(PhotoTypeEnum.PHOTO_TYPE_PERSONAL.getTypeValue());
		pte.setRelatedId(frontUser.getId());
		
		EntityWrapper<PhotoTypeEntity> wrapper = new EntityWrapper<PhotoTypeEntity>(pte);
		List<PhotoTypeEntity> list = this.photoTypeService.selectList(wrapper);
		
		return R.ok().put("list", list).put("url", PostObjectPolicyController.CDN_URL);
	}
	
	/**
	 * 查询班级相册分类
	 */
	@RequestMapping("/querClassType")
	@CheckAuth(needAuth="user:logined")
	public R querClassType(Long classId){
		
		PhotoTypeEntity pte = new PhotoTypeEntity();
		pte.setType(PhotoTypeEnum.PHOTO_TYPE_CLASS.getTypeValue());
		pte.setRelatedId(classId);
		
		EntityWrapper<PhotoTypeEntity> wrapper = new EntityWrapper<PhotoTypeEntity>(pte);
		List<PhotoTypeEntity> list = this.photoTypeService.selectList(wrapper);
		
		return R.ok().put("list", list).put("url", PostObjectPolicyController.CDN_URL);
	}
	
	
	
	/**
	 * 查询用户分类和照片
	 */
	@RequestMapping("/querUserTypePic")
	@CheckAuth(needAuth="user:logined")
	public R querUserTypePic(@ModelAttribute("frontUser") PhotoFrontUserEntity frontUser){
		
		List<PhotoTypeEntity> list = this.photoTypeService.queryUserPhotoTypes(PhotoTypeEnum.PHOTO_TYPE_PERSONAL.getTypeValue(), 
				frontUser.getId());
		
		return R.ok().put("list", list).put("url", PostObjectPolicyController.CDN_URL);
	}
	
	/**
	 * 查询用户分类和照片
	 */
	@RequestMapping("/queryClassPhotoTypes")
	@CheckAuth(needAuth="graduate:all")
	public R queryClassPhotoTypes(Long classId){
		
		List<PhotoTypeEntity> list = this.photoTypeService.queryClassPhotoTypes(PhotoTypeEnum.PHOTO_TYPE_CLASS.getTypeValue(), 
				classId);
		
		return R.ok().put("list", list).put("url", PostObjectPolicyController.CDN_URL);
	}
	
	/**
	 * 查询用户分类和照片
	 */
	@RequestMapping("/querOtherUserTypePic")
	@IgnoreAuth
	public R querOtherUserTypePic(Long id, String password){
		// 查询用户信息记录
		PhotoFrontUserInfoEntity pfuie = new PhotoFrontUserInfoEntity();
		pfuie.setFrontUserId(id);
		EntityWrapper<PhotoFrontUserInfoEntity> wrapper = new EntityWrapper<PhotoFrontUserInfoEntity>(pfuie);
		pfuie = this.photoFrontUserInfoService.selectOne(wrapper);
		
		
		// 判断是否有密码，如果没有密码则默认为不需要密码
		if (pfuie != null && pfuie.getPerm() != null) {
			if (pfuie.getPerm().equals(ControllerConstant.CLASS_PHOTO_PERM_NEED)) {
				
				if (password == null || password.equals(""))
					return R.error("需要输入密码查看");
				
				if (!pfuie.getPassword().equals(new Sha256Hash(password).toHex()))
					return R.error("密码错误");
			}
		}
		
		List<PhotoTypeEntity> list = this.photoTypeService.queryUserPhotoTypes(PhotoTypeEnum.PHOTO_TYPE_PERSONAL.getTypeValue(), 
				id);
		PhotoFrontUserEntity user = this.photoFrontUserService.queryObject(id);
		user.setPassword("");
		user.setAccount("");
		user.setId(0L);
		user.setPerm("");
		user.setPhone(user.getPhone());
		user.setNickname(user.getNickname());
		user.setStudentQq(user.getStudentQq());
		user.setStudentWeixin(user.getStudentWeixin());
		user.setStudentWhereabouts(user.getStudentWhereabouts());
		user.setStatus(0);
		user.setRoleId(0);
		user.setSex(0);
		
		R r = R.ok().put("list", list).put("url", PostObjectPolicyController.CDN_URL).put("user", user);
		System.out.println(r);
		return r;
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/classTypeSave")
	@CheckAuth(needAuth="graduate:all")
	public R classTypeSave(PhotoTypeEntity photoType, @ModelAttribute("frontUser") PhotoFrontUserEntity frontUser){
		
		ValidatorUtils.validateEntity(photoType, AddGroup.class);
		
		// 用户验证,防止其他用户非法操作
		PhotoUserClassEntity puce = new PhotoUserClassEntity();
		puce.setClassId(photoType.getRelatedId());
		puce.setFrontUserId(frontUser.getId());
		puce.setClassRoleType(ClassRoleEnum.CLASS_ADMIN.getTypeValue());
		EntityWrapper<PhotoUserClassEntity> wrapper = new EntityWrapper<PhotoUserClassEntity>(puce);
		int count = this.photoUserClassService.selectCount(wrapper);
		if ( count < 1) 
			return R.error("没有操作权限,非法操作");
		
		photoType.setGtmCreate(new Date());
		photoType.setGtmModified(new Date());
		photoType.setType(PhotoTypeEnum.PHOTO_TYPE_CLASS.getTypeValue());
		//photoType.setRelatedId(frontUser.getId());
		photoType.setTypeOrder(1);
		photoTypeService.save(photoType);
		
		return R.ok();
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@CheckAuth(needAuth="user:logined")
	public R save(PhotoTypeEntity photoType, @ModelAttribute("frontUser") PhotoFrontUserEntity frontUser){
		
		photoType.setGtmCreate(new Date());
		photoType.setGtmModified(new Date());
		photoType.setType(PhotoTypeEnum.PHOTO_TYPE_PERSONAL.getTypeValue());
		photoType.setRelatedId(frontUser.getId());
		photoType.setTypeOrder(1);
		photoTypeService.save(photoType);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@CheckAuth(needAuth="user:logined")
	public R update(Long typeId, String text, @ModelAttribute("frontUser") PhotoFrontUserEntity frontUser){
		
		PhotoTypeEntity photoTy = new PhotoTypeEntity();
		photoTy.setId(typeId);
		photoTy.setTitle(text);
		PhotoTypeEntity photoTypeOld = this.photoTypeService.queryObject(typeId);
		if (photoTypeOld == null ) 
			return R.error("分类不存在").put("status", ResponseDTJson.FAIL);
		if ( photoTypeOld.getType() != PhotoTypeEnum.PHOTO_TYPE_PERSONAL.getTypeValue() || 
				photoTypeOld.getRelatedId() != frontUser.getId() ) 
			return R.error("非法操作，三次非法操作冻结账户！").put("status", ResponseDTJson.FAIL);
		
		photoTypeService.update(photoTy);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@CheckAuth(needAuth="user:logined")
	public R delete(Long typeId , @ModelAttribute("frontUser") PhotoFrontUserEntity frontUser){
		
		PhotoTypeEntity pte = this.photoTypeService.queryObject(typeId);
		
		if (pte == null) 
			return R.error("记录不存在").put("status", ResponseDTJson.FAIL);
		if ( pte.getType() != PhotoTypeEnum.PHOTO_TYPE_PERSONAL.getTypeValue() || 
				pte.getRelatedId() != Integer.parseInt(frontUser.getId().toString()) ){
			return R.error("非法操作，三次非法操作冻结账户！").put("status", ResponseDTJson.FAIL);
		} 
		
		photoTypeService.delete(typeId);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/deleteClassType")
	@CheckAuth(needAuth="graduate:all")
	public R deleteClassType(Long typeId , @ModelAttribute("frontUser") PhotoFrontUserEntity frontUser){
		
		PhotoTypeEntity pte = this.photoTypeService.queryObject(typeId);
		
		if (pte == null) 
			return R.error("记录不存在");
		
		// 用户验证,防止其他用户非法操作
		PhotoUserClassEntity puce = new PhotoUserClassEntity();
		puce.setClassId(pte.getRelatedId());
		puce.setFrontUserId(frontUser.getId());
		puce.setClassRoleType(ClassRoleEnum.CLASS_ADMIN.getTypeValue());
		EntityWrapper<PhotoUserClassEntity> wrapper = new EntityWrapper<PhotoUserClassEntity>(puce);
		int count = this.photoUserClassService.selectCount(wrapper);
		if ( count < 1) 
			return R.error("没有操作权限,非法操作");
		
		photoTypeService.delete(typeId);
		
		return R.ok();
	}
	
}
