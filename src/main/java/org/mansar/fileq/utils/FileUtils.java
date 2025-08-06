package org.mansar.fileq.utils;

import org.mansar.fileq.model.Topic;
import org.springframework.web.multipart.MultipartFile;

public final class FileUtils {
    private FileUtils() {}


    public static String getContentType(Topic topic, MultipartFile multipartFile) {
        int lastDotIndex = multipartFile.getOriginalFilename().lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == multipartFile.getOriginalFilename().length() - 1) {
            return "";
        }
        return multipartFile.getOriginalFilename().substring(lastDotIndex);
    }
}
