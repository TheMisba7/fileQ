package org.mansar.fileq.exceptions;

public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(String topicName) {
        super("Could not find topic with name " + topicName);
    }
}
