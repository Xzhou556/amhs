package amhs.amhs.controller;


import amhs.amhs.dao.MenuDao;
import amhs.amhs.entity.Menu;
import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import amhs.amhs.service.MenuService;
import amhs.amhs.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping("/Menu")
@RestController
@Api(value = "菜单api",tags = "菜单api")
public class MenuController {

    @Autowired
    MenuDao menuDao;
    @Autowired
    MenuService menuService;

    @PostMapping("/addMenu")
    @ApiOperation(value = "添加菜单",notes = "添加菜单")
    public RestResult addMenu(@RequestBody Menu menu) {

            menuDao.save(menu);
            return new ResultGenerator().getSuccessResult();

    }

    @GetMapping("/ziCaiDan")
    @ApiOperation(value = "查询子菜单",notes = "查询子菜单")
    public RestResult ziCaiDan(){
        List<Menu> menus = menuDao.ziCaiDan();
        return new ResultGenerator().getSuccessResult(menus);

    }

     @PutMapping("/updateMenu")
     @ApiOperation(value = "修改菜单",notes = "修改菜单")
     public RestResult updateMenu(@RequestBody Menu menu){
         if (menu == null) {
             return new ResultGenerator().getFailResult("数据不能为空");
         } else {

             menuService.update(menu);
             return new ResultGenerator().getSuccessResult();
         }
     }


    @GetMapping("/menuList")
    @ApiOperation(value = "查询菜单",notes = "查询菜单, pId   父节点  id")
    public RestResult menuList(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                               @RequestParam(value = "pageSize", required = false) Integer pageSize,
                               @RequestParam(value = "pId", required = false) Integer pId){
      if (pageNum==null||pageSize==null||pId==null){
          return  new ResultGenerator().getFailResult("数据不能为空");
      }

          Map<String, Object> map = new HashMap<>();
          if (StringUtil.isNotEmpty("pId")) {
              map.put("pId", pId);
          }
          List<Menu> content = menuService.list(map, pageNum - 1, pageSize);

          long total = menuService.getTotal(map);


          map.clear();
          map.put("content", content);
          map.put("total", total);
          return new ResultGenerator().getSuccessResult(map);

    }

    @DeleteMapping("/deleteMenu")
    @ApiOperation(value = "删除菜单",notes = "删除菜单, pId   父节点  id")
    public RestResult menuList(@RequestParam(value = "pId",required = false)String pId) {
        if (pId == null) {
            return new ResultGenerator().getFailResult("数据不能为空");
        } else {
            String[] arr = pId.split(",");
            Map<String, Object> map = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                try {
                    map.put("pId", Integer.parseInt(arr[i]));
                    //看看下面有没有菜单   一同删除
                    List<Menu> list = menuService.list(map, 0, 100);
                    for (Menu menu : list) {
                        menuDao.deleteById(menu.getMenuId());
                    }
                    //看看下面有没有菜单   一同删除
                    menuDao.deleteById(Integer.parseInt(arr[i]));
                } catch (Exception e) {
                    return new ResultGenerator().getFailResult("有角色正在使用些菜单");
                }
            }
            return new ResultGenerator().getSuccessResult("删除成功", "null");
        }
    }

    @GetMapping("/menuDetail")
    @ApiOperation(value = "查询单个菜单",notes = "查询菜单")
    public RestResult menuDetail(Integer menuId){
        if (menuId == null){
            return  new ResultGenerator().getFailResult("参数不能为空");
        }
        Menu origin = menuDao.findId(menuId);
       if(origin.getpId() != -1){

       }
        return  new ResultGenerator().getSuccessResult(origin);
    }


    @GetMapping("/allMenuList")
    @ApiOperation(value = "查询所有菜单",notes = "查询所有菜单")
    public RestResult allMenuList() throws Exception {
        Map<String,Object> map  = new HashMap<>();
        map.put("pId",-1);
        List<Menu> list = menuService.list(map, 0, 100);
        List<JSONObject> data = new ArrayList<>();
        for (Menu menu : list) {
            JSONObject n = new JSONObject();
            n.put("menu",menu);
            n.put("children",getChildren(menu.getMenuId()));
            data.add(n);
        }
    return  new ResultGenerator().getSuccessResult(data);
    }


    /**
     * 辅助方法  辅助 上面的 admin_main（getCheckedTreeMenu）
     * @param pId
     *
     * @return
     */
    public List<JSONObject> getChildren(Integer pId)  throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("pId",pId);
        List<Menu> list = menuService.list(map, 0, 100);
        List<JSONObject>  data =  new ArrayList<JSONObject>();
        for (Menu menu : list) {
            JSONObject n = new JSONObject();
            n.put("menu",menu);
            data.add(n);
        }
        return data;
    }
}
