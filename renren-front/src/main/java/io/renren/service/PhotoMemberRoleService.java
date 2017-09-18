package io.renren.service;

import io.renren.entity.PhotoMemberRoleEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-04 10:25:52
 */
public interface PhotoMemberRoleService extends IService<PhotoMemberRoleEntity>{
	
	PhotoMemberRoleEntity queryObject(Integer id);
	
	List<PhotoMemberRoleEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PhotoMemberRoleEntity photoMemberRole);
	
	void update(PhotoMemberRoleEntity photoMemberRole);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
