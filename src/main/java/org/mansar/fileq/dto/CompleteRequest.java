package org.mansar.fileq.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompleteRequest {
    private String topicItemId;
    private Boolean isSuccess;
    private String error;
}
