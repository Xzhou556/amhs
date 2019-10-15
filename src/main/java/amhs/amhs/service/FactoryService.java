package amhs.amhs.service;

import amhs.amhs.entity.Factory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FactoryService {

    //4、计算出工厂得总数量
    int totalFactory();

    List<Factory> findByFactoryNameContaining(String factoryName);

    //模糊分页查询
    Page<Factory> findLikeNameByPage(Integer pageNum,Integer pageSize,Factory factory);

    //
    Page<Factory> findAll(Integer pageNum,Integer pageSize);

    Integer update(Factory factory);
}
