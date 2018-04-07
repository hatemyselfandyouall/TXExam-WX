 
package example.model.dataobject;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;

 
public class Question implements Serializable{


	//========== properties ==========
	
    //
    private Integer id;
    //
    private Integer examinationId;
    //1单选,2多选,3简单
    private Integer questionType;
    //题目内容
    private String content;
    //选项,对单多选有效,以|分隔
    private String choice;
    //答案,对单多选有效,以|分隔
    private String answerChoice;
    //文字答案,对简答有效
    private String answerContent;
    //试题解析
    private String answerDetail;
    //简答题分数,不能超过章节最大分数
    private Double score;
    //所在章节id
    private Integer chapterId;
    //所属章节名
    private String chapterName;
    //难度级别,1到4
    private Integer hardRank;
    //是否为真题
    private Integer isReal;
    //
    private Integer isDelete;
    //
    private Date createTime;
    //
    private Date updateTime;
    //-============================额外字段
    private Integer sort;

    //========== getters and setters ==========
	
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getExaminationId() {
        return examinationId;
    }
    public void setExaminationId(Integer examinationId) {
        this.examinationId = examinationId;
    }
    public Integer getQuestionType() {
        return questionType;
    }
    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getChoice() {
        return choice;
    }
    public void setChoice(String choice) {
        this.choice = choice;
    }
    public String getAnswerChoice() {
        return answerChoice;
    }
    public void setAnswerChoice(String answerChoice) {
        this.answerChoice = answerChoice;
    }
    public String getAnswerContent() {
        return answerContent;
    }
    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }
    public String getAnswerDetail() {
        return answerDetail;
    }
    public void setAnswerDetail(String answerDetail) {
        this.answerDetail = answerDetail;
    }

    public Integer getChapterId() {
        return chapterId;
    }
    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }
    public String getChapterName() {
        return chapterName;
    }
    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
    public Integer getHardRank() {
        return hardRank;
    }
    public void setHardRank(Integer hardRank) {
        this.hardRank = hardRank;
    }
    public Integer getIsReal() {
        return isReal;
    }
    public void setIsReal(Integer isReal) {
        this.isReal = isReal;
    }
    public Integer getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
