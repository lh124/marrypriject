package io.renren.service.smart.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.smart.ClassDao;
import io.renren.entity.smart.ClassEntity;
import io.renren.service.smart.ClassService;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("classService")
public class ClassServiceImpl extends ServiceImpl<ClassDao, ClassEntity>  implements ClassService {
	@Autowired
	private ClassDao classDao;
	
	@Override
	public ClassEntity queryObject(Integer id){
		return classDao.queryObject(id);
	}
	
	@Override
	public List<ClassEntity> queryList(Map<String, Object> map){
		return classDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return classDao.queryTotal(map);
	}
	
	@Override
	public void save(ClassEntity classe){
		classDao.save(classe);
	}
	
	@Override
	public void update(ClassEntity classe){
		classDao.update(classe);
	}
	
	@Override
	public void delete(Integer id){
		classDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		classDao.deleteBatch(ids);
	}
	
}
