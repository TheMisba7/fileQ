package org.mansar.fileq.dto;

import lombok.Getter;
import lombok.Setter;
import org.mansar.fileq.model.TopicItem;

import java.time.LocalDateTime;

@Getter @Setter
public class PullResponse {
    private String id;
    private Long fileSize;
    private String contentType;
    private String topic;
    private String status;
    private String downloadUrl;
    private LocalDateTime uploadedAt;


    public static PullResponse fromTopicItem(TopicItem topicItem) {
        PullResponse pullResponse = new PullResponse();
        pullResponse.setId(topicItem.getId());
        pullResponse.setFileSize(topicItem.getFileSize());
        pullResponse.setTopic(topicItem.getTopic().getName());
        pullResponse.setStatus(topicItem.getStatus().name());
        pullResponse.setUploadedAt(topicItem.getCreatedAt());
        pullResponse.setDownloadUrl("/api/consumer/download/" + topicItem.getId());
        return pullResponse;
    }
}
