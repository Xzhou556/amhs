package amhs.amhs.service.impl;

import amhs.amhs.dao.MenuDao;
import amhs.amhs.dao.RoleDao;
import amhs.amhs.dao.RoleMenuDao;
import amhs.amhs.entity.Menu;
import amhs.amhs.entity.Role;
import amhs.amhs.entity.RoleMenu;
import amhs.amhs.service.MenuService;
import amhs.amhs.service.RoleService;
import amhs.amhs.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;
    @Autowired
    RoleMenuDao roleMenuDao;
    @Autowired
    MenuDao menuDao;
    @Autowired
    MenuService menuService;

    public  Role replace(Role curr,Role origin){
        if (curr.getName()==null){
            curr.setName(origin.getName());
        }
        if (curr.getOrderNo() == null) {
            curr.setOrderNo(origin.getOrderNo());
        }
        if (curr.getCreateDateTime() == null) {
            curr.setCreateDateTime(origin.getCreateDateTime());
        }
        if (curr.getUpdateDateTime() == null) {
            curr.setUpdateDateTime(origin.getUpdateDateTime());
        }

        return curr;
    }

    @Override
    public Integer update(Role role) {
        Role origin = roleDao.findId(role.getRoleId());
        role = replace(role,origin);
        roleDao.save(role);
        return 1;
    }

    @Override
    public Page<Role> findAll(Integer pageNum, Integer pageSize) {
        if (pageNum==null||pageNum==0){
            pageNum=1;
        }
        if (pageSize==null||pageSize==0){
            pageSize=15;
        }
        PageRequest of = PageRequest.of(pageNum - 1, pageSize);
        Page<Role> all = roleDao.findAll(of);

        return all;
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        long count = roleDao.count();
        return count;
    }

    @Override
    public Integer updateMenu(Integer roleId, String menuIds) {
        String[] idsArr = menuIds.split(",");
        RoleMenu roleMenu;
        //删除之前的菜单  根据 角色id
       roleMenuDao.deleteByRoleId(roleId);
        //添加现在新的 （角色 对应的菜单 ）
        for (int i = 0; i <idsArr.length ; i++) {
            if (StringUtil.isNotEmpty(idsArr[i])) {
                roleMenu = new RoleMenu();
                roleMenu.setRole(roleDao.findId(roleId));
                roleMenu.setMenu(menuDao.findId(Integer.parseInt(idsArr[i])));
                roleMenuDao.save(roleMenu);
            }
        }
       //修改角色 的更新 时间
        Role role = roleDao.findId(roleId);
         role.setUpdateDateTime(new Date());
        roleDao.save(role);
        return 1;
    }
}
