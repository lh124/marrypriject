package io.renren.service.smart;

import io.renren.entity.smart.IoEntity;

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
public interface IoService extends IService<IoEntity>{
	
	IoEntity queryObject(Integer id);
	
	List<IoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(IoEntity io);
	
	void update(IoEntity io);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
