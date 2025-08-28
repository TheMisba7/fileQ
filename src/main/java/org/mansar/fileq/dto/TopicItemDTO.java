package org.mansar.fileq.dto;

import lombok.Getter;
import lombok.Setter;
import org.mansar.fileq.model.TopicItem;

import java.time.LocalDateTime;
import java.util.Map;

@Setter @Getter
public class TopicItemDTO {
    private String id;
    private String originalFilename;
    private String topic;
    private String status;
    private LocalDateTime uploadedAt;
    private LocalDateTime processingStartedAt;
    private LocalDateTime processingCompletedAt;
    private Map<String, String> metaData;
    private String error;


    public static TopicItemDTO fromTopicItem(TopicItem topicItem) {
        TopicItemDTO pullResponse = new TopicItemDTO();
        pullResponse.setId(topicItem.getId());
        pullResponse.setTopic(topicItem.getTopic().getName());
        pullResponse.setStatus(topicItem.getStatus().name());
        pullResponse.setUploadedAt(topicItem.getCreatedAt());
        pullResponse.setMetaData(topicItem.getMetaData());
        pullResponse.setProcessingCompletedAt(topicItem.getProcessingCompletedAt());
        pullResponse.setProcessingStartedAt(topicItem.getProcessingStartedAt());
        pullResponse.setError(topicItem.getError());
        pullResponse.setOriginalFilename(topicItem.getOriginalFilename());
        return pullResponse;
    }
}
