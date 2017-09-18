package io.renren.service;

import io.renren.entity.SysAdminSchoolEntity;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-27 22:23:54
 */
public interface SysAdminSchoolService extends IService<SysAdminSchoolEntity>{
	
	SysAdminSchoolEntity queryObject(Long id);
	
	List<SysAdminSchoolEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysAdminSchoolEntity sysAdminSchool);
	
	void update(SysAdminSchoolEntity sysAdminSchool);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
