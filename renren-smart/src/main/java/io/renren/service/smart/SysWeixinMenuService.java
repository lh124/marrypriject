package io.renren.service.smart;

import io.renren.entity.smart.SysWeixinMenuEntity;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-09-27 10:06:01
 */
public interface SysWeixinMenuService extends IService<SysWeixinMenuEntity>{
	
	SysWeixinMenuEntity queryObject(Integer id);
	
	List<SysWeixinMenuEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysWeixinMenuEntity sysWeixinMenu);
	
	void update(SysWeixinMenuEntity sysWeixinMenu);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
