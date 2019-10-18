package amhs.amhs.controller;

import amhs.amhs.dao.UserInfoDao;
import amhs.amhs.entity.Role;
import amhs.amhs.entity.UserInfo;
import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import amhs.amhs.service.UserInfoService;
import amhs.amhs.utils.*;
import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
@Api(value = "用户api",tags = "用户api")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class);
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserInfoDao userInfoDao;

    @PostMapping("/addUser")
    @ApiOperation(value = "添加用户",notes = "添加用户")
    public RestResult addUser(@RequestBody UserInfo userInfo, HttpServletRequest request , HttpServletResponse response) {
        if (userInfo == null) {
            LOG.error("数据不能为空");
        } else {
            userInfo.setCreateDateTime(new Date());
            //CommonUtil随机获取五位salt
            String salt = CommonUtil.getRandomString(5);
            //给密码加密加盐
            userInfo.setSalt(salt);
            //userInfo.setPassword(MD5Util.md5(userInfo.getAccount()) + salt);
            userInfo.setPassword(ShiroKit.md5(userInfo.getAccount(),userInfo.getPassword()));

      //      System.out.println(userInfo.getRole().getRoleId());
            userInfoDao.save(userInfo);
        }
        return new ResultGenerator().getSuccessResult();
    }
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public RestResult login(@RequestParam(value = "account" ,required = false)String account,
                            @RequestParam(value = "password" ,required = false)String password){
        UserInfo userInfo = userInfoDao.findByAccount(account);
        // if ()

        String cc = ShiroKit.md5(account, password);

        System.out.println(userInfo.getPassword());
        if (userInfo.getPassword().equals(cc)){
            String token = JWTUtil.sign(account, password);
            return new ResultGenerator().getSuccessResult(token);
        }else if (!userInfo.getAccount().equals(account)) {
            return new ResultGenerator().getFailResult("账户密码不对");
        }else {
                LOG.error("账号密码错误");
                throw new UnauthorizedException();
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
            LOG.error("数据不能为空");
        } else {
            userInfo.setUpdateDateTime(new Date());
            userInfoService.upDate(userInfo);
        }
        return new ResultGenerator().getSuccessResult();
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
        if (map == null) {
            LOG.error("数据为空！！！");
        }
        return new ResultGenerator().getSuccessResult(map);
    }

    @PutMapping("/setPwd")
    @ApiOperation(value = "修改密码",notes = "修改密码")
    public RestResult setPwd(@RequestBody UserInfo userInfo){
        if (userInfo == null ) {
            LOG.error("数据不能为空");
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
        String s = JSONArray.toJSONString(userInfos);
        String[] arr = new String[s.length()];
        System.out.println(arr);
        // WriteExcelUtil.exportExcel(s,response.getOutputStream());
        return null;
    }
    @ApiOperation(value = "导出111")
    @GetMapping(value = "/findall")
    public RestResult findAllStudent(HttpServletResponse response) throws IOException {
        Workbook wb = new HSSFWorkbook();
        String headers[] = { "id", "major", "t_name" };
        int rowIndex = 0;
        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(rowIndex++);
        for (int i = 0; i < headers.length; i++) { // 先写表头
            row.createCell(i).setCellValue(headers[i]);

        }
        List<UserInfo> userInfos = userInfoDao.findAll();
        for (int i = 0; i < userInfos.size(); i++) {
            row = sheet.createRow(rowIndex++);
            if (row == null){
                continue;
            }
            row.createCell(0).setCellValue(userInfos.get(i).getAccount());
            row.createCell(1).setCellValue(userInfos.get(i).getUserId());
            row.createCell(2).setCellValue(userInfos.get(i).getPassword());
        }
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String("excel.xls".getBytes("utf-8"), "iso8859-1"));
        response.setContentType("application/ynd.ms-excel;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
        return new ResultGenerator().getSuccessResult();
    }


}
