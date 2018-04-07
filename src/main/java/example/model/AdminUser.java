 
package example.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;


public class AdminUser implements Serializable{


	//========== properties ==========

    private Integer id;
    //微信账号
    private String wAccount;
    //微信昵称
    private String wNickname;
    //微信获取性别,0未知,1男2女
    private Integer sex;
    //使用语言
    private String language;
    //所在城市
    private String city;
    //所在省份
    private String province;
    //所在国家
    private String country;
    //微信头像
    private String headimgurl;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;


    //========== getters and setters ==========

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getWAccount() {
        return wAccount;
    }
    public void setWAccount(String wAccount) {
        this.wAccount = wAccount;
    }
    public String getWNickname() {
        return wNickname;
    }
    public void setWNickname(String wNickname) {
        this.wNickname = wNickname;
    }
    public Integer getSex() {
        return sex;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getHeadimgurl() {
        return headimgurl;
    }
    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
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
