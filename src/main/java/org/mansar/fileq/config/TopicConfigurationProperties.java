package org.mansar.fileq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "app.topics")
@Data
public class TopicConfigurationProperties {

    private boolean autoCreate = false;
    private List<TopicConfig> startup = new ArrayList<>();

    @Data
    public static class TopicConfig {
        private String name;
        private List<String> allowedTypes = new ArrayList<>();
        private String description;
        private boolean enabled = true;
    }
}