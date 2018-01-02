package io.renren.service.married;

import io.renren.entity.married.MarryImgEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-12-28 09:15:04
 */
public interface MarryImgService extends IService<MarryImgEntity>{
	
	MarryImgEntity queryObject(Integer id);
	
	List<MarryImgEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MarryImgEntity marryImg);
	
	void update(MarryImgEntity marryImg);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
