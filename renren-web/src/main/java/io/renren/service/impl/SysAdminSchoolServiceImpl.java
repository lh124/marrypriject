package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.SysAdminSchoolDao;
import io.renren.entity.SysAdminSchoolEntity;
import io.renren.service.SysAdminSchoolService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("sysAdminSchoolService")
public class SysAdminSchoolServiceImpl extends ServiceImpl<SysAdminSchoolDao, SysAdminSchoolEntity>  implements SysAdminSchoolService {
	@Autowired
	private SysAdminSchoolDao sysAdminSchoolDao;
	
	@Override
	public SysAdminSchoolEntity queryObject(Long id){
		return sysAdminSchoolDao.queryObject(id);
	}
	
	@Override
	public List<SysAdminSchoolEntity> queryList(Map<String, Object> map){
		return sysAdminSchoolDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysAdminSchoolDao.queryTotal(map);
	}
	
	@Override
	public void save(SysAdminSchoolEntity sysAdminSchool){
		sysAdminSchoolDao.save(sysAdminSchool);
	}
	
	@Override
	public void update(SysAdminSchoolEntity sysAdminSchool){
		sysAdminSchoolDao.update(sysAdminSchool);
	}
	
	@Override
	public void delete(Long id){
		sysAdminSchoolDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysAdminSchoolDao.deleteBatch(ids);
	}
	
}
