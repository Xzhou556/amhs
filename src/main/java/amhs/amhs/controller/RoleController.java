package amhs.amhs.controller;

import amhs.amhs.dao.MenuDao;
import amhs.amhs.dao.RoleDao;
import amhs.amhs.dao.RoleMenuDao;
import amhs.amhs.entity.Menu;
import amhs.amhs.entity.Role;
import amhs.amhs.entity.RoleMenu;
import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import amhs.amhs.service.MenuService;
import amhs.amhs.service.RoleService;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/role")
@Api(value = "角色api", tags = "角色api")
public class RoleController {
    private static final Logger LOG = Logger.getLogger(RoleController.class);
    @Autowired
    RoleMenuDao roleMenuDao;
    @Autowired
    RoleService roleService;
    @Autowired
    RoleDao roleDao;
    @Autowired
    MenuService menuService;
    @Autowired
    MenuDao menuDao;

    @PostMapping("/addRole")
    @ApiOperation(value = "添加角色", notes = "添加角色")
    public RestResult addRole(@RequestBody Role role) {
        if (role.getName() == null || "".equals(role.getName())) {
           LOG.error("角色名称不能为空");
        } else {
            role.setCreateDateTime(new Date());
            roleDao.save(role);
        }
        return new ResultGenerator().getSuccessResult();
    }

    @PutMapping("/updateRole")
    @ApiOperation(value = "修改角色", notes = "修改角色")
    public RestResult updateRole(@RequestBody Role role) {
        // role.setUpdateDateTime(new Date());
        roleService.update(role);
        return new ResultGenerator().getSuccessResult();
    }

    @DeleteMapping("/deleteRole")
    @ApiOperation(value = "删除角色", notes = "删除角色")
    public RestResult deleteRole(@RequestParam String ids) {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            try {
                roleDao.deleteById(Integer.parseInt(idsStr[i]));
            } catch (Exception e) {
                return new ResultGenerator().getFailResult("有角色在使用");
            }
        }
        return new ResultGenerator().getSuccessResult();
    }

    @GetMapping("/getCheckedMenuData")
    @ApiOperation(value = "通过获取菜单数据", notes = "获取菜单数据")
    public RestResult getCheckedMenuData(@RequestParam(value = "roleId", required = false) Integer roleId) {
        List<JSONObject> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("pId", -1);
        List<Menu> menus = menuService.list(map, 0, 100);
        for (Menu menu : menus) {
            JSONObject node = new JSONObject();
            node.put("menu", menu);
            node.put("children", getChilren(menu.getMenuId(), roleId));
            list.add(node);
        }
        return new ResultGenerator().getSuccessResult(list);
    }


    public List<JSONObject> getChilren(Integer pId, Integer roleId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pId", pId);
        List<Menu> menuList = menuService.list(map, 0, 100);

        List<JSONObject> list = new ArrayList<JSONObject>();
        for (Menu menu : menuList) {
            JSONObject node = new JSONObject();
            // node.put("spread", true);
            node.put("menu", menu);

            //判断 当前菜单     当前角色有没有拥有。
            RoleMenu roleMenu = roleMenuDao.findByRoleIdAndMenuId(roleId, menu.getMenuId());
            if (roleMenu != null) {
                node.put("checked", true);
            }
            //判断 当前菜单     当前角色有没有拥有。

            list.add(node);
        }
        return list;
    }

    @GetMapping("/roleList")
    @ApiOperation(value = "获取所有角色信息(分页)", notes = "获取所有角色信息(分页)")
    public RestResult roleList(@RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        if (pageNum == 0 || pageNum == null) {
            pageNum = 1;
        }
        Map<String, Object> map = new HashMap<>();
        Page<Role> all = roleService.findAll(pageNum, pageSize);
        List<Role> content = all.getContent();
        long total = roleDao.count();
        map.put("total", total);
        map.put("content", content);
        return new ResultGenerator().getSuccessResult(map);
    }

    @GetMapping("/allRole")
    @ApiOperation(value = "获取所有角色信息", notes = "获取所有角色信息")
    public RestResult allRole() {
        List<Role> all = roleDao.findAll();
        return new ResultGenerator().getSuccessResult(all);
    }

  /*  @PostMapping("/updateMenu")
    @ApiOperation(value = "修改角色和菜单",notes = "修改角色和菜单")
    public RestResult updateMenu(@RequestBody String data){

        Menu menu4 = null;

       JSONObject jsonObject = JSON.parseObject(data);
        String menu = jsonObject.getString("menu");
        JSONArray jsonArray  = JSON.parseArray(menu);
        for (int i = 0; i < jsonArray.size(); i++) {
            String children  = jsonArray.getJSONObject(i).getString("children");

            JSONArray jsonArray1 = JSON.parseArray(children);

           String menu2  = jsonArray.getJSONObject(i).getString("menu");

           JSONObject jsonObject1 = JSON.parseObject(menu2);

            Integer menuId1 = jsonObject1.getInteger("menuId");




            for (int j = 0; j <jsonArray1.size() ; j++) {

                String menu1  = jsonArray1.getJSONObject(j).getString("menu");
             //   System.out.println(jsonArray1);
                JSONObject jsonObject3 = JSON.parseObject(menu1);
                Integer menuId = jsonObject3.getInteger("menuId");
                Integer pId  =  jsonObject3.getInteger("pId");
                String url = jsonObject3.getString("url");
                String name = jsonObject3.getString("name");
                Integer state = jsonObject3.getInteger("state");
                String icon = jsonObject3.getString("icon");
                Integer orderNo = jsonObject3.getInteger("orderNo");

                menu4 = new Menu();
                menu4.setMenuId(menuId);
                menu4.setpId(pId);
                menu4.setUrl(url);
                menu4.setName(name);
                menu4.setState(state);
                menu4.setIcon(icon);
                menu4.setOrderNo(orderNo);

                menuService.update(menu4);
            }

        }
        //获取到的roleId
        Integer roleId = jsonObject.getInteger("roleId");
        roleService.updateMenu(roleId,menu4.getMenuId());

        return new ResultGenerator().getSuccessResult();
    }
*/

    @GetMapping("/roleDetail")
    @ApiOperation(value = "查询单个角色", notes = "查询单个角色")
    public RestResult roleDetail(Integer roleId) {
        Role data = roleDao.findId(roleId);
        return new ResultGenerator().getSuccessResult(data);
    }

    @PostMapping("/updateMenu")
    @ApiOperation(value = "修改角色和菜单", notes = "修改角色和菜单")
    public RestResult updateMenu(@RequestParam(value = "roleId", required = false) Integer roleId,
                                 @RequestParam(value = "menuId", required = false) String menuId) {
        roleService.updateMenu(roleId, menuId);
        return new ResultGenerator().getSuccessResult();
    }
}
