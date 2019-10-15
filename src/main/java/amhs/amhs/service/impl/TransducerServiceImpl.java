package amhs.amhs.service.impl;

import amhs.amhs.dao.TransducerDao;
import amhs.amhs.entity.Transducer;
import amhs.amhs.service.TransducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TransducerServiceImpl implements TransducerService {

    @Autowired
    TransducerDao transducerDao;



    @Override
    public int findInLine() {
        return transducerDao.findInLine();
    }

    @Override
    public int findOffLine() {
        return transducerDao.findOffLine();
    }

    @Override
    public Page<Transducer> findLikeNameByPage(Integer pageNum, Integer pageSize, Transducer transducer) {
        if (pageNum==null||pageNum==0){
            pageNum=1;
        }
        if (pageSize==null||pageSize==0){
            pageSize=15;
        }
        PageRequest of = PageRequest.of(pageNum, pageSize);
     //   transducerDao.findLikeNameByPage(transdu)
        return null;
    }

    @Override
    public Page<Transducer> findT(Integer pageNum, Integer pageSize) {
        if (pageNum==null||pageNum==0){
            pageNum=1;
        }
        if (pageSize==null||pageSize==0){
            pageSize=15;
        }
        PageRequest of = PageRequest.of(pageNum - 1, pageSize);
        Page<Transducer> transducers = transducerDao.findAll(of);
        return transducers;
    }


    public  Transducer replace(Transducer curr,Transducer origin){
        if (curr.getTransducerId()==null){
            curr.setTransducerId(origin.getTransducerId());
        }
        if (curr.getDeviceId()==null){
            curr.setDeviceId(origin.getDeviceId());
        }
        if (curr.getDeviceNumber() == null) {
            curr.setDeviceNumber(origin.getDeviceNumber());
        }
        if (curr.getThreshold() == null) {
            curr.setThreshold(origin.getThreshold());
        }
        if (curr.getLiquidLevel() == null) {
            curr.setLiquidLevel(origin.getLiquidLevel());
        }
        if (curr.getCrc() == null) {
            curr.setCrc(origin.getCrc());
        }
        if (curr.getCollectTime() == null) {
            curr.setCollectTime(origin.getCollectTime());
        }
        if (curr.getTemp() == null) {
            curr.setTemp(origin.getTemp());
        }
        if (curr.getLongitude() == null) {
            curr.setLongitude(origin.getLongitude());
        }
        if (curr.getLatitude() == null) {
            curr.setLatitude(origin.getLatitude());
        }
        if (curr.getExpirationDate() == null) {
            curr.setExpirationDate(origin.getExpirationDate());
        }
        if (curr.getBatteryPercentage() == null) {
            curr.setBatteryPercentage(origin.getBatteryPercentage());
        }
        if (curr.getRxLev() == null) {
            curr.setRxLev(origin.getRxLev());
        }
        if (curr.getNoisePower() == null) {
            curr.setNoisePower(origin.getNoisePower());
        }
        if (curr.getDataWarm() == null) {
            curr.setDataWarm(origin.getDataWarm());
        }
        if (curr.getDataType() == null) {
            curr.setDataType(origin.getDataType());
        }
        if (curr.getCreateDateTime() == null) {
            curr.setCreateDateTime(origin.getCreateDateTime());
        }
        if (curr.getFactory() == null) {
            curr.setFactory(origin.getFactory());
        }

        return curr;
    }

    @Override
    public Integer update(Transducer transducer) {
        Transducer origin = transducerDao.findId(transducer.getDeviceId());
        transducer=  replace(transducer,origin);
        transducerDao.save(transducer);
        return 1;
    }

    @Override
    public List<Transducer> list(Map<String, Object> map, Integer page, Integer pageSize) {
        Pageable pageable=new PageRequest(page, pageSize, Sort.Direction.DESC);
        Page<Transducer> list = transducerDao.findAll(pageable);
        List<Transducer> transducers = list.getContent();//拿到list集合
        return transducers;//拿到list集合

    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        long count = transducerDao.count();
        return count;
    }
}
