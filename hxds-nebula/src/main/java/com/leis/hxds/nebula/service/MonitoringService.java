package com.leis.hxds.nebula.service;

import org.springframework.web.multipart.MultipartFile;

public interface MonitoringService {

    void monitoring(MultipartFile file, String name, String text);
}
