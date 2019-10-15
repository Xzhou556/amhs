package amhs.amhs.service;

import amhs.amhs.entity.Transducer;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface TransducerService {

    //4、查询传感器在线设备
    int findInLine();

    //5、查询传感器离线设备
    int findOffLine();

    //模糊分页查询
    Page<Transducer> findLikeNameByPage(Integer pageNum, Integer pageSize, Transducer transducer);

    Page<Transducer> findT(Integer pageNum, Integer pageSize);

    public Integer update(Transducer transducer);

    /**
     * @param map
     * @param page  从0开始
     * @param pageSize
     */
    public List<Transducer> list(Map<String,Object> map, Integer page, Integer pageSize);

    public Long getTotal(Map<String,Object> map);
}
