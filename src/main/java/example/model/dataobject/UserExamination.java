 
package example.model.dataobject;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;

 
public class UserExamination implements Serializable{


	//========== properties ==========
	
    //
    private Integer id;
    //
    private Integer userId;
    //
    private Integer examinationId;

    private String examinationName;
    //
    private Date createTime;
    //
    private Date updateTime;

    private String orderId;

    private String prepayId;

    private Integer isDelete;

    private Integer hasPayed;

    private Date payTime;

    private Integer testCount;

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
    public Integer getExaminationId() {
        return examinationId;
    }
    public void setExaminationId(Integer examinationId) {
        this.examinationId = examinationId;
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


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }


    /**
     *
     */
    public static UserExamination createOrder(Integer userId,Integer examinationId,String examinationName,String OrderId,String prepayId){
        UserExamination userExamination=new UserExamination();
        userExamination.setUserId(userId);
        userExamination.setExaminationId(examinationId);
        userExamination.setExaminationName(examinationName);
        userExamination.setCreateTime(new Date());
        userExamination.setUpdateTime(new Date());
        userExamination.setIsDelete(0);
        userExamination.setOrderId(OrderId);
        userExamination.setPayTime(new Date());
        userExamination.setPrepayId(prepayId);
        userExamination.setHasPayed(0);
        return userExamination;
    }

    public Integer getHasPayed() {
        return hasPayed;
    }

    public void setHasPayed(Integer hasPayed) {
        this.hasPayed = hasPayed;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getExaminationName() {
        return examinationName;
    }

    public void setExaminationName(String examinationName) {
        this.examinationName = examinationName;
    }





    public Integer getTestCount() {
        return testCount;
    }

    public void setTestCount(Integer testCount) {
        this.testCount = testCount;
    }
}
