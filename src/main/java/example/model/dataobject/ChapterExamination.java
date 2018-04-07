 
package example.model.dataobject;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;

 
public class ChapterExamination implements Serializable{


	//========== properties ==========
	
    //
    private Integer id;
    //
    private Integer examinationId;
    //
    private Integer charpterId;
    //
    private Integer judgeSize;
    //
    private Integer judgeUse;
    //
    private Integer singleSize;
    //
    private Integer singleUse;
    //
    private Integer multSize;
    //
    private Integer multUse;
    //
    private Integer saqSize;
    //
    private Integer saqUse;
    //
    private Integer isDelete;
    //
    private Date createTime;
    //
    private Date updateTime;


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
    public Integer getCharpterId() {
        return charpterId;
    }
    public void setCharpterId(Integer charpterId) {
        this.charpterId = charpterId;
    }
    public Integer getJudgeSize() {
        return judgeSize;
    }
    public void setJudgeSize(Integer judgeSize) {
        this.judgeSize = judgeSize;
    }
    public Integer getJudgeUse() {
        return judgeUse;
    }
    public void setJudgeUse(Integer judgeUse) {
        this.judgeUse = judgeUse;
    }
    public Integer getSingleSize() {
        return singleSize;
    }
    public void setSingleSize(Integer singleSize) {
        this.singleSize = singleSize;
    }
    public Integer getSingleUse() {
        return singleUse;
    }
    public void setSingleUse(Integer singleUse) {
        this.singleUse = singleUse;
    }
    public Integer getMultSize() {
        return multSize;
    }
    public void setMultSize(Integer multSize) {
        this.multSize = multSize;
    }
    public Integer getMultUse() {
        return multUse;
    }
    public void setMultUse(Integer multUse) {
        this.multUse = multUse;
    }
    public Integer getSaqSize() {
        return saqSize;
    }
    public void setSaqSize(Integer saqSize) {
        this.saqSize = saqSize;
    }
    public Integer getSaqUse() {
        return saqUse;
    }
    public void setSaqUse(Integer saqUse) {
        this.saqUse = saqUse;
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
}
