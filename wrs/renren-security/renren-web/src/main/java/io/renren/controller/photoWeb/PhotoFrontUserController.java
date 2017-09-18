package io.renren.controller.photoWeb;

import io.renren.annotation.JSONFilter;
import io.renren.entity.PhotoClassEntity;
import io.renren.entity.PhotoFrontUserEntity;
import io.renren.entity.PhotoFunctionModulesEntity;
import io.renren.entity.PhotoUserClassEntity;
import io.renren.entity.PhotoUserFunctionEntity;
import io.renren.enums.ClassClassifyEnum;
import io.renren.enums.ClassRoleEnum;
import io.renren.enums.UserClasssStatusEnum;
import io.renren.enums.UserPermissionEnum;
import io.renren.enums.UserStatusEnum;
import io.renren.model.json.RWrapper;
import io.renren.model.json.ResponseDTJson;
import io.renren.service.PhotoClassService;
import io.renren.service.PhotoFrontUserService;
import io.renren.service.PhotoFunctionModulesService;
import io.renren.service.PhotoUserClassService;
import io.renren.service.PhotoUserFunctionService;
import io.renren.service.smart.StudentService;
import io.renren.utils.POIMvcUtil;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.dataSource.DBTypeEnum;
import io.renren.utils.dataSource.DbContextHolder;
import io.renren.validator.ValidatorUtils;
import io.renren.validator.group.AddGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.mapper.EntityWrapper;


/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-05-04 15:07:49
 */
@Controller
@RequestMapping("photofrontuser")
@SessionAttributes(value={"classId"})
public class PhotoFrontUserController {
	@Autowired
	private PhotoFrontUserService photoFrontUserService;
	
	@Autowired
	private PhotoUserClassService photoUserClassService;
	
	@Autowired
	private PhotoUserFunctionService photoUserFunctionService;
	
	@Autowired
	private PhotoFunctionModulesService photoFunctionModulesService;
	
	@Autowired
	private PhotoClassService photoClassService;
	
	@Autowired
	private StudentService studentService;
	
	/*
	 * 性别：男
	 */
	public static int MAN = 1;
	/*
	 * 性别：女
	 */
	public static int WOMEN = 2;
	/*
	 * 住校生
	 */
	public static String IN_RESIDENCE_2 = "住校生";
	/*
	 * 走读生
	 */
	public static String OUT_RESIDENCE_1 = "走读生";
	
	/*
	 * 用户分类：微人生相册用户
	 */
	public static int USER_TYPE_PHOTO = 1;
	/*
	 * 用户分类：智能校服用户
	 */
	public static int USER_TYPE_UNIFORM = 2;
	
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photofrontuser:list")
	@JSONFilter(type = PhotoFrontUserEntity.class, filter = "password")
	public RWrapper list(@RequestParam Map<String, Object> params, Model model){
		
		//查询列表数据
        Query query = new Query(params);
        Long classId = params.containsKey("classId") ? Long.parseLong(params.get("classId").toString()) : null ;
        
        if (classId != null) {
        	query.put("classId", classId);
        	model.addAttribute("classId", classId);
        }
        
		List<PhotoFrontUserEntity> photoFrontUserList = photoFrontUserService.queryList(query);
		int total = photoFrontUserService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoFrontUserList, total, query.getLimit(), query.getPage());
		
