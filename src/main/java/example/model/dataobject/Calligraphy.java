 
package example.model.dataobject;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;

 
public class Calligraphy implements Serializable{


	//========== properties ==========
	private UserLearn userLearn;
    //
    private Integer id;
    //
    private String name;
    //样字总数
    private Integer caSize;
    //样字保存路径.默认取path/img1的格式
    private String imgPath;

    private String shortImagePath;
    //
    private Date createTimte;
    //
    private Date updateTime;

    private String imgType;

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
    public String getImgPath() {
        return imgPath;
    }
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    public Date getCreateTimte() {
        return createTimte;
    }
    public void setCreateTimte(Date createTimte) {
        this.createTimte = createTimte;
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

    public UserLearn getUserLearn() {
        return userLearn;
    }

    public void setUserLearn(UserLearn userLearn) {
        this.userLearn = userLearn;
    }

    public Integer getCaSize() {
        return caSize;
    }

    public void setCaSize(Integer caSize) {
        this.caSize = caSize;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String getShortImagePath() {
        return shortImagePath;
    }

    public void setShortImagePath(String shortImagePath) {
        this.shortImagePath = shortImagePath;
    }
}
