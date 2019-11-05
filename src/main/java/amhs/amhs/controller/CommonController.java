package amhs.amhs.controller;

import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@CrossOrigin
@RequestMapping("/common")
@RestController
@Api(value = "通用", tags = "通用api")
public class CommonController {
    private static final Logger LOG = Logger.getLogger(CommonController.class);

    //处理文件上传
    @ApiOperation(value = "单个文件上传", notes = "单个文件上传")
    @PostMapping(value = "/uploadFile")
    public RestResult uploadFile(@RequestParam(value = "file", required = false) MultipartFile file,
                             HttpServletRequest request) {
        if (file.isEmpty()) {
            return new ResultGenerator().getFailResult("文件不能为空");
        }
        // String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();  //获取上传文件原名

        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        LOG.info(fileName + suffixName);
        String filePath = "E://other//";
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();//返回json
            LOG.info("上传成功");
        }
        try {
            file.transferTo(dest);

        } catch (IOException e) {
            LOG.error(e.getMessage(), e.getCause());
        }
        return new ResultGenerator().getSuccessResult("/other/" + fileName);
    }

    //处理文件上传
    @ApiOperation(value = "单个文件上传", notes = "单个文件上传")
    @PostMapping(value = "/uploadImage")
    public RestResult uploadImage(@RequestParam(value = "file", required = false) MultipartFile file,
                             HttpServletRequest request) {
        if (file.isEmpty()) {
            return new ResultGenerator().getFailResult("文件不能为空");
        }
        // String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();  //获取上传文件原名

        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        LOG.info(fileName + suffixName);
        String filePath = "E://txt//";
        fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();//返回json
            LOG.info("上传成功");
        }
        try {
            file.transferTo(dest);

        } catch (IOException e) {
            LOG.error(e.getMessage(), e.getCause());
        }
        return new ResultGenerator().getSuccessResult("/images/" + fileName);
    }
}
