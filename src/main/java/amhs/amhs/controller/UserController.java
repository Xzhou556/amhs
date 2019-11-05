package amhs.amhs.controller;

import amhs.amhs.dao.FactoryDao;
import amhs.amhs.dao.RoleDao;
import amhs.amhs.dao.UserFactoryDao;
import amhs.amhs.dao.UserInfoDao;
import amhs.amhs.entity.Factory;
import amhs.amhs.entity.UserFactory;
import amhs.amhs.entity.UserInfo;
import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import amhs.amhs.service.UserInfoService;
import amhs.amhs.utils.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.service.ApiListing;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
@Api(value = "用户api", tags = "用户api")
public class UserController {
    private static final Logger LOG = Logger.getLogger(UserController.class);
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    UserFactoryDao userFactoryDao;
    @Autowired
    FactoryDao factoryDao;
    @Autowired
    RoleDao roleDao;

    @PostMapping("/addUser")
    @ApiOperation(value = "添加用户", notes = "添加用户")
    public RestResult addUser(@RequestBody String data) {
        JSONObject jsonObject = JSON.parseObject(data);
        String account = jsonObject.getString("account").trim();
        Integer factoryId = jsonObject.getInteger("factoryId");
        String mobilePhone = jsonObject.getString("mobilePhone").trim();
        String remark = jsonObject.getString("remark");
        Integer roleId = jsonObject.getInteger("roleId");
        Integer state = jsonObject.getInteger("state");
        String trueName = jsonObject.getString("trueName").trim();
        String password = jsonObject.getString("password").trim();
        UserInfo userInfo = new UserInfo();
        userInfo.setCreateDateTime(new Date());
        String salt = CommonUtil.getRandomString(5);
        userInfo.setCreateDateTime(new Date());
        userInfo.setSalt(salt);
        userInfo.setAccount(account);
        userInfo.setPassword(ShiroKit.md5(account, password));
        userInfo.setMobilePhone(mobilePhone);
        userInfo.setRemark(remark);
        userInfo.setState(state);
        userInfo.setTrueName(trueName);
        userInfo.setRole(roleDao.findId(roleId));
        userInfoDao.save(userInfo);
        UserFactory userFactory = new UserFactory();
        userFactory.setFactory(factoryDao.findId(factoryId));
        userFactory.setUserInfo(userInfoDao.findId(userInfo.getUserId()));
        userFactoryDao.save(userFactory);
          /*  userInfo.setCreateDateTime(new Date());
            //CommonUtil随机获取五位salt
            String salt = CommonUtil.getRandomString(5);
            //给密码加密加盐
            userInfo.setSalt(salt);
            //userInfo.setPassword(MD5Util.md5(userInfo.getAccount()) + salt);
            userInfo.setPassword(ShiroKit.md5(userInfo.getAccount(), userInfo.getPassword()));
            //System.out.println(userInfo.getRole().getRoleId());
            userInfoDao.save(userInfo);

           */
        LOG.info("用户添加成功");
        return new ResultGenerator().getSuccessResult();
    }

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public RestResult login(@RequestBody String data) {
       JSONObject jsonObject = JSON.parseObject(data);
        String account = jsonObject.getString("account");
        String password = jsonObject.getString("password");
        UserInfo userInfo = userInfoDao.findByAccount(account);
        // if ()
        Map<String, Object> map = new HashMap<>();
        String cc = ShiroKit.md5(account, password);
        if (userInfo == null){
            return new ResultGenerator().getFailResult("账号或密码错误");
        }
        //   System.out.println(userInfo.getPassword());
        if (userInfo.getPassword().equals(cc)) {
            String token = JWTUtil.sign(account, password);
            map.put("token", token);
            map.put("userInfo", userInfo);
        } else if (!userInfo.getAccount().equals(account)) {
            return new ResultGenerator().getFailResult("账号或密码错误");
        } else {

            return new ResultGenerator().getFailResult("账号或密码错误");
        }

        return new ResultGenerator().getSuccessResult(map);
    }

