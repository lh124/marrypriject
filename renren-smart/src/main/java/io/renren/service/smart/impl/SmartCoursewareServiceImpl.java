package io.renren.service.smart.impl;

import io.renren.dao.smart.SmartCoursewareDao;
import io.renren.entity.smart.SmartCoursewareEntity;
import io.renren.service.smart.SmartCoursewareService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("smartCoursewareService")
public class SmartCoursewareServiceImpl extends ServiceImpl<SmartCoursewareDao, SmartCoursewareEntity>  implements SmartCoursewareService {
	@Autowired
	private SmartCoursewareDao smartCoursewareDao;
	
	@Override
	public SmartCoursewareEntity queryObject(Integer id){
		return smartCoursewareDao.queryObject(id);
	}
	
	@Override
	public List<SmartCoursewareEntity> queryList(Map<String, Object> map){
		return smartCoursewareDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smartCoursewareDao.queryTotal(map);
	}
	
	@Override
	public void save(SmartCoursewareEntity smartCourseware){
		smartCoursewareDao.save(smartCourseware);
	}
	
	@Override
	public void update(SmartCoursewareEntity smartCourseware){
		smartCoursewareDao.update(smartCourseware);
	}
	
	@Override
	public void delete(Integer id){
		smartCoursewareDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		smartCoursewareDao.deleteBatch(ids);
	}
	
}
