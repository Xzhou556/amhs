package amhs.amhs.controller;

import amhs.amhs.dao.UserInfoDao;
import amhs.amhs.entity.Role;
import amhs.amhs.entity.UserInfo;
import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import amhs.amhs.service.UserInfoService;
import amhs.amhs.utils.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
@Api(value = "用户api",tags = "用户api")
public class UserController {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserInfoDao userInfoDao;

    @PostMapping("/addUser")
    @ApiOperation(value = "添加用户",notes = "添加用户")
    public RestResult addUser(@RequestBody UserInfo userInfo, HttpServletRequest request , HttpServletResponse response) {
        if (userInfo == null) {
            return new ResultGenerator().getFailResult("数据不能为空");
        } else {
            userInfo.setCreateDateTime(new Date());
            //CommonUtil随机获取五位salt
            String salt = CommonUtil.getRandomString(5);
            //给密码加密加盐
            userInfo.setPassword(MD5Util.md5(userInfo.getAccount()) + salt);
            userInfo.setSalt(salt);
      //      System.out.println(userInfo.getRole().getRoleId());
            userInfoDao.save(userInfo);
            return new ResultGenerator().getSuccessResult();
        }
    }

    @GetMapping("/userDetail")
    @ApiOperation(value = "查询单个用户",notes = "查询单个用户")
    public RestResult userDetail(@RequestParam(value = "userId",required = false)Integer userId){
        UserInfo userInfo = userInfoDao.findId(userId);
        return  new ResultGenerator().getSuccessResult(userInfo);
    }

    @PutMapping("/updateUser")
    @ApiOperation(value = "修改用户",notes = "修改用户")
    public RestResult updateUser(@RequestBody UserInfo userInfo) {
        if (userInfo == null) {
            return new ResultGenerator().getFailResult("数据不能为空");
        } else {
            userInfo.setUpdateDateTime(new Date());
            userInfoService.upDate(userInfo);
            return new ResultGenerator().getSuccessResult();
        }
    }
    @DeleteMapping("/deleteUser")
    @ApiOperation(value = "删除用户",notes = "删除用户")
    public RestResult deleteUser(@RequestParam(value = "ids")String ids) {
        if (ids == null) {
            return new ResultGenerator().getFailResult("数据不能为空");
        } else {
            String[] arr = ids.split(",");
            for (int i = 0; i < arr.length; i++) {
                userInfoDao.deleteById(Integer.parseInt(arr[i]));
            }
            return new ResultGenerator().getSuccessResult();
        }
    }

    @GetMapping("/userList")
    @ApiOperation(value = "遍历所有用户",notes = "遍历所有用户")
    public RestResult userList(@RequestParam(value = "pageNum",required = false)Integer pageNum,@RequestParam(value = "pageSize",required = false)Integer pageSize){
        if (pageNum == null || pageSize==null) {
            return new ResultGenerator().getFailResult("数据不能为空");
        }

        if (pageNum==0||pageNum==null){
            pageNum=1;
        }
        Map<String,Object> map = new HashMap<>();
        Page<UserInfo> all = userInfoService.findAll(pageNum, pageSize);
        Long total = userInfoService.getTotal(map);
        List<UserInfo> content = all.getContent();
        map.put("total",total);
        map.put("content",content);
        return new ResultGenerator().getSuccessResult(map);
    }

    @PutMapping("/setPwd")
    @ApiOperation(value = "修改密码",notes = "修改密码")
    public RestResult setPwd(@RequestBody UserInfo userInfo){
        if (userInfo == null ) {
            return new ResultGenerator().getFailResult("数据不能为空");
        }

        userInfo.setUpdateDateTime(new Date());
        if (StringUtil.isNotEmpty(userInfo.getPassword())){
            String salt = CommonUtil.getRandomString(5);
            userInfo.setPassword(salt+userInfo.getAccount());
            userInfoService.upDate(userInfo);
        }
        return  new ResultGenerator().getSuccessResult("修改成功","success");
    }

    @ApiOperation(value = "用户登出", httpMethod = "POST")
    @PostMapping("/exit")
    public RestResult accountExit(){
        SecurityUtils.getSubject().logout();
        return new ResultGenerator().getSuccessResult("用户退出成功",null);
    }


    @ApiOperation(value = "导入")
    @PostMapping("/importFile")
    public RestResult importFile(@RequestParam(value = "file" ,required = false) MultipartFile file) throws Exception {

        List<String[]> list = ReadExcelUtil.readExcel(file);
        System.out.println(list);
        return new ResultGenerator().getSuccessResult();
    }

    @ApiOperation(value = "导出")
    @GetMapping("/exportFile")
    public RestResult exportFile(HttpServletResponse response) throws IOException {
        List<UserInfo> userInfos = userInfoDao.findAll();
      //  WriteExcelUtil.exportExcel(userInfos,response.getOutputStream());
        return null;
    }
}