    @GetMapping("/userDetail")
    @ApiOperation(value = "查询单个用户", notes = "查询单个用户")
    public RestResult userDetail(@RequestParam(value = "userId", required = false) Integer userId) {
        Map<String, Object> map = new HashMap<>();
        UserInfo userInfo = userInfoDao.findId(userId);
        UserFactory userFactory = userFactoryDao.findUserId(userId);
        Factory factory = factoryDao.findId(userFactory.getFactory().getFactoryId());
        map.put("userInfo", userInfo);
        map.put("factory", factory);
        return new ResultGenerator().getSuccessResult(map);
    }

    @PostMapping("/updateUser")
    @ApiOperation(value = "修改用户", notes = "修改用户")
    public RestResult updateUser(@RequestBody String data) {
        JSONObject jsonObject = JSON.parseObject(data);
        Integer userId = jsonObject.getInteger("userId");
        Integer factoryId = jsonObject.getInteger("factoryId");
        String mobilePhone = jsonObject.getString("mobilePhone").trim();
        String account = jsonObject.getString("account");
        String remark = jsonObject.getString("remark");
        Integer roleId = jsonObject.getInteger("roleId");
        Integer state = jsonObject.getInteger("state");
        String trueName = jsonObject.getString("trueName").trim();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setAccount(account);
        userInfo.setMobilePhone(mobilePhone);
        userInfo.setRemark(remark);
        userInfo.setState(state);
        userInfo.setTrueName(trueName);
        userInfo.setRole(roleDao.findId(roleId));
        userInfo.setUpdateDateTime(new Date());
        userInfoService.upDate(userInfo);
        LOG.info("用户修改成功");
        UserFactory uf = new UserFactory();
        userFactoryDao.deleteUserId(userId);
        uf.setFactory(factoryDao.findId(factoryId));
        uf.setUserInfo(userInfoDao.findId(userId));
        userFactoryDao.save(uf);
        return new ResultGenerator().getSuccessResult();
    }

    @DeleteMapping("/deleteUser")
    @ApiOperation(value = "删除用户", notes = "删除用户")
    public RestResult deleteUser(@RequestParam(value = "ids") String ids) {
        if (ids == null) {
            return new ResultGenerator().getFailResult("数据不能为空");
        } else {
            String[] arr = ids.split(",");
            for (int i = 0; i < arr.length; i++) {
                userFactoryDao.deleteUserId(Integer.parseInt(arr[i]));
                userInfoDao.deleteById(Integer.parseInt(arr[i]));
                LOG.info("用户删除成功");
            }
            return new ResultGenerator().getSuccessResult();
        }
    }

    @GetMapping("/userList")
    @ApiOperation(value = "遍历所有用户", notes = "遍历所有用户")
    public RestResult userList(@RequestParam(value = "pageNum", required = false) Integer pageNum, @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageNum == null || pageSize == null) {
            return new ResultGenerator().getFailResult("数据不能为空");
        }

