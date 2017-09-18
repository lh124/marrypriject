package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.renren.dao.PhotoMemberRoleDao;
import io.renren.entity.PhotoMemberRoleEntity;
import io.renren.service.PhotoMemberRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("photoMemberRoleService")
public class PhotoMemberRoleServiceImpl extends ServiceImpl<PhotoMemberRoleDao, PhotoMemberRoleEntity>  implements PhotoMemberRoleService {
	@Autowired
	private PhotoMemberRoleDao photoMemberRoleDao;
	
	@Override
	public PhotoMemberRoleEntity queryObject(Integer id){
		return photoMemberRoleDao.queryObject(id);
	}
	
	@Override
	public List<PhotoMemberRoleEntity> queryList(Map<String, Object> map){
		return photoMemberRoleDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return photoMemberRoleDao.queryTotal(map);
	}
	
	@Override
	public void save(PhotoMemberRoleEntity photoMemberRole){
		photoMemberRoleDao.save(photoMemberRole);
	}
	
	@Override
	public void update(PhotoMemberRoleEntity photoMemberRole){
		photoMemberRoleDao.update(photoMemberRole);
	}
	
	@Override
	public void delete(Integer id){
		photoMemberRoleDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		photoMemberRoleDao.deleteBatch(ids);
	}
	
}
