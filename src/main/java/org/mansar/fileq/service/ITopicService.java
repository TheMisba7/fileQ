package org.mansar.fileq.service;

import org.mansar.fileq.dto.CompleteRequest;
import org.mansar.fileq.dto.FileResource;
import org.mansar.fileq.dto.PullResponse;
import org.mansar.fileq.dto.PushResponse;
import org.mansar.fileq.model.FileType;
import org.mansar.fileq.model.Topic;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ITopicService {

    Topic create(String name, FileType... types);
    PushResponse pushItem(String topic, MultipartFile file, Map<String, String> metaData);
    PullResponse pullNext(String topic);

    FileResource download(String itemId);

    void complete(CompleteRequest completeRequest);
}
