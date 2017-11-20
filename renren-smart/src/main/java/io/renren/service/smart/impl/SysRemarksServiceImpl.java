package io.renren.service.smart.impl;

import io.renren.dao.smart.SysRemarksDao;
import io.renren.entity.smart.SysRemarksEntity;
import io.renren.service.smart.SysRemarksService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("sysRemarksService")
public class SysRemarksServiceImpl extends ServiceImpl<SysRemarksDao, SysRemarksEntity>  implements SysRemarksService {
	@Autowired
	private SysRemarksDao sysRemarksDao;
	
	@Override
	public SysRemarksEntity queryObject(Integer id){
		return sysRemarksDao.queryObject(id);
	}
	
	@Override
	public List<SysRemarksEntity> queryList(Map<String, Object> map){
		return sysRemarksDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysRemarksDao.queryTotal(map);
	}
	
	@Override
	public void save(SysRemarksEntity sysRemarks){
		sysRemarksDao.save(sysRemarks);
	}
	
	@Override
	public void update(SysRemarksEntity sysRemarks){
		sysRemarksDao.update(sysRemarks);
	}
	
	@Override
	public void delete(Integer id){
		sysRemarksDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		sysRemarksDao.deleteBatch(ids);
	}
	
}
