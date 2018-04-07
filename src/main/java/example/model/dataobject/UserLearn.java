 
package example.model.dataobject;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;

 
public class UserLearn implements Serializable{


	//========== properties ==========
	
    //
    private Integer id;
    //
    private Integer userId;
    //
    private Integer calligrapyId;
    //表示已学习到第几张,默认0
    private Integer learnProgress;
    //历史分值,目前没想好怎么做
    private String historyScore;
    //平均分
    private Integer averageScore;
    //
    private Date createTime;
    //
    private Date updateTime;
    //
    private Integer highestScore;

    //========== getters and setters ==========
	
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getCalligrapyId() {
        return calligrapyId;
    }
    public void setCalligrapyId(Integer calligrapyId) {
        this.calligrapyId = calligrapyId;
    }
    public Integer getLearnProgress() {
        return learnProgress;
    }
    public void setLearnProgress(Integer learnProgress) {
        this.learnProgress = learnProgress;
    }
    public String getHistoryScore() {
        return historyScore;
    }
    public void setHistoryScore(String historyScore) {
        this.historyScore = historyScore;
    }
    public Integer getAverageScore() {
        return averageScore;
    }
    public void setAverageScore(Integer averageScore) {
        this.averageScore = averageScore;
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


    public Integer getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(Integer highestScore) {
        this.highestScore = highestScore;
    }
}
