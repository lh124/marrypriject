package io.renren.service.smart.impl;

import io.renren.dao.smart.ClassInfoDao;
import io.renren.entity.smart.ClassInfoEntity;
import io.renren.service.smart.ClassInfoService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("classInfoService")
public class ClassInfoServiceImpl extends ServiceImpl<ClassInfoDao, ClassInfoEntity>  implements ClassInfoService {
	@Autowired
	private ClassInfoDao classInfoDao;
	
	@Override
	public ClassInfoEntity queryObject(Integer id){
		return classInfoDao.queryObject(id);
	}
	
	@Override
	public List<ClassInfoEntity> queryList(Map<String, Object> map){
		return classInfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return classInfoDao.queryTotal(map);
	}
	
	@Override
	public void save(ClassInfoEntity classInfo){
		classInfoDao.save(classInfo);
	}
	
	@Override
	public void update(ClassInfoEntity classInfo){
		classInfoDao.update(classInfo);
	}
	
	@Override
	public void delete(Integer id){
		classInfoDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		classInfoDao.deleteBatch(ids);
	}

	@Override
	public List<ClassInfoEntity> queryListtongji(Map<String, Object> map) {
		return classInfoDao.queryListtongji(map);
	}
	
}
