package amhs.amhs.service;

import amhs.amhs.entity.Menu;
import amhs.amhs.entity.Role;
import amhs.amhs.entity.UserInfo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface RoleService {
    //int
    Integer update(Role role);

    Page<Role> findAll(Integer pageNum, Integer pageSize);

    Long getTotal(Map<String,Object> map);

    Integer updateMenu(Integer roleId, String menuIds);
}
