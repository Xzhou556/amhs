package amhs.amhs.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="t_role_menu")
public class RoleMenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="roleId")
    private Role role; // 角色
    @ManyToOne
    @JoinColumn(name="menuId")
    private Menu menu; // 菜单

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
