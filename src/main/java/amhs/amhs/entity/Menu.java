package amhs.amhs.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="t_menu")
public class Menu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer menuId;
    @Column(length=10)
    private Integer pId; // 父菜单Id 根是-1   然后是自己编id
    @Column(length=50)
    private String name; //菜单名称
    @Column(length=200)
    private String url; //菜单地址
    @Column(length=10)
    private Integer state; //状态
    @Column(length=100)
    private String icon; // 图标
    @Column(length=10)
    private Integer orderNo;

   private String peimissions;

    public String getPeimissions() {
        return peimissions;
    }

    public void setPeimissions(String peimissions) {
        this.peimissions = peimissions;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }





    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
}
