package amhs.amhs.service;

import amhs.amhs.entity.PCD;

import java.util.List;

public interface PCDService {
    public List<PCD> findPCDByPid(int pid);
}
