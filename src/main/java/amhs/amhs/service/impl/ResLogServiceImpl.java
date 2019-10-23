package amhs.amhs.service.impl;

import amhs.amhs.dao.ResLogDao;
import amhs.amhs.entity.ResLog;
import amhs.amhs.service.ResLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ResLogServiceImpl implements ResLogService {
    @Autowired
    ResLogDao resLogDao;


    @Override
    public Page<ResLog> findAll(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }
        PageRequest of = PageRequest.of(pageNum - 1, pageSize);
        Page<ResLog> all = resLogDao.findAll(of);
        return all;

    }

}
