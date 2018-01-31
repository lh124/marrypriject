package io.renren.service.smart.impl;

import io.renren.dao.smart.SmartGradeDao;
import io.renren.entity.smart.SmartGradeEntity;
import io.renren.service.smart.SmartGradeService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("smartGradeService")
public class SmartGradeServiceImpl extends ServiceImpl<SmartGradeDao, SmartGradeEntity>  implements SmartGradeService {
	@Autowired
	private SmartGradeDao smartGradeDao;
	
	@Override
	public SmartGradeEntity queryObject(Integer id){
		return smartGradeDao.queryObject(id);
	}
	
	@Override
	public List<SmartGradeEntity> queryList(Map<String, Object> map){
		return smartGradeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smartGradeDao.queryTotal(map);
	}
	
	@Override
	public void save(SmartGradeEntity smartGrade){
		smartGradeDao.save(smartGrade);
	}
	
	@Override
	public void update(SmartGradeEntity smartGrade){
		smartGradeDao.update(smartGrade);
	}
	
	@Override
	public void delete(Integer id){
		smartGradeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		smartGradeDao.deleteBatch(ids);
	}
	
}
