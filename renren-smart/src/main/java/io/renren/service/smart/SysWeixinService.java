package io.renren.service.smart;

import io.renren.entity.smart.SysWeixinEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-09-27 09:40:45
 */
public interface SysWeixinService extends IService<SysWeixinEntity>{
	
	SysWeixinEntity queryObject(Integer id);
	
	List<SysWeixinEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysWeixinEntity sysWeixin);
	
	void update(SysWeixinEntity sysWeixin);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
