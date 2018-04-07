 
package example.model.dataobject;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;

 
public class UserExam implements Serializable{


	//========== properties ==========
	
    //
    private Integer id;
    //
    private Integer userId;
    //图片路径
    private String imgPath;
    //图片类型
    private String imgType;
    //范例数量
    private Integer exSize;
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
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getImgPath() {
        return imgPath;
    }
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    public String getImgType() {
        return imgType;
    }
    public void setImgType(String imgType) {
        this.imgType = imgType;
    }
    public Integer getExSize() {
        return exSize;
    }
    public void setExSize(Integer exSize) {
        this.exSize = exSize;
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
