package amhs.amhs.controller;

import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import amhs.amhs.service.FactoryService;
import amhs.amhs.service.TransducerService;
import amhs.amhs.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@CrossOrigin
@RequestMapping("/index")
@RestController
@Api(value = "初始化界面api",tags = "初始化界面api")
public class IndexController {
    private static final Logger LOG = Logger.getLogger(IndexController.class);
    @Value("${smas.captrue.image.path}")
    private String captureImagePath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Value("${file.uri}")
    private String uri;


    @Autowired
    FactoryService factoryService;

    @Autowired
    TransducerService transducerService;

    @ApiOperation(value = "显示所有统计数据" ,httpMethod = "GET")
    @RequestMapping("/allData")
    public RestResult allData(){
        int total = factoryService.totalFactory();
        int total1 = transducerService.findInLine();
        int total2 = transducerService.findOffLine();
        Map<String,Integer> map= new HashMap<>();
        map.put("factoryTotal",total);
        map.put("inLine",total1);
        map.put("offLine",total2);
        if (map == null){
            LOG.error("数据为空！！！");
        }
        return new ResultGenerator().getSuccessResult(map);
    }





}
