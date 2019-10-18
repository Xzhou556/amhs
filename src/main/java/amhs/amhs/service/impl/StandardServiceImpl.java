package amhs.amhs.service.impl;

import amhs.amhs.dao.StandardDao;
import amhs.amhs.entity.Standard;
import amhs.amhs.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class StandardServiceImpl implements StandardService {
    @Autowired
    StandardDao standardDao;

    public Standard replace(Standard curr,Standard origin){
        if (curr.getName() == null){
            curr.setName(origin.getName());
        }
        if (curr.getUpperLimit() == null){
            curr.setUpperLimit(origin.getUpperLimit());
        }
        if (curr.getLowerLimit() == null){
            curr.setLowerLimit(origin.getLowerLimit());
        }
        if (curr.getUnit() == null){
            curr.setUnit(origin.getUnit());
        }

        return curr;
    }


    @Override
    public Integer update(Standard standard) {
     Standard origin =    standardDao.findId(standard.getStandardId());
     standard = replace(standard,origin);
    // standardDao.deleteByStandardId(standard.getStandardId());
     standardDao.save(standard);
        return 1;
    }

    @Override
    public Page<Standard> findAll(Integer pageNum, Integer pageSize) {
        if (pageNum==null||pageNum==0){
            pageNum=1;
        }
        if (pageSize==null||pageSize==0){
            pageSize=15;
        }
        PageRequest of = PageRequest.of(pageNum - 1, pageSize);
        Page<Standard> all = standardDao.findAll(of);
        return all;
    }
}
