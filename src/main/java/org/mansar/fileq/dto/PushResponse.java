package org.mansar.fileq.dto;

import lombok.Getter;
import lombok.Setter;
import org.mansar.fileq.model.TopicItem;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter @Getter
public class PushResponse {
    private UUID id;
    private String filename;
    private String originalFilename;
    private Long fileSize;
    private String contentType;
    private String topic;
    private String status;
    private LocalDateTime uploadedAt;


    public static PushResponse fromTopicItem(TopicItem topicItem) {
        PushResponse pushResponse = new PushResponse();
        pushResponse.setId(topicItem.getId());
        pushResponse.setFilename(topicItem.getFilename());
        pushResponse.setOriginalFilename(topicItem.getOriginalFilename());
        pushResponse.setFileSize(topicItem.getFileSize());
        pushResponse.setTopic(topicItem.getTopic().getName());
        pushResponse.setUploadedAt(topicItem.getCreatedAt());
        return pushResponse;
    }
}
