package org.mansar.fileq.service;

import java.io.File;

public interface IStorageService {
    String upload(String des, byte[] content, String contentType);
    File download(String path);

    boolean delete(String path);
}
