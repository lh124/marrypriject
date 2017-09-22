package io.renren.service.smart.impl;

import io.renren.dao.smart.PsychologicalCounselingDao;
import io.renren.entity.smart.PsychologicalCounselingEntity;
import io.renren.service.smart.PsychologicalCounselingService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("psychologicalCounselingService")
public class PsychologicalCounselingServiceImpl extends ServiceImpl<PsychologicalCounselingDao, PsychologicalCounselingEntity>  implements PsychologicalCounselingService {
	@Autowired
	private PsychologicalCounselingDao psychologicalCounselingDao;
	
	@Override
	public PsychologicalCounselingEntity queryObject(Integer id){
		return psychologicalCounselingDao.queryObject(id);
	}
	
	@Override
	public List<PsychologicalCounselingEntity> queryList(Map<String, Object> map){
		return psychologicalCounselingDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return psychologicalCounselingDao.queryTotal(map);
	}
	
	@Override
	public void save(PsychologicalCounselingEntity psychologicalCounseling){
		psychologicalCounselingDao.save(psychologicalCounseling);
	}
	
	@Override
	public void update(PsychologicalCounselingEntity psychologicalCounseling){
		psychologicalCounselingDao.update(psychologicalCounseling);
	}
	
	@Override
	public void delete(Integer id){
		psychologicalCounselingDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		psychologicalCounselingDao.deleteBatch(ids);
	}
	
}
