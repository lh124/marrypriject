package io.renren.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import io.renren.entity.PhotoGraduationTimeEntity;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-05-06 11:27:27
 */
public interface PhotoGraduationTimeDao extends BaseDao<PhotoGraduationTimeEntity>,BaseMapper<PhotoGraduationTimeEntity> {
	
	/**
	 * 查询毕业年限
	 * @param status
	 * @param schoolId
	 * @param collegeId
	 * @param classify
	 * @return
	 */
	List<PhotoGraduationTimeEntity> queryGraduationClass(@Param("status") Integer status, @Param("schoolId") Long schoolId, @Param("collegeId") Long collegeId, @Param("classify") Integer classify);
}
