package io.renren.service.smart.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.smart.SchoolDao;
import io.renren.entity.smart.SchoolEntity;
import io.renren.service.smart.SchoolService;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("schoolService")
public class SchoolServiceImpl extends ServiceImpl<SchoolDao, SchoolEntity>  implements SchoolService {
	@Autowired
	private SchoolDao schoolDao;
	
	@Override
	public SchoolEntity queryObject(Integer id){
		return schoolDao.queryObject(id);
	}
	 
	@Override
	public List<SchoolEntity> queryList(Map<String, Object> map){
		return schoolDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return schoolDao.queryTotal(map);
	}
	
	@Override
	public void save(SchoolEntity school){
		schoolDao.save(school);
	}
	
	@Override
	public void update(SchoolEntity school){
		schoolDao.update(school);
	}
	
	@Override
	public void delete(Integer id){
		schoolDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		schoolDao.deleteBatch(ids);
	}

	@Override
	public SchoolEntity queryObjectName(String schoolName) {
		return schoolDao.queryObjectName(schoolName);
	}
	
}
