package org.mansar.fileq.utils;

import org.mansar.fileq.exceptions.UnSupportedFileType;
import org.mansar.fileq.model.FileType;
import org.mansar.fileq.model.Topic;
import org.springframework.web.multipart.MultipartFile;

public final class FileUtils {
    private FileUtils() {}


    public static String validateAndGetExtension(Topic topic, MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        FileType fileType = FileType.getFileType(contentType);
        if (fileType == null)
            throw new UnSupportedFileType("invalid content type");

        if (topic.getAllowedTypes() == null)
            return fileType.getExtension();

        for (FileType topicType: topic.getAllowedTypes()) {
            if (topicType.equals(fileType))
                return topicType.getExtension();
        }
        throw new UnSupportedFileType("invalid content type");
    }
}