		RWrapper rw = new RWrapper();
		rw.setPage(pageUtil);
		return rw;
		//return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@ResponseBody
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photofrontuser:info")
	public R info(@PathVariable("id") Long id, @ModelAttribute("classId") Long classId){
		PhotoFrontUserEntity photoFrontUser = photoFrontUserService.queryClassUser(id, classId);
		
		return R.ok().put("photoFrontUser", photoFrontUser);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions("photofrontuser:save")
	public R save(@RequestBody PhotoFrontUserEntity photoFrontUser, @ModelAttribute("classId") String classId){
		
		ValidatorUtils.validateEntity(photoFrontUser, AddGroup.class);
		
		PhotoClassEntity pce = this.photoClassService.queryObject(Long.parseLong(classId));
		if (pce == null) {
			return R.error("班级不存在").put("status", ResponseDTJson.FAIL);
		}
		
		// 账号重复验证
		PhotoFrontUserEntity  pfue = new PhotoFrontUserEntity();
		pfue.setAccount(photoFrontUser.getAccount());
		EntityWrapper<PhotoFrontUserEntity> wrraper = new EntityWrapper<PhotoFrontUserEntity>(pfue);
		PhotoFrontUserEntity pfu = this.photoFrontUserService.selectOne(wrraper);
		
		if (pfu != null) {
			return R.error("账号不能重复");
		}
		// 号码重复验证
		if (photoFrontUser.getPhone() != null) {
			pfue = new PhotoFrontUserEntity();
			pfue.setAccount(null);
			pfue.setPhone(photoFrontUser.getPhone());
			
			wrraper = new EntityWrapper<PhotoFrontUserEntity>(pfue);
			
			pfu = this.photoFrontUserService.selectOne(wrraper);
			if (pfu != null) {
				return R.error("手机号码不能重复");
			}
		}
		
		//String phone = photoFrontUser.getPhone();
		//phone.Su
		//sha256加密
		String newPassword = new Sha256Hash(photoFrontUser.getAccount()).toHex();
		photoFrontUser.setPassword(newPassword);
		photoFrontUser.setGmtCreate(new Date());
		photoFrontUser.setGmtModified(new Date());
		photoFrontUser.setStatus(UserStatusEnum.STATUS_COMMON.getValue());
		
		photoFrontUserService.save(photoFrontUser);
		
		//  关联班级
		PhotoUserClassEntity puc = new PhotoUserClassEntity();
		
		puc.setClassId(Long.parseLong(classId));
		puc.setClassRoleType(photoFrontUser.getRoleId());
		puc.setFrontUserId(photoFrontUser.getId());
		puc.setStatus(UserClasssStatusEnum.STATUS_COMMON.getValue());
		puc.setGtmCreate(new Date());
		puc.setGtmModified(new Date());
		
		photoUserClassService.save(puc);
		
		//用户授权
		PhotoUserFunctionEntity pfePhoto = new PhotoUserFunctionEntity();
		
		PhotoFunctionModulesEntity pfme = new PhotoFunctionModulesEntity();
		pfme.setPerm(UserPermissionEnum.PERM_BASE_LOGIN.getValue());
		EntityWrapper<PhotoFunctionModulesEntity> wrapper = new EntityWrapper<PhotoFunctionModulesEntity>(pfme);
		pfme = photoFunctionModulesService.selectOne(wrapper);
		
		// 基本权限
		if (pfme != null ) {
			pfePhoto.setGmtCreate(new Date());
			pfePhoto.setGmtModified(new Date());
			pfePhoto.setUserId(photoFrontUser.getId());
			pfePhoto.setFunctionModulesId(pfme.getId().longValue());
			
			photoUserFunctionService.save(pfePhoto);
		}
		
		PhotoFunctionModulesEntity pfme2 = new PhotoFunctionModulesEntity();
		// 遍历找出属于哪个分类
		for ( int i = 0; i < ClassClassifyEnum.values().length; i++) {
			if (pce.getClassify().equals(ClassClassifyEnum.values()[i].getValue())) {
				pfme2.setPerm(ClassClassifyEnum.values()[i].getPerm());
				break;
			}
				
		} 
		//pfme2.setPerm(UserPermissionEnum.PERM_GRADUATION_PHOTO.getValue());
		EntityWrapper<PhotoFunctionModulesEntity> wrapper2 = new EntityWrapper<PhotoFunctionModulesEntity>(pfme2);
		pfme2 = photoFunctionModulesService.selectOne(wrapper2);
		
		// 相册权限权限
		if (pfme2 != null ) {
			pfePhoto.setId(null);
			pfePhoto.setGmtCreate(new Date());
			pfePhoto.setGmtModified(new Date());
			pfePhoto.setUserId(photoFrontUser.getId());
			pfePhoto.setFunctionModulesId(pfme2.getId().longValue());
			
			photoUserFunctionService.save(pfePhoto);
		}
		
		
		
		return R.ok();
	}
	
	@ResponseBody
	@RequestMapping(value="/saveByExcel",method=RequestMethod.POST)
	@RequiresPermissions("photofrontuser:save")
	public R saveByExcel(@RequestParam(value="file", required=false) MultipartFile file, @ModelAttribute("classId") String classId){
		if (file == null ) {
			return R.error("未选择文件").put("status", ResponseDTJson.FAIL);
		}
		
		String fileName = file.getOriginalFilename();
		POIMvcUtil poi = new POIMvcUtil();
		List<List<Object>> list = null;
		
		try{
			list = poi.getBankListByExcel(file.getInputStream(), fileName);
		}catch(Exception e){
			e.printStackTrace();
			return R.error("读取Excel发生异常").put("status", ResponseDTJson.FAIL);
		}
		
		PhotoFrontUserEntity frontUser = null;
		// 数字格式化
		List<PhotoFrontUserEntity> userList = new ArrayList<PhotoFrontUserEntity>(list.size());
		System.out.println(list.size());
		for(int i=1; i<list.size(); i++){
			
			frontUser = new PhotoFrontUserEntity();
			List<Object> objList = list.get(i);
			// 防止空数据
			if ( objList.get(0) == null || objList.get(0).toString().equals(""))
				break;
			frontUser.setAccount(objList.get(0).toString());
			frontUser.setGmtCreate(new Date());
			frontUser.setNickname(objList.get(1).toString());
			int sex = (objList.get(2)== null || objList.get(2).toString().equals("男")) ? 1 : 2;
			frontUser.setSex(sex);
			//防止空号码
			if (objList.get(3) != null && !objList.get(3).toString().equals(""))
				frontUser.setPhone(objList.get(3).toString());
			//防止班级角色为空
			if ( objList.get(4).toString() != null && !objList.get(4).toString().equals("")) {
				Integer roleId = ClassRoleEnum.getValue(objList.get(4).toString());
				frontUser.setRoleId(roleId);
			}
			
			userList.add(frontUser);
		}
		
		String backe = this.batchSaveUsers(USER_TYPE_PHOTO, userList, Long.parseLong(classId));
		
		return R.ok(backe).put("status", ResponseDTJson.SUCCESS);
	}
	
	/**
	 * 保存
	 */
	/*@ResponseBody
	@RequestMapping(value="/unifromSaveByExcel",method=RequestMethod.POST)
	@RequiresPermissions("photofrontuser:save")
	public R unifromSaveByExcel(@RequestParam(value="file", required=false) MultipartFile file, @ModelAttribute("classId") String classId){
		
		if (file == null ) {
			return R.error("为选择文件").put("status", ResponseDTJson.FAIL);
		}
		
		String fileName = file.getOriginalFilename();
		POIMvcUtil poi = new POIMvcUtil();
		List<List<Object>> list = null;
		
		try{
			list = poi.getBankListByExcel(file.getInputStream(), fileName);
		}catch(Exception e){
			e.printStackTrace();
			return R.error("读取Excel发生异常").put("status", ResponseDTJson.FAIL);
		}
		
		PhotoFrontUserEntity frontUser = null;
		// 数字格式化
		List<PhotoFrontUserEntity> userList = new ArrayList<PhotoFrontUserEntity>(list.size());
		for(int i=1; i<list.size(); i++){
			frontUser = new PhotoFrontUserEntity();
			List<Object> objList = list.get(i);
			frontUser.setAccount(objList.get(0).toString());
			frontUser.setGmtCreate(new Date());
			frontUser.setNickname(objList.get(1).toString());
			int sex = objList.get(2).toString().equals("男") ? 1 : 2;
			frontUser.setSex(sex);
			// 防止空指针异常
			if (objList.get(3) != null && !objList.get(3).toString().equals(""))
				frontUser.setPhone(objList.get(3).toString());
			
			if (objList.get(4) != null && !objList.get(4).toString().equals(""))
			frontUser.setStudentCode(objList.get(4).toString());
			
			if (objList.get(5) != null && !objList.get(5).toString().equals(""))
			frontUser.setStudentNo(objList.get(5).toString());
			
			if (objList.get(6) != null && !objList.get(6).toString().equals(""))
			frontUser.setStudentType(objList.get(6).toString());
			
			userList.add(frontUser);
		}
		
		String backe = this.batchSaveUsers(USER_TYPE_UNIFORM, userList, Long.parseLong(classId));
		
		return R.ok(backe).put("status", ResponseDTJson.SUCCESS);
	}*/
	
	/**
	 * 保存
	 */
	/*@ResponseBody
	@RequestMapping("/unifromSave")
	@RequiresPermissions("photofrontuser:save")
	public R unifromSave(@RequestBody PhotoFrontUserEntity photoFrontUser, @ModelAttribute("classId") String classId){
		
		ValidatorUtils.validateEntity(photoFrontUser, AddGroup.class);
		
		// 账号重复验证
		PhotoFrontUserEntity  pfue = new PhotoFrontUserEntity();
		pfue.setAccount(photoFrontUser.getAccount());
		EntityWrapper<PhotoFrontUserEntity> wrraper = new EntityWrapper<PhotoFrontUserEntity>(pfue);
		PhotoFrontUserEntity pfu = this.photoFrontUserService.selectOne(wrraper);
		
		if (pfu != null) {
			return R.error("账号不能重复");
		}
		// 号码重复验证
		if (photoFrontUser.getPhone() != null) {
			pfue = new PhotoFrontUserEntity();
			pfue.setAccount(null);
			pfue.setPhone(photoFrontUser.getPhone());
			
			wrraper = new EntityWrapper<PhotoFrontUserEntity>(pfue);
			
			pfu = this.photoFrontUserService.selectOne(wrraper);
			if (pfu != null) {
				return R.error("手机号码不能重复");
			}
		}
		
		//String phone = photoFrontUser.getPhone();
		//phone.Su
		//sha256加密
		String newPassword = new Sha256Hash(photoFrontUser.getAccount()).toHex();
		photoFrontUser.setPassword(newPassword);
		photoFrontUser.setGmtCreate(new Date());
		photoFrontUser.setGmtModified(new Date());
		photoFrontUser.setStatus(UserStatusEnum.STATUS_COMMON.getValue());
		String residence = photoFrontUser.getStudentType().equals("2") ? IN_RESIDENCE_2 : OUT_RESIDENCE_1;
		photoFrontUser.setStudentType(residence);
		
		photoFrontUserService.insert(photoFrontUser);
		
		DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
		// 同步到sqlserver 
		Student student = new Student();
		student.setId(photoFrontUser.getId());
		student.setClassId(Long.parseLong(classId));
		student.setStudentName(photoFrontUser.getNickname());
		String sex = photoFrontUser.getSex().equals(1) ? "男" : "女";
		student.setSex(sex);
		if (photoFrontUser.getStudentCode() != null && !photoFrontUser.getStudentCode().equals(""))
			student.setStudentCode(photoFrontUser.getStudentCode());
		if (photoFrontUser.getStudentNo() != null && !photoFrontUser.getStudentNo().equals(""))
			student.setStudentNo(photoFrontUser.getStudentNo());
		student.setStudentType(residence);
		this.studentService.insert(student);
		
		DbContextHolder.setDbType(DBTypeEnum.MYSQL);
		//  关联班级
		PhotoUserClassEntity puc = new PhotoUserClassEntity();
		
		puc.setClassId(Long.parseLong(classId));
		puc.setClassRoleType(photoFrontUser.getRoleId());
		puc.setFrontUserId(photoFrontUser.getId());
		puc.setStatus(UserClasssStatusEnum.STATUS_COMMON.getValue());
		puc.setGtmCreate(new Date());
		puc.setGtmModified(new Date());
		
		photoUserClassService.save(puc);
		
		//用户授权
		PhotoUserFunctionEntity pfePhoto = new PhotoUserFunctionEntity();
		
		PhotoFunctionModulesEntity pfme = new PhotoFunctionModulesEntity();
		pfme.setPerm(UserPermissionEnum.PERM_BASE_LOGIN.getValue());
		EntityWrapper<PhotoFunctionModulesEntity> wrapper = new EntityWrapper<PhotoFunctionModulesEntity>(pfme);
		pfme = photoFunctionModulesService.selectOne(wrapper);
		
		// 基本权限
		if (pfme != null ) {
			pfePhoto.setGmtCreate(new Date());
			pfePhoto.setGmtModified(new Date());
			pfePhoto.setUserId(photoFrontUser.getId());
			pfePhoto.setFunctionModulesId(pfme.getId().longValue());
			
			photoUserFunctionService.save(pfePhoto);
		}
		
		// 智能校服授权
		PhotoFunctionModulesEntity pfme2 = new PhotoFunctionModulesEntity();
		pfme2.setPerm(UserPermissionEnum.PERM_SMART_UNIFORM.getValue());
		EntityWrapper<PhotoFunctionModulesEntity> wrapper2 = new EntityWrapper<PhotoFunctionModulesEntity>(pfme2);
		pfme2 = photoFunctionModulesService.selectOne(wrapper2);
		
		if (pfme2 != null ) {
			pfePhoto.setId(null);
			pfePhoto.setGmtCreate(new Date());
			pfePhoto.setGmtModified(new Date());
			pfePhoto.setUserId(photoFrontUser.getId());
			pfePhoto.setFunctionModulesId(pfme2.getId().longValue());
			
			photoUserFunctionService.save(pfePhoto);
		}
		
		
		return R.ok();
	}*/
	
	/**
	 * 保存
	 */
	@ResponseBody
	@RequestMapping("/saveMoreRelation")
	@RequiresPermissions("photofrontuser:saveMoreRelation")
	public R saveMoreRelation(Long userId, Long classId, Integer roleId){
		// 验证用户
		PhotoFrontUserEntity user = this.photoFrontUserService.queryObject(userId);
		if (user == null) 
			return R.error("用户不存在").put("status", ResponseDTJson.FAIL);
		
		//验证班级
		PhotoClassEntity pc = this.photoClassService.queryObject(classId);
		if (pc == null)
			return R.error("班级不存在").put("status", ResponseDTJson.FAIL);
		
		PhotoUserClassEntity puce = new PhotoUserClassEntity();
		puce.setFrontUserId(userId);
		puce.setClassId(classId);
		EntityWrapper<PhotoUserClassEntity> wrapper = new EntityWrapper<PhotoUserClassEntity>(puce);
		List<PhotoUserClassEntity> puceList = this.photoUserClassService.selectList(wrapper);
		
		if ( puceList != null && puceList.size() >0)
			return R.error("请勿重复添加关系").put("status", ResponseDTJson.FAIL);
		
		//  关联班级
		PhotoUserClassEntity puc = new PhotoUserClassEntity();
		
		puc.setClassId(classId);
		puc.setClassRoleType(roleId);
		puc.setFrontUserId(userId);
		puc.setStatus(UserClasssStatusEnum.STATUS_COMMON.getValue());
		puc.setGtmCreate(new Date());
		puc.setGtmModified(new Date());
		
		photoUserClassService.save(puc);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("photofrontuser:update")
	public R update(@RequestBody PhotoFrontUserEntity photoFrontUser, @ModelAttribute("classId") Long classId){
		
		ValidatorUtils.validateEntity(photoFrontUser, AddGroup.class);
		
		photoFrontUserService.update(photoFrontUser);
		
		//  更新用户班级角色
		PhotoUserClassEntity puce = new PhotoUserClassEntity();
		puce.setClassId(classId);
		puce.setFrontUserId(photoFrontUser.getId());
		EntityWrapper<PhotoUserClassEntity> wrapper = new EntityWrapper<PhotoUserClassEntity>(puce);
		puce = this.photoUserClassService.selectOne(wrapper);
		puce.setClassRoleType(photoFrontUser.getRoleId());
		this.photoUserClassService.update(puce);
		
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("photofrontuser:delete")
	public R delete(@RequestBody Long[] ids){
		
		photoFrontUserService.deleteBatch(ids);
		
		/*EntityWrapper<PhotoUserClassEntity> wrapper = new EntityWrapper<PhotoUserClassEntity>();
		wrapper.in("front_user_id", ids);
		this.photoUserClassService.delete(wrapper);*/
		
		return R.ok();
	}
	
	/**
	 * 
	 * @param userType 用户类型
	 * @param userList 用户集合
	 * @param classId 班级id
	 * @return
	 */
	public String batchSaveUsers(int userType, List<PhotoFrontUserEntity> userList, Long classId){
		
		StringBuffer repeatName = new StringBuffer("已存在的账号：");
		if ( userList != null && userList.size() > 0) {
			// 存储有效用户
			List<PhotoFrontUserEntity> newUserList = new ArrayList<PhotoFrontUserEntity>(userList.size());
			// 存储有效用户，同步数据到sqlserver
			//List<Student> studentList = new ArrayList<Student>(userList.size());
			
			for(PhotoFrontUserEntity user : userList){
				// 进行账号重复查询
				PhotoFrontUserEntity  pfue = new PhotoFrontUserEntity();
				pfue.setAccount(user.getAccount());
				EntityWrapper<PhotoFrontUserEntity> wrraper = new EntityWrapper<PhotoFrontUserEntity>(pfue);
				PhotoFrontUserEntity pfu = this.photoFrontUserService.selectOne(wrraper);
				if (pfu != null){
					repeatName.append(user.getAccount());
					repeatName.append("(");
					repeatName.append(user.getNickname());
					repeatName.append(");");
				} else {
					// 添加到保存集合
					String newPassword = new Sha256Hash(user.getAccount()).toHex();
					user.setPassword(newPassword);
					user.setGmtCreate(new Date());
					user.setGmtModified(new Date());
					user.setStatus(UserStatusEnum.STATUS_COMMON.getValue());
					// 判断用户属于那个分类
					if ( userType == USER_TYPE_UNIFORM) {
						String residence = user.getStudentType().equals("2") ? IN_RESIDENCE_2 : OUT_RESIDENCE_1;
						user.setStudentType(residence);
					}
					
					newUserList.add(user);
					// 加入同步数据
					
				}
			} 
			// 如果所有账号都重复
			if (newUserList == null || newUserList.size() == 0)
				return repeatName.toString();
			
			// 用户本地保存
			this.photoFrontUserService.insertBatch(newUserList);
			
			// 只有智能校服的才同步到sqlserver数据库
			/*if (userType == USER_TYPE_UNIFORM) {
				Student student = null;
				for (PhotoFrontUserEntity user : newUserList){
					student = new Student();
					student.setId(user.getId());
					student.setClassId(classId);
					student.setStudentName(user.getNickname());
					String sex = user.getSex().equals(1) ? "男" : "女";
					student.setSex(sex);
					if (user.getStudentCode() != null && !user.getStudentCode().equals(""))
						student.setStudentCode(user.getStudentCode());
					if (user.getStudentNo() != null && !user.getStudentNo().equals(""))
						student.setStudentNo(user.getStudentNo());
					student.setStudentType(user.getStudentType());
					studentList.add(student);
				}
				// 切换数据库
				DbContextHolder.setDbType(DBTypeEnum.SQLSERVER);
				this.studentService.insertBatch(studentList);
				DbContextHolder.setDbType(DBTypeEnum.MYSQL);
			}*/
			
			//  关联班级
			DbContextHolder.setDbType(DBTypeEnum.MYSQL);
			PhotoUserClassEntity puc = null;
			List<PhotoUserClassEntity> pucList = new ArrayList<PhotoUserClassEntity>(newUserList.size());
			for ( PhotoFrontUserEntity user : newUserList ) {
				puc = new PhotoUserClassEntity();
				puc.setClassId(classId);
				int roleId = user.getRoleId() != null ? user.getRoleId() : ClassRoleEnum.CLASS_STUDENT.getTypeValue();
				puc.setClassRoleType(roleId);
				puc.setFrontUserId(user.getId());
				puc.setStatus(UserClasssStatusEnum.STATUS_COMMON.getValue());
				puc.setGtmCreate(new Date());
				puc.setGtmModified(new Date());
				pucList.add(puc);
			}
			if (pucList.size() >0 )
				this.photoUserClassService.insertBatch(pucList);
			
			// 基本权限授权,登录权限
			PhotoFunctionModulesEntity pfme = new PhotoFunctionModulesEntity();
			pfme.setPerm(UserPermissionEnum.PERM_BASE_LOGIN.getValue());
			EntityWrapper<PhotoFunctionModulesEntity> wrapper = new EntityWrapper<PhotoFunctionModulesEntity>(pfme);
			pfme = photoFunctionModulesService.selectOne(wrapper);
			List<PhotoUserFunctionEntity> functionList = new ArrayList<PhotoUserFunctionEntity>(newUserList.size());
			PhotoUserFunctionEntity pfePhoto = null;
			// 基本权限
			if (pfme != null ) {
				for ( PhotoFrontUserEntity user : newUserList ) {
					pfePhoto = new PhotoUserFunctionEntity();
					pfePhoto.setGmtCreate(new Date());
					pfePhoto.setGmtModified(new Date());
					pfePhoto.setUserId(user.getId());
					pfePhoto.setFunctionModulesId(pfme.getId().longValue());
					functionList.add(pfePhoto);
				}
				photoUserFunctionService.insertBatch(functionList);
			}
			
			// 其它权限授权
			PhotoFunctionModulesEntity pfme2 = new PhotoFunctionModulesEntity();
			List<PhotoUserFunctionEntity> functionList2 = new ArrayList<PhotoUserFunctionEntity>(newUserList.size());
			if (userType == USER_TYPE_PHOTO) {
				pfme2.setPerm(UserPermissionEnum.PERM_GRADUATION_PHOTO.getValue());
			} else if (userType == USER_TYPE_UNIFORM) {
				pfme2.setPerm(UserPermissionEnum.PERM_SMART_UNIFORM.getValue());
			}
			EntityWrapper<PhotoFunctionModulesEntity> wrapper2 = new EntityWrapper<PhotoFunctionModulesEntity>(pfme2);
			pfme2 = photoFunctionModulesService.selectOne(wrapper2);
			
			if (pfme2 != null ) {
				for ( PhotoFrontUserEntity user : newUserList ) {
					pfePhoto = new PhotoUserFunctionEntity();
					pfePhoto.setId(null);
					pfePhoto.setGmtCreate(new Date());
					pfePhoto.setGmtModified(new Date());
					pfePhoto.setUserId(user.getId());
					pfePhoto.setFunctionModulesId(pfme2.getId().longValue());
					functionList2.add(pfePhoto);
				}
				
				photoUserFunctionService.insertBatch(functionList2);
			}
			
		} 
			
		return repeatName.toString();
	}
}
