package io.renren.controller.photo;

import io.renren.annotation.CheckAuth;
import io.renren.annotation.IgnoreAuth;
import io.renren.constant.ControllerConstant;
import io.renren.entity.PhotoClassMsglEntity;
import io.renren.entity.PhotoFrontUserEntity;
import io.renren.model.json.ResponseDTJson;
import io.renren.service.PhotoClassMsglService;
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
@RequestMapping("front/photoclassmsgl")
public class PhotoClassMsglController {
	@Autowired
	private PhotoClassMsglService photoClassMsglService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@IgnoreAuth
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<PhotoClassMsglEntity> photoClassMsglList = photoClassMsglService.queryList(query);
		int total = photoClassMsglService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(photoClassMsglList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil).put("url", PostObjectPolicyController.CDN_URL);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("photoclassmsgl:info")
	public R info(@PathVariable("id") Long id){
		PhotoClassMsglEntity photoClassMsgl = photoClassMsglService.queryObject(id);
		
		return R.ok().put("photoClassMsgl", photoClassMsgl);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@CheckAuth(needAuth="graduate:all")
	public R save(PhotoClassMsglEntity photoClassMsgl, HttpServletRequest request){
		ValidatorUtils.validateEntity(photoClassMsgl, AddGroup.class);
		
		// 验证是否已经发布
		PhotoFrontUserEntity user = (PhotoFrontUserEntity) request.getSession().getAttribute(ControllerConstant.USER_SESSION_KEY);
		PhotoClassMsglEntity pcme = new PhotoClassMsglEntity();
		pcme.setClassId(photoClassMsgl.getClassId());
		pcme.setUserId(user.getId());
		
		EntityWrapper<PhotoClassMsglEntity> wrapper = new EntityWrapper<PhotoClassMsglEntity>(pcme);
		pcme = this.photoClassMsglService.selectOne(wrapper);
		
		if (pcme != null )
			return R.error("已经发布班级留言").put("status", ResponseDTJson.FAIL);
		
		// 构造对象
		photoClassMsgl.setGmtCreate(new Date());
		photoClassMsgl.setGmtModified(new Date());
		photoClassMsgl.setUserId(user.getId());
		photoClassMsglService.save(photoClassMsgl);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("photoclassmsgl:update")
	public R update(@RequestBody PhotoClassMsglEntity photoClassMsgl){
		photoClassMsglService.update(photoClassMsgl);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("photoclassmsgl:delete")
	public R delete(@RequestBody Long[] ids){
		photoClassMsglService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
