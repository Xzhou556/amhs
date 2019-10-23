package amhs.amhs.service.impl;

import amhs.amhs.dao.TransducerTypeDao;
import amhs.amhs.entity.TransducerType;
import amhs.amhs.service.TransducerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TransducerTypeServiceImpl implements TransducerTypeService {

    @Autowired
    TransducerTypeDao transducerTypeDao;

    public TransducerType replace(TransducerType curr,TransducerType origin){

        if (curr.getName() == null){
            origin.setName(curr.getName());
        }
        if (curr.getState() == null){
            curr.setState(origin.getState());
        }

        return curr;
    }

    @Override
    public Integer update(TransducerType transducerType) {
        TransducerType origin = transducerTypeDao.findId(transducerType.getTtId());
        transducerType=replace(transducerType,origin);
        transducerTypeDao.save(transducerType);
        return 1;
    }

    @Override
    public Page<TransducerType> findAll(Integer pageNum, Integer pageSize) {

        if (pageNum==null||pageNum==0){
            pageNum=1;
        }
        if (pageSize==null||pageSize==0){
            pageSize=15;
        }
        PageRequest of = PageRequest.of(pageNum - 1, pageSize);
        Page<TransducerType> all = transducerTypeDao.findAll(of);
        return all;
    }
}
