package amhs.amhs.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Entity(name = "factory")
@Table(name = "t_factory")

public class Factory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer factoryId;//工厂id

    private String factoryName;//工厂名称

    private  String address;//工厂地址

    private  String phone;//工厂电话

    private String  Introduction;//公司简介

    @Column(precision = 10, scale = 6)
    private BigDecimal longitude;//经度

    @Column(precision = 10, scale = 6)
    private BigDecimal latitude;//纬度

    @Column(columnDefinition = "text")
    private String picture; //图片



    private String factoryType; //公司的类型 不用枚举了自己填下公司类型

    @OneToMany(targetEntity = Transducer.class)
    @JoinColumn(name = "factory_Id",referencedColumnName = "factoryId")
    private List<Transducer> transducers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_factory",joinColumns = {@JoinColumn(name = "factoryId")},inverseJoinColumns = {@JoinColumn(name ="userId" )})
    private List<UserInfo> userInfos ;

    @ManyToMany
    @JoinTable(name = "wxUser_factory",
            joinColumns = {@JoinColumn(name = "factoryId")},
            inverseJoinColumns = {@JoinColumn(name = "wxId")})
    private Set<WxUser> wxUsers;

    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDateTime;
    //修改时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDateTime;

    public Integer getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Integer factoryId) {
        this.factoryId = factoryId;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(String introduction) {
        Introduction = introduction;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFactoryType() {
        return factoryType;
    }

    public void setFactoryType(String factoryType) {
        this.factoryType = factoryType;
    }

    public List<Transducer> getTransducers() {
        return transducers;
    }

    public void setTransducers(List<Transducer> transducers) {
        this.transducers = transducers;
    }

    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }

    public Set<WxUser> getWxUsers() {
        return wxUsers;
    }

    public void setWxUsers(Set<WxUser> wxUsers) {
        this.wxUsers = wxUsers;
    }
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(Date updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    @Override
    public String toString() {
        return "Factory{" +
                "factoryId=" + factoryId +
                ", factoryName='" + factoryName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", Introduction='" + Introduction + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", picture='" + picture + '\'' +
                ", factoryType='" + factoryType + '\'' +
                ", transducers=" + transducers +
                ", userInfos=" + userInfos +
                ", wxUsers=" + wxUsers +
                ", createDateTime=" + createDateTime +
                ", updateDateTime=" + updateDateTime +
                '}';
    }
}
