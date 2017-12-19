package io.renren.service.married;

import io.renren.entity.married.MarriedUserEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-12-01 13:50:42
 */
public interface MarriedUserService extends IService<MarriedUserEntity>{
	
	MarriedUserEntity queryObject(Integer id);
	
	List<MarriedUserEntity> queryList(Map<String, Object> map);
	
	List<MarriedUserEntity> queryListtongji(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MarriedUserEntity marriedUser);
	
	void update(MarriedUserEntity marriedUser);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
