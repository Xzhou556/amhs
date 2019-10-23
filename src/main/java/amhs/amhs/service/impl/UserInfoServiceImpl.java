package amhs.amhs.service.impl;

import amhs.amhs.dao.UserInfoDao;
import amhs.amhs.entity.UserInfo;
import amhs.amhs.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoDao userInfoDao;


    @Override
    public UserInfo findByAccount(String account) {
        return userInfoDao.findByAccount(account);
    }

    @Override
    public Integer upDate(UserInfo userInfo) {
        UserInfo user = userInfoDao.findId(userInfo.getUserId());
        //把没有值的数据  换成原数据库的数据。
        userInfo = replace(userInfo, user);
        userInfoDao.save(userInfo);
        return 1;
    }

    @Override
    public Page<UserInfo> findAll(Integer pageNum, Integer pageSize) {
        if (pageNum==null||pageNum==0){
            pageNum=1;
        }
        if (pageSize==null||pageSize==0){
            pageSize=15;
        }
        PageRequest of = PageRequest.of(pageNum - 1, pageSize);
        Page<UserInfo> all = userInfoDao.findAll(of);
        return all;
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        long count = userInfoDao.count();
        return count;
    }

    public UserInfo replace(UserInfo curr,UserInfo origin){
        if (curr.getAccount() == null){
            curr.setAccount(origin.getAccount());
        }
        if (curr.getTrueName() == null){
            curr.setTrueName(origin.getTrueName());
        }
        if (curr.getMobilePhone() == null){
            curr.setMobilePhone(origin.getMobilePhone());
        }
        if (curr.getUpdateDateTime() == null){
            curr.setUpdateDateTime(origin.getUpdateDateTime());
        }
        if (curr.getRole() == null){
            curr.setRole(origin.getRole());
        }
        if (curr.getCreateDateTime() == null){
            curr.setCreateDateTime(origin.getCreateDateTime());
        }
        if (curr.getPassword() == null){
            curr.setPassword(origin.getPassword());
        }
        if (curr.getSalt() == null){
            curr.setSalt(origin.getSalt());
        }
        if (curr.getState() == null){
            curr.setState(origin.getState());
        }
        if (curr.getRemark() == null){
            curr.setRemark(origin.getRemark());
        }



        return curr;
    }
}
