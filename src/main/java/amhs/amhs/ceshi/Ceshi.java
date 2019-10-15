package amhs.amhs.ceshi;

import amhs.amhs.dao.FactoryDao;
import amhs.amhs.dao.UserInfoDao;
import amhs.amhs.entity.Factory;
import amhs.amhs.entity.UserInfo;
import amhs.amhs.service.FactoryService;
import amhs.amhs.service.UserInfoService;
import amhs.amhs.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Ceshi {

    @Autowired
    UserInfoService userInfoService;
    @Autowired
    FactoryService factoryService;

    @Autowired
    FactoryDao factoryDao;
@Autowired
UserInfoDao userInfoDao;

    @Test
    public void t2(){
        int i  = factoryService.totalFactory();
        System.out.println(i);
    }



    @Test
    public void t4(){
       List<Factory> f =  factoryService.findByFactoryNameContaining("苏");
       for (Factory factory:f){
           System.out.println(factory);
       }


    }

    @Test
    public  void t5(){
        int i = factoryService.totalFactory();
        System.out.println(i);
    }

    @Test
    public  void t6(){
       boolean b = false;
       if (true==b){
           System.out.println("dsd");
       }else {
           System.out.println("dsddsdsd");
       }

    }


}
