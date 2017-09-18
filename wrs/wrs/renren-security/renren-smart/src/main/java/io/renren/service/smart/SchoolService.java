package io.renren.service.smart;

import io.renren.entity.smart.SchoolEntity;

import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-08-28 12:02:26
 */
public interface SchoolService extends IService<SchoolEntity>{
	
	SchoolEntity queryObject(Integer id);
	
	List<SchoolEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SchoolEntity school);
	
	void update(SchoolEntity school);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
