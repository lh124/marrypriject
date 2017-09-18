package io.renren.controller.photo;

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
import com.baomidou.mybatisplus.mapper.Wrapper;

import io.renren.annotation.CheckAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.PhotoFrontUserEntity;
import io.renren.entity.PhotoUserClassEntity;
import io.renren.enums.ClassRoleEnum;
import io.renren.model.json.ResponseDTJson;
import io.renren.service.PhotoFrontUserService;
import io.renren.service.PhotoUserClassService;
import io.renren.utils.MD5;
import io.renren.utils.PageUtils;
import io.renren.utils.Query;
import io.renren.utils.R;
import io.renren.utils.RRException;
import io.renren.validator.Assert;


/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 15:07:49
 */
@RestController("frontPhotofrontuser")
@RequestMapping("front/Photofrontuser")
public class PhotoFrontUserController {
	@Autowired
	private PhotoFrontUserService photoFrontUserService;
	
	@Autowired
	private PhotoUserClassService photoUserClassService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("photofrontuser:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoFrontUserEntity> photoFrontUserList = photoFrontUserService.queryList(query);
		int total = photoFrontUserService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoFrontUserList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	/**
	 * 列表
	 */
	@RequestMapping("/userFunction")
	@CheckAuth(needAuth = "user:logined")
	public R userFunctions(HttpServletRequest request, HttpSession session){
		
		// 相册用户
		reflashUserSession(request, this.photoFrontUserService);
		PhotoFrontUserEntity user = (PhotoFrontUserEntity)request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
		user.setPassword("");
		
		// 查询用户是否有管理班级的权限
		PhotoUserClassEntity userClass = new PhotoUserClassEntity();
		userClass.setFrontUserId(user.getId());
		userClass.setClassRoleType(ClassRoleEnum.CLASS_ADMIN.getTypeValue());
		
		EntityWrapper<PhotoUserClassEntity> wrapper = new EntityWrapper<PhotoUserClassEntity>(userClass);
		int count = this.photoUserClassService.selectCount(wrapper);
		
		return R.ok().put("user", user).put("admin", count);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photofrontuser:info")
	public R info(@PathVariable("id") Long id){
		PhotoFrontUserEntity photoFrontUser = photoFrontUserService.queryObject(id);
		
		return R.ok().put("photoFrontUser", photoFrontUser);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@CheckAuth(needAuth="user:forbidden")
	public R save(@RequestBody PhotoFrontUserEntity photoFrontUser){
		photoFrontUserService.save(photoFrontUser);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@CheckAuth(needAuth="user:logined")
	public R update(HttpServletRequest request,String nickname){
		
		Assert.isBlank(nickname, "昵称不能为空");
		PhotoFrontUserEntity user = (PhotoFrontUserEntity)request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
		PhotoFrontUserEntity userNew = new PhotoFrontUserEntity();
		userNew.setId(user.getId());
		userNew.setNickname(nickname);
		
		photoFrontUserService.update(userNew);
		
		// 刷新session
		reflashUserSession(request, photoFrontUserService);
		
		return R.ok();
	}
	/**
	 * 修改密码
	 */
	@RequestMapping("/updatePassword")
	@CheckAuth(needAuth="user:logined")
	public R updatePassword(HttpServletRequest request,String oldPassword, String newPassword){
		Assert.isBlank(oldPassword, "原密码不能为空");
		Assert.isBlank(newPassword, "新密码不能为空");
		
		int length = newPassword.length();
		
		if ( length < 6 || length > 12 )
			return R.error("新密码长度错误,应为6~12位").put("status", ResponseDTJson.FAIL);
		
		PhotoFrontUserEntity user = (PhotoFrontUserEntity)request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
		user = this.photoFrontUserService.queryObject(user.getId());
		
		if (user.getPassword().equals(new Sha256Hash(oldPassword).toHex()) || 
        		user.getPassword().equals(MD5.md5(oldPassword)) ) {
			
			PhotoFrontUserEntity userNew = new PhotoFrontUserEntity();
			userNew.setId(user.getId());
			userNew.setPassword(new Sha256Hash(newPassword).toHex());
			
			photoFrontUserService.update(userNew);
			
			// 刷新session
			reflashUserSession(request, photoFrontUserService);
			
			return R.ok().put("status", ResponseDTJson.SUCCESS);
		} else {
			return R.error("原密码错误").put("status", ResponseDTJson.FAIL);
		}
		
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/cancelWechatBinding")
	@CheckAuth(needAuth="user:logined")
	public R cancelWechatBinding(HttpServletRequest request){
		
		PhotoFrontUserEntity user = (PhotoFrontUserEntity)request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
		PhotoFrontUserEntity userNew = new PhotoFrontUserEntity();
		userNew.setId(user.getId());
		userNew.setOpenid("");
		
		photoFrontUserService.update(userNew);
		
		// 刷新session
		reflashUserSession(request, photoFrontUserService);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photofrontuser:delete")
	public R delete(@RequestBody Long[] ids){
		photoFrontUserService.deleteBatch(ids);
		
		return R.ok();
	}
	
	public void reflashUserSession(HttpServletRequest request, PhotoFrontUserService photoFrontUserService){
		
		PhotoFrontUserEntity user = (PhotoFrontUserEntity)request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
		user = photoFrontUserService.queryByAccount(user.getAccount());
		
		request.getSession().setAttribute(ControllerConstant.USER_SESSION_KEY,user);
	}
	
}
