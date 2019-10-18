package amhs.amhs.service;

import amhs.amhs.entity.TransducerType;
import org.springframework.data.domain.Page;

public interface TransducerTypeService {

    Integer update(TransducerType transducerType);

    Page<TransducerType> findAll(Integer pageNum, Integer pageSize);
}
