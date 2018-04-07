 
package example.model.dataobject;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;

 
public class Examination implements Serializable{


	//========== properties ==========
	
    //
    private Integer id;
    //
    private String name;
    //总题量
    private Integer totalSize;
    //单选题量
    private Integer singleChoiceSize;
    //多选题量
    private Integer multChoiceSize;
    //判断题量
    private Integer judgeSize;
    //简答题量
    private Integer saqSize;
    //总分,只能为100,120,150
    private Double totalScore;
    //判断题分值
    private Double judgeScore;
    //单选分值,总分必须为100
    private Double singleScore;
    //多选分值,总分必须为100
    private Double multScore;
    //简答分值不固定,这里保存简答题总分
    private Double saqScore;
    //试卷应有多少道判断
    private Integer judgeUse;
    //试卷应有多少道单选
    private Integer singleUse;
    //试卷应有多少道多选
    private Integer multUse;
    //试卷应有多少道简答
    private Integer saqUse;
    //该测试包含多少章节
    private Integer chapterSize;
    //
    private Integer isDelete;
    //每个章节最少多少分
    private Double chapterMinScore;
    //每个章节最多多少分
    private Double chapterMaxScore;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    private String introduce;

    private Double price;

    private Integer examTime;
    //-------------额外字段
    private Integer charged;

    private Date chargeFinalTime;

    private UserExamination userExamination;
    //========== getters and setters ==========
	
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getTotalSize() {
        return totalSize;
    }
    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }
    public Integer getSingleChoiceSize() {
        return singleChoiceSize;
    }
    public void setSingleChoiceSize(Integer singleChoiceSize) {
        this.singleChoiceSize = singleChoiceSize;
    }
    public Integer getMultChoiceSize() {
        return multChoiceSize;
    }
    public void setMultChoiceSize(Integer multChoiceSize) {
        this.multChoiceSize = multChoiceSize;
    }
    public Integer getJudgeSize() {
        return judgeSize;
    }
    public void setJudgeSize(Integer judgeSize) {
        this.judgeSize = judgeSize;
    }
    public Integer getSaqSize() {
        return saqSize;
    }
    public void setSaqSize(Integer saqSize) {
        this.saqSize = saqSize;
    }
    public Double getTotalScore() {
        return totalScore;
    }
    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }
    public Double getJudgeScore() {
        return judgeScore;
    }
    public void setJudgeScore(Double judgeScore) {
        this.judgeScore = judgeScore;
    }
    public Double getSingleScore() {
        return singleScore;
    }
    public void setSingleScore(Double singleScore) {
        this.singleScore = singleScore;
    }
    public Double getMultScore() {
        return multScore;
    }
    public void setMultScore(Double multScore) {
        this.multScore = multScore;
    }
    public Double getSaqScore() {
        return saqScore;
    }
    public void setSaqScore(Double saqScore) {
        this.saqScore = saqScore;
    }
    public Integer getJudgeUse() {
        return judgeUse;
    }
    public void setJudgeUse(Integer judgeUse) {
        this.judgeUse = judgeUse;
    }
    public Integer getSingleUse() {
        return singleUse;
    }
    public void setSingleUse(Integer singleUse) {
        this.singleUse = singleUse;
    }
    public Integer getMultUse() {
        return multUse;
    }
    public void setMultUse(Integer multUse) {
        this.multUse = multUse;
    }
    public Integer getSaqUse() {
        return saqUse;
    }
    public void setSaqUse(Integer saqUse) {
        this.saqUse = saqUse;
    }
    public Integer getChapterSize() {
        return chapterSize;
    }
    public void setChapterSize(Integer chapterSize) {
        this.chapterSize = chapterSize;
    }
    public Integer getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
    public Double getChapterMinScore() {
        return chapterMinScore;
    }
    public void setChapterMinScore(Double chapterMinScore) {
        this.chapterMinScore = chapterMinScore;
    }
    public Double getChapterMaxScore() {
        return chapterMaxScore;
    }
    public void setChapterMaxScore(Double chapterMaxScore) {
        this.chapterMaxScore = chapterMaxScore;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	
	/**
	* 重载toString方法
	* @return
	*
	* @see Object#toString()
	*/
	@Override
    public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Date getChargeFinalTime() {
        return chargeFinalTime;
    }

    public void setChargeFinalTime(Date chargeFinalTime) {
        this.chargeFinalTime = chargeFinalTime;
    }

    public Integer getCharged() {
        return charged;
    }

    public void setCharged(Integer charged) {
        this.charged = charged;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getExamTime() {
        return examTime;
    }

    public void setExamTime(Integer examTime) {
        this.examTime = examTime;
    }

    public UserExamination getUserExamination() {
        return userExamination;
    }

    public void setUserExamination(UserExamination userExamination) {
        this.userExamination = userExamination;
    }
}
