package org.mansar.fileq.service.impl;

import org.mansar.fileq.service.IStorageService;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class LocalStorageImpl implements IStorageService {
    @Override
    public String upload(String des, byte[] content, String contentType) {
        return null;
    }

    @Override
    public File download(String path) {
        return null;
    }
}
