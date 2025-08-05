package org.mansar.fileq.service;

import org.mansar.fileq.dto.CompleteRequest;
import org.mansar.fileq.dto.PullResponse;
import org.mansar.fileq.dto.PushResponse;
import org.mansar.fileq.model.FileType;
import org.mansar.fileq.model.Topic;
import org.springframework.web.multipart.MultipartFile;

public interface ITopicService {

    Topic create(String name, FileType... types);
    PushResponse pushItem(String topic, MultipartFile file);
    PullResponse pullNext(String topic);

    void complete(CompleteRequest completeRequest);
}
