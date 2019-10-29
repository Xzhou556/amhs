package amhs.amhs.service;

import amhs.amhs.entity.Notice;
import org.springframework.data.domain.Page;

public interface NoticeService {

    Integer update(Notice notice);

    Page<Notice> findAll(Integer pageNum, Integer pageSize);
}
