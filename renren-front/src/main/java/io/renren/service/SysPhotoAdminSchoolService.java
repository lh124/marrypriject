package io.renren.service;

import io.renren.entity.SysPhotoAdminSchoolEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-10-26 14:38:48
 */
public interface SysPhotoAdminSchoolService extends IService<SysPhotoAdminSchoolEntity>{
	
	SysPhotoAdminSchoolEntity queryObject(Integer id);
	
	List<SysPhotoAdminSchoolEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysPhotoAdminSchoolEntity sysPhotoAdminSchool);
	
	void update(SysPhotoAdminSchoolEntity sysPhotoAdminSchool);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
