package amhs.amhs.service.impl;

import amhs.amhs.dao.PCDDao;
import amhs.amhs.entity.PCD;
import amhs.amhs.service.PCDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class PCDServiceImpl implements PCDService {
    @Autowired
    PCDDao pcdDao;
    @Override
    public List<PCD> findPCDByPid(int pid) {
        return pcdDao.findPCDByPid(pid);
    }
}
