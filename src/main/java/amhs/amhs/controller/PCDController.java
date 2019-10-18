package amhs.amhs.controller;


import amhs.amhs.entity.PCD;
import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import amhs.amhs.service.PCDService;

import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RequestMapping("/pcd")
@RestController
@Api(value = "城市级联api", tags = "城市级联api")
public class PCDController {
    private static final Logger LOG = Logger.getLogger(PCDController.class);
    @Autowired
    PCDService pcdService;


    /**
     * 省市区三级联动根据pid查询
     * @param pId
     * @throws IOException
     */
    @GetMapping("/address")
    public RestResult getPCD(@RequestParam(name = "pId",required = false) Integer pId) throws IOException {


        List<PCD> data = pcdService.findPCDByPid(pId);
        if (data == null){
            LOG.error("数据不能为空");
        }
        return new ResultGenerator().getSuccessResult(data);
    }

}
