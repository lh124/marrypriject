package io.renren.service.smart;

import io.renren.entity.smart.StudentEntity;

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
public interface StudentService extends IService<StudentEntity>{
	
	StudentEntity queryObject(Integer id);
	
	List<StudentEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(StudentEntity student);
	
	void update(StudentEntity student);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
	
	void updateOpenId(Long id);
}
