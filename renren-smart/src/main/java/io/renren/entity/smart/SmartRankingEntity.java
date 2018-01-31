package io.renren.entity.smart;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 
 * 
 * @author yuanbichang
 * @email 673290684@qq.com
 * @date 2018-01-27 14:35:12
 */
 @TableName("smart_ranking")
public class SmartRankingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		//
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;	
		
	//学生名字
	@TableField(value="name")
	private String name;
	
	@TableField(value="pic")
	private String pic;
			
	//用户id
	@TableField(value="user_id")
	private Integer userId;
			
	//总分
	@TableField(value="fraction_total")
	private String fractionTotal;
			
	//考试科目总数
	@TableField(value="subject_total")
	private Integer subjectTotal;
	
	//上一次考试成绩总分
	@TableField(value="fraction_total_last")
	private Integer fractionTotalLast;
			
	//平均分
	@TableField(value="fraction_average")
	private Double fractionAverage;
			
	//年级排名
	@TableField(value="grade_ranking")
	private Integer gradeRanking;
	
	@TableField(value="grade_ranking_last")
	private Integer gradeRankingLast;
			
	//班级排名
	@TableField(value="class_ranking")
	private Integer classRanking;
	
	@TableField(value="class_ranking_last")
	private Integer classRankingLast;
	
	//班级id
	@TableField(value="class_id")
	private Integer classId;
	
	//考试主题id
	@TableField(value="examination_id")
	private Integer examinationId;

	public Integer getFractionTotalLast() {
		return fractionTotalLast;
	}
	public void setFractionTotalLast(Integer fractionTotalLast) {
		this.fractionTotalLast = fractionTotalLast;
	}
	public Integer getGradeRankingLast() {
		return gradeRankingLast;
	}
	public void setGradeRankingLast(Integer gradeRankingLast) {
		this.gradeRankingLast = gradeRankingLast;
	}
	public Integer getClassRankingLast() {
		return classRankingLast;
	}
	public void setClassRankingLast(Integer classRankingLast) {
		this.classRankingLast = classRankingLast;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public Integer getExaminationId() {
		return examinationId;
	}
	public void setExaminationId(Integer examinationId) {
		this.examinationId = examinationId;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	/**class_id
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：学生名字
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：学生名字
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：用户id
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * 设置：总分
	 */
	public void setFractionTotal(String fractionTotal) {
		this.fractionTotal = fractionTotal;
	}
	/**
	 * 获取：总分
	 */
	public String getFractionTotal() {
		return fractionTotal;
	}
	/**
	 * 设置：考试科目总数
	 */
	public void setSubjectTotal(Integer subjectTotal) {
		this.subjectTotal = subjectTotal;
	}
	/**
	 * 获取：考试科目总数
	 */
	public Integer getSubjectTotal() {
		return subjectTotal;
	}
	/**
	 * 设置：平均分
	 */
	public void setFractionAverage(Double fractionAverage) {
		this.fractionAverage = fractionAverage;
	}
	/**
	 * 获取：平均分
	 */
	public Double getFractionAverage() {
		return fractionAverage;
	}
	/**
	 * 设置：年级排名
	 */
	public void setGradeRanking(Integer gradeRanking) {
		this.gradeRanking = gradeRanking;
	}
	/**
	 * 获取：年级排名
	 */
	public Integer getGradeRanking() {
		return gradeRanking;
	}
	/**
	 * 设置：班级排名
	 */
	public void setClassRanking(Integer classRanking) {
		this.classRanking = classRanking;
	}
	/**
	 * 获取：班级排名
	 */
	public Integer getClassRanking() {
		return classRanking;
	}
}
