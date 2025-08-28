package org.mansar.fileq.service;

import org.mansar.fileq.dto.CompleteRequest;
import org.mansar.fileq.dto.FileResource;
import org.mansar.fileq.dto.PullResponse;
import org.mansar.fileq.dto.PushResponse;
import org.mansar.fileq.dto.TopicItemDTO;
import org.mansar.fileq.model.FileType;
import org.mansar.fileq.model.Topic;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ITopicService {

    Topic create(String name, FileType... types);
    PushResponse pushItem(String topic, MultipartFile file, Map<String, String> metaData);
    PullResponse pullNext(String topic);

    FileResource download(String itemId);

    void complete(CompleteRequest completeRequest);

    List<TopicItemDTO> listItems(Optional<String> topic);

    void cancel(String topicItemId, String reason);
}
