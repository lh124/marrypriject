package io.renren.service.married;

import io.renren.entity.married.MarryBlessingEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-12-28 15:08:23
 */
public interface MarryBlessingService extends IService<MarryBlessingEntity>{
	
	MarryBlessingEntity queryObject(Integer id);
	
	List<MarryBlessingEntity> queryList(Map<String, Object> map);
	
	List<MarryBlessingEntity> queryList1(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MarryBlessingEntity marryBlessing);
	
	void update(MarryBlessingEntity marryBlessing);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
