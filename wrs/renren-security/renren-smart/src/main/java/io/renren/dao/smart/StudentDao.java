package io.renren.dao.smart;

import org.apache.ibatis.annotations.Param;

import io.renren.dao.BaseDao;
import io.renren.entity.smart.StudentEntity;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2017-08-28 12:02:26
 */
public interface StudentDao extends BaseDao<StudentEntity>,BaseMapper<StudentEntity> {
	
	public void updateOpenId(@Param("id") Long id);
}
