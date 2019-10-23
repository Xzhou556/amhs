package amhs.amhs.service;

import amhs.amhs.entity.ResLog;
import org.springframework.data.domain.Page;

public interface ResLogService {
    Page<ResLog> findAll(Integer pageNum, Integer pageSize);
}
