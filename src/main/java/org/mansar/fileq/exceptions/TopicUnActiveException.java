package org.mansar.fileq.exceptions;

public class TopicUnActiveException extends RuntimeException {

    public TopicUnActiveException(String topic) {
        super("topic " + topic + " is not active");
    }
}
