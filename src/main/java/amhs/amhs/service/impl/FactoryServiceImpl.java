package amhs.amhs.service.impl;

import amhs.amhs.dao.FactoryDao;
import amhs.amhs.entity.Factory;
import amhs.amhs.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FactoryServiceImpl implements FactoryService {

    @Autowired
    FactoryDao factoryDao;


    @Override
    public int totalFactory() {
        return factoryDao.totalFactory();
    }

    @Override
    public List<Factory> findByFactoryNameContaining(String factoryName) {
        return factoryDao.findByFactoryNameContaining(factoryName);
    }

    @Override
    public Page<Factory> findLikeNameByPage(Integer pageNum, Integer pageSize, Factory factory) {
        if (pageNum==null||pageNum==0)
        {pageNum=1;}
        if (pageSize==null||pageSize==0){
            pageSize=15;
        }
        PageRequest of = PageRequest.of(pageNum - 1, pageSize);
        Page<Factory> likeNameByPage = factoryDao.findLikeNameByPage(factory.getFactoryName() == ""|| factory.getFactoryName() == null ? "" : factory.getFactoryName(), of);
        return likeNameByPage;
    }

    @Override
    public Page<Factory> findAll(Integer pageNum, Integer pageSize) {
        if (pageNum==null||pageNum==0)
        {pageNum=1;}
        if (pageSize==null||pageSize==0){
            pageSize=15;
        }
        PageRequest of = PageRequest.of(pageNum - 1, pageSize);
        Page<Factory> all = factoryDao.findAll(of);
        return all;
    }

    public  Factory replace(Factory curr,Factory origin){
        if (curr.getFactoryId()==null){
            curr.setFactoryId(origin.getFactoryId());
        }
        if(curr.getFactoryName()==null){
            curr.setFactoryName(origin.getFactoryName());
        }
        if(curr.getAddress()==null){
            curr.setAddress(origin.getAddress());
        }
        if(curr.getPhone()==null){
            curr.setPhone(origin.getPhone());
        }
        if(curr.getDescription()==null){
            curr.setDescription(origin.getDescription());
        }
        if(curr.getLatitude()==null){
            curr.setLatitude(origin.getLatitude());
        }
        if(curr.getLongitude()==null){
            curr.setLongitude(origin.getLongitude());
        }
        if(curr.getFactoryType()==null){
            curr.setFactoryType(origin.getFactoryType());
        }
        if(curr.getTransducers()==null){
            curr.setTransducers(origin.getTransducers());
        }
        if(curr.getCreateDateTime()==null){
            curr.setCreateDateTime(origin.getCreateDateTime());
        }
        if(curr.getUpdateDateTime()==null){
            curr.setUpdateDateTime(origin.getUpdateDateTime());
        }
        if(curr.getLeader()==null){
            curr.setLeader(origin.getLeader());
        }
        if(curr.getTel()==null){
            curr.setTel(origin.getTel());
        }
        if(curr.getProvince()==null){
            curr.setProvince(origin.getProvince());
        }

        return curr;
    }

    @Override
    public Integer update(Factory factory) {
        Factory origin = factoryDao.findId(factory.getFactoryId());
       factory= replace(factory,origin);
       factoryDao.save(factory);
        return 1;
    }
}
