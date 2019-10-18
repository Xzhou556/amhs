package amhs.amhs.service.impl;

import amhs.amhs.dao.StandardDao;
import amhs.amhs.entity.Standard;
import amhs.amhs.service.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class StandardServiceImpl implements StandardService {
    @Autowired
    StandardDao standardDao;

    public Standard replace(Standard curr,Standard origin){
        if (curr.getStandardId() == null){
            curr.setStandardId(origin.getStandardId());
        }
        if (curr.getName() == null){
            origin.setName(curr.getName());
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
     standardDao.save(standard);
        return 1;
    }
}
