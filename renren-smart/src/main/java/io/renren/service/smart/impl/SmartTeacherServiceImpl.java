package io.renren.service.smart.impl;

import io.renren.dao.smart.SmartTeacherDao;
import io.renren.entity.smart.SmartTeacherEntity;
import io.renren.service.smart.SmartTeacherService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("smartTeacherService")
public class SmartTeacherServiceImpl extends ServiceImpl<SmartTeacherDao, SmartTeacherEntity>  implements SmartTeacherService {
	@Autowired
	private SmartTeacherDao smartTeacherDao;
	
	@Override
	public SmartTeacherEntity queryObject(Integer id){
		return smartTeacherDao.queryObject(id);
	}
	
	@Override
	public List<SmartTeacherEntity> queryList(Map<String, Object> map){
		return smartTeacherDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smartTeacherDao.queryTotal(map);
	}
	
	@Override
	public void save(SmartTeacherEntity smartTeacher){
		smartTeacherDao.save(smartTeacher);
	}
	
	@Override
	public void update(SmartTeacherEntity smartTeacher){
		smartTeacherDao.update(smartTeacher);
	}
	
	@Override
	public void delete(Integer id){
		smartTeacherDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		smartTeacherDao.deleteBatch(ids);
	}
	
}
