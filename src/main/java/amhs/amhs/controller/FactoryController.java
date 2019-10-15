package amhs.amhs.controller;

import amhs.amhs.dao.FactoryDao;
import amhs.amhs.entity.Factory;
import amhs.amhs.entity.vo.RestResult;
import amhs.amhs.entity.vo.ResultGenerator;
import amhs.amhs.service.FactoryService;
import amhs.amhs.service.TransducerService;
import amhs.amhs.utils.ReadExcelUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@CrossOrigin
@RequestMapping("/factory")
@RestController
@Api(value = "工厂api",tags = "工厂api")
public class FactoryController {
    @Autowired
    FactoryService factoryService;

    @Autowired
    FactoryDao factoryDao;



    @GetMapping("/findByPage")
    @ApiOperation(value = "模糊分页查询",notes = "根据姓名模糊查询并分页")
    public  RestResult findByPage(Integer pageNum, Integer pageSize, Factory factory){
        if (pageNum==0||pageNum==null)
        {pageNum=1;}
        Page<Factory> likeNameByPage = factoryService.findLikeNameByPage(pageNum, pageSize, factory);
        return new ResultGenerator().getSuccessResult(likeNameByPage);
    }

    @GetMapping("/findAllFactory")
    @ApiOperation(value = "遍历所有工厂",notes = "遍历所有工厂")
    public  RestResult findAllFactory(Integer pageNum, Integer pageSize){
        if (pageNum==0||pageNum==null)
        {pageNum=1;}
        Map<String,Object> map = new HashMap<>();
        Page<Factory> all = factoryService.findAll(pageNum,pageSize);
        List<Factory> content = all.getContent();
        long total = factoryDao.count();
        map.put("total",total);
        map.put("content",content);
        return new ResultGenerator().getSuccessResult(map);
    }

    @GetMapping("/factoryDetail")
    @ApiOperation(value = "单个工厂",notes = "单个工厂")
    public  RestResult factoryDetail(@RequestParam(value = "factoryId" ,required = false)Integer factoryId){
        Factory factory = factoryDao.findId(factoryId);
        return new ResultGenerator().getSuccessResult(factory);
    }

    @DeleteMapping("/deleteFactory")
    @ApiOperation(value = "删除工厂",notes = "删除工厂")
    public RestResult deleteFactory(@RequestParam String ids){
        String[] idsStr = ids.split(",");
        for (int i=0;i<idsStr.length;i++){
            factoryDao.deleteById(Integer.parseInt(idsStr[i]));
        }
        return  new ResultGenerator().getSuccessResult();
    }

    @PutMapping("/updateFactory")
    @ApiOperation(value = "修改工厂",notes = "修改工厂")
    public RestResult updateFactory(@RequestBody Factory factory){
        if (factory == null) {
            return new ResultGenerator().getFailResult("数据不能为空");
        } else {

            factoryService.update(factory);
            return new ResultGenerator().getSuccessResult();
        }
    }
    @ApiOperation(value = "导入")
    @PostMapping("/importFile")
    public RestResult importFile(@RequestParam(value = "file" ,required = false) MultipartFile file) throws Exception {

        List<String[]> list = ReadExcelUtil.readExcel(file);

        System.out.println(list.toString());
        return new ResultGenerator().getSuccessResult(list);
    }

    //处理文件上传
    @ApiOperation(value = "多个文件上传" ,notes = "多个文件上传")
    @PostMapping(value = "/uploadList")
    public RestResult uploadList(@RequestParam("files") MultipartFile[] files, MultipartHttpServletRequest request) {
        List<MultipartFile> files1 = request.getFiles("file");

        for (MultipartFile file : files1) {
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;
            String filePath = "E://txt//";
            File dest = new File(filePath + fileName);

            if (file.isEmpty()) {
                return new ResultGenerator().getFailResult("文件不能为空");
            }
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();

                try {
                    file.transferTo(dest);

                } catch (IOException e) {
                    e.printStackTrace();
                    return  new ResultGenerator().getFailResult("文件上传失败！");
                }
                //返回json



            }
        }
        return new ResultGenerator().getSuccessResult();
    }

}
