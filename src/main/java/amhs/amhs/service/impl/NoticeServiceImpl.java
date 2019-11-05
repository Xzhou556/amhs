package amhs.amhs.service.impl;

import amhs.amhs.dao.NoticeDao;
import amhs.amhs.entity.Notice;
import amhs.amhs.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeDao noticeDao;


    public Notice replace(Notice curr, Notice origin) {

        if (curr.getNoticeName() == null) {
            origin.setNoticeName(curr.getNoticeName());
        }
        if (curr.getDescription() == null) {
            curr.setDescription(origin.getDescription());
        }
        if (curr.getContent() == null) {
            curr.setContent(origin.getContent());
        }
        if (curr.getState() == null) {
            curr.setState(origin.getState());
        }
        if (curr.getFactory() == null) {
            curr.setFactory(origin.getFactory());
        }
        if (curr.getCreatetime() == null) {
            curr.setCreatetime(origin.getCreatetime());
        }
        if (curr.getKeyword() == null) {
            curr.setKeyword(origin.getKeyword());
        }
        if (curr.getFile() == null) {
            curr.setFile(origin.getFile());
        }
        if (curr.getType() == null) {
            curr.setType(origin.getType());
        }
        return curr;
    }

    @Override
    public Integer update(Notice notice) {
        Notice origin = noticeDao.findId(notice.getNoticeId());
        notice=replace(notice,origin);
        noticeDao.save(notice);
        return 1;
    }

    @Override
    public Page<Notice> findAll(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 15;
        }
        PageRequest of = PageRequest.of(pageNum - 1, pageSize);
        Page<Notice> all = noticeDao.findAll(of);
        return all;

    }
}