        if (pageNum == 0 || pageNum == null) {
            pageNum = 1;
        }
        Map<String, Object> map = new HashMap<>();
        if (pageSize == null || pageSize == 0) {
            List<UserInfo> content = userInfoDao.findAll();
            long total = userInfoDao.count();
            map.put("total", total);
            map.put("content", content);
        } else {

            Page<UserInfo> all = userInfoService.findAll(pageNum, pageSize);
            Long total = userInfoService.getTotal(map);
            List<UserInfo> content = all.getContent();
            map.put("total", total);
            map.put("content", content);
        }
        if (map == null) {
            LOG.error("数据为空！！！");
        }
        return new ResultGenerator().getSuccessResult(map);
    }

    @PostMapping("/setPwd")
    @ApiOperation(value = "修改密码", notes = "修改密码")
    public RestResult setPwd(@RequestBody String data) {
        System.out.println(data + "++++++++++++++++++++++++++");
        JSONObject jsonObject = JSON.parseObject(data);
        Integer userId = jsonObject.getInteger("userId");
        String password = jsonObject.getString("password");
        UserInfo userInfo = userInfoDao.findId(userId);
        userInfo.setPassword(ShiroKit.md5(userInfo.getAccount(), password));
        userInfoService.upDate(userInfo);
        LOG.info("用户密码修改成功");
        return new ResultGenerator().getSuccessResult("修改成功", "success");
    }

    @ApiOperation(value = "用户登出", httpMethod = "POST")
    @PostMapping("/exit")
    public RestResult accountExit() {
        SecurityUtils.getSubject().logout();
        return new ResultGenerator().getSuccessResult("用户退出成功", null);
    }


    @ApiOperation(value = "导入")
    @PostMapping("/importFile")
    public RestResult importFile(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {

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
        String headers[] = {"id", "major", "t_name"};
        int rowIndex = 0;
        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(rowIndex++);
        for (int i = 0; i < headers.length; i++) { // 先写表头
            row.createCell(i).setCellValue(headers[i]);

        }
        List<UserInfo> userInfos = userInfoDao.findAll();
        for (int i = 0; i < userInfos.size(); i++) {
            row = sheet.createRow(rowIndex++);
            if (row == null) {
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

    @ApiOperation(value = "账号查找")
    @GetMapping("/isFound")
    public RestResult isFound(String account) {
        UserInfo userInfo = userInfoDao.findAccount(account);
        Map<String, Object> map = new HashMap<>();
        if (userInfo == null) {
            map.put("isFound", false);
        } else {
            map.put("isFound", true);
        }
        return new ResultGenerator().getSuccessResult(map);
    }

  /*  @ApiOperation(value = "验证码生成")
    @GetMapping("/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();

        try {

            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "验证码判断")
    @GetMapping("/verifyCode")
    public RestResult verifyCode(HttpSession request, HttpServletResponse response, String code) {
        System.out.println(code);

         String codeKey = request.getAttribute("RANDOMVALIDATECODEKEY").toString();
     //   Subject subject = SecurityUtils.getSubject();
     //   String codeKey = (String) subject.getSession().getAttribute("RANDOMVALIDATECODEKEY");
        Map<String, Object> map = new HashMap<>();

        if (code != null && !"".equals(code) && codeKey != null && !"".equals(codeKey)) {
            if (code.equalsIgnoreCase(codeKey)) {
                map.put("isVerify",true);
            }else {
                map.put("isVerify",false);

            }
        }
        return new ResultGenerator().getSuccessResult(map);
    }
*/

    @ApiOperation(value = "验证码生成")
    @GetMapping("/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        VerificationCode verificationCode = new VerificationCode();
        //获取验证码图片
        BufferedImage image = verificationCode.getImage();
        //获取验证码内容
        String text = verificationCode.getText();
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        randomCode.append(text);
        // 将验证码保存到Session中。
        HttpSession session = request.getSession();
        session.setAttribute("signcode", randomCode.toString());
        System.out.println("session-signcode==>" + randomCode.toString());
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(image, "jpeg", sos);
        sos.flush();
        sos.close();
    }

    @ApiOperation(value = "验证码判断")
    @GetMapping("/verifyCode")
    public RestResult verifyCode(HttpServletRequest request, String code) {
        HttpSession session = request.getSession();
        String signcode = (String) session.getAttribute("signcode");
        System.out.println("signcode==>" + signcode);
        System.out.println("signcodeSession==>" + code);
        Map<String, Object> map = new HashMap<>();

        if (code != null && !"".equals(code) && signcode != null && !"".equals(signcode)) {
            if (code.equalsIgnoreCase(signcode)) {
                map.put("isVerify", true);
            } else {
                map.put("isVerify", false);
            }
        }
        return new ResultGenerator().getSuccessResult(map);
    }
}
