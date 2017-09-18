package io.renren.service.smart;

import io.renren.entity.smart.StudentEpcEntity;

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
public interface StudentEpcService extends IService<StudentEpcEntity>{
	
	StudentEpcEntity queryObject(Integer id);
	
	List<StudentEpcEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(StudentEpcEntity studentEpc);
	
	void update(StudentEpcEntity studentEpc);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
