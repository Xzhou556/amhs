package amhs.amhs.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity(name = "transducer")
@Table(name = "t_transducer")
@ApiModel("传感器")
public class Transducer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer TransducerId;

    private Integer deviceId;//设备类型

    private String deviceNumber;//设备编号
    @Column(precision = 10, scale = 2)
    private BigDecimal threshold;//报警水位阈值
    @Column(precision = 10, scale = 2)
    private BigDecimal liquidLevel;//液位值
    /*0：在线  1：离线*/
    private byte deviceStatus;//设备状态

    private String crc;//crc校验码


    private Date collectTime;//采集时间
    @Column(precision = 10, scale = 2)
    private BigDecimal temp;// 温度值
    @Column(precision = 10, scale = 6)
    private BigDecimal longitude;//经度
    @Column(precision = 10, scale = 6)
    private BigDecimal latitude;//纬度
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expirationDate;//到期时间

    private Integer batteryPercentage;//电池电量百分比

    private Integer rxLev;//信号强度
    @Column(precision = 10, scale = 2)
    private BigDecimal noisePower;//噪声强度

    private Integer dataWarm;//数据报警

    private Integer dataType;//数据类型

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    private Date createDateTime;//创建时间

    @ManyToOne(targetEntity = Factory.class)
    @JoinColumn(name = "factory_Id", referencedColumnName = "factoryId")
    private Factory factory;

    @ManyToOne
    @JoinColumn(name = "tt_id")
    private TransducerType transducerType;


    public TransducerType getTransducerType() {
        return transducerType;
    }

    public void setTransducerType(TransducerType transducerType) {
        this.transducerType = transducerType;
    }

    public Integer getTransducerId() {
        return TransducerId;
    }

    public void setTransducerId(Integer transducerId) {
        TransducerId = transducerId;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public BigDecimal getThreshold() {
        return threshold;
    }

    public void setThreshold(BigDecimal threshold) {
        this.threshold = threshold;
    }

    public BigDecimal getLiquidLevel() {
        return liquidLevel;
    }

    public void setLiquidLevel(BigDecimal liquidLevel) {
        this.liquidLevel = liquidLevel;
    }

    public byte getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(byte deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public BigDecimal getTemp() {
        return temp;
    }

    public void setTemp(BigDecimal temp) {
        this.temp = temp;
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

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(Integer batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public Integer getRxLev() {
        return rxLev;
    }

    public void setRxLev(Integer rxLev) {
        this.rxLev = rxLev;
    }

    public BigDecimal getNoisePower() {
        return noisePower;
    }

    public void setNoisePower(BigDecimal noisePower) {
        this.noisePower = noisePower;
    }

    public Integer getDataWarm() {
        return dataWarm;
    }

    public void setDataWarm(Integer dataWarm) {
        this.dataWarm = dataWarm;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Factory getFactory() {
        return factory;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }

    @Override
    public String toString() {
        return "Transducer{" +
                "deviceId=" + deviceId +
                ", deviceNumber=" + deviceNumber +
                ", threshold=" + threshold +
                ", liquidLevel=" + liquidLevel +
                ", deviceStatus=" + deviceStatus +
                ", crc='" + crc + '\'' +
                ", collectTime=" + collectTime +
                ", temp=" + temp +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", expirationDate=" + expirationDate +
                ", batteryPercentage=" + batteryPercentage +
                ", rxLev=" + rxLev +
                ", noisePower=" + noisePower +
                ", dataWarm=" + dataWarm +
                ", dataType=" + dataType +
                ", createDateTime=" + createDateTime +
                ", factory=" + factory +
                '}';
    }
}
