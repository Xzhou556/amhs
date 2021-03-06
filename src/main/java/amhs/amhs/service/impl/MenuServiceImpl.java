package amhs.amhs.service.impl;

import amhs.amhs.dao.MenuDao;
import amhs.amhs.entity.Menu;
import amhs.amhs.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuDao menuDao;
    /**
     * @param curr      当前更新的数据
     * @param origin   源数据  以前的数据
     * @return  curr
     */
    public  Menu replace(Menu curr,Menu origin){
        if (curr.getpId()==null){
            curr.setpId(origin.getpId());
        }
        if(curr.getName()==null){
            curr.setName(origin.getName());
        }
        if(curr.getUrl()==null){
            curr.setUrl(origin.getUrl());
        }
        if(curr.getState()==null){
            curr.setState(origin.getState());
        }
        if(curr.getIcon()==null){
            curr.setIcon(origin.getIcon());
        }


        return curr;
    }
    @Override
    public Integer update(Menu menu) {
        Menu origin = menuDao.findId(menu.getMenuId());
        menu=replace(menu,origin);
        menuDao.save(menu);
        return 1;
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
      Long count =   menuDao.count(new Specification<Menu>() {
            @Override
            public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate conjunction = cb.conjunction();
                // 加入 等于 divId  父节点
                if (map.get("pId") != null){
                    conjunction.getExpressions().add(cb.equal(root.get("pId"),map.get("pId")));
                }

                return conjunction;
            }
        });
        return count;
    }

    @Override
    public List<Menu> list(Map<String, Object> map, Integer page, Integer pageSize) {
        Pageable pageable  = PageRequest.of(page, pageSize, Sort.Direction.ASC, "menuId");
        Page<Menu> pages =menuDao.findAll(new Specification<Menu>() {
            @Override
            public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (map.get("pId") != null) {
                    predicate.getExpressions().add(cb.equal(root.get("pId"), map.get("pId")));
                }
                return predicate;
            }
        },pageable);
        return pages.getContent();
    }

    @Override
    public List<Menu> list(Map<String, Object> map) {
        List<Menu> all = menuDao.findAll(new Specification<Menu>() {
            @Override
            public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (map.get("pId") != null) {
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("pId"), map.get("pId")));
                }
                return predicate;
            }
        });
        return  all;
    }


}
