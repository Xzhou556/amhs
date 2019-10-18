package amhs.amhs.service;

import amhs.amhs.entity.Standard;
import org.springframework.data.domain.Page;

public interface StandardService {
    Integer update(Standard standard);

    Page<Standard> findAll(Integer pageNum, Integer pageSize);
}
