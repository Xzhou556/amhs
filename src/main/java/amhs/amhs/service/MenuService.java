package amhs.amhs.service;

import amhs.amhs.dao.MenuDao;
import amhs.amhs.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface MenuService {

    Integer update(Menu menu);

    Long getTotal(Map<String,Object> map);

    List<Menu> list(Map<String,Object> map, Integer page, Integer pageSize);
}
