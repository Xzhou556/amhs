package amhs.amhs.service;

import amhs.amhs.entity.Factory;
import amhs.amhs.entity.UserInfo;
import org.springframework.data.domain.Page;

import java.util.Map;


public interface UserInfoService {

    UserInfo findByAccount(String account);

    Integer upDate(UserInfo userInfo);

    Page<UserInfo> findAll(Integer pageNum, Integer pageSize);

    Long getTotal(Map<String, Object> map);

}
