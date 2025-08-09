package org.mansar.fileq;

import org.mansar.fileq.config.TopicConfigurationProperties;
import org.mansar.fileq.dao.TopicDao;
import org.mansar.fileq.model.FileType;
import org.mansar.fileq.model.Topic;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TopicInitializer implements ApplicationRunner {
    private final TopicDao topicDao;
    private final TopicConfigurationProperties topicConfigurationProperties;

    public TopicInitializer(TopicDao topicDao,
                            TopicConfigurationProperties topicConfigurationProperties) {
        this.topicDao = topicDao;
        this.topicConfigurationProperties = topicConfigurationProperties;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (topicConfigurationProperties.isAutoCreate())
            this.initTopics();
    }

    private void initTopics() {
        for (TopicConfigurationProperties.TopicConfig topicConfig: topicConfigurationProperties.getStartup()) {
            Optional<Topic> byName = topicDao.findByName(topicConfig.getName());
            if (byName.isPresent()) continue;

            Topic topic = new Topic();
            topic.setName(topicConfig.getName());
            if (topicConfig.getAllowedTypes() != null && !topicConfig.getAllowedTypes().isEmpty()) {
                List<FileType> types = new ArrayList<>();
                for (String type: topicConfig.getAllowedTypes()) {
                    FileType fileType = FileType.fromValue(type);
                    if (fileType != null)
                        types.add(fileType);
                }

                topic.setAllowedTypes(types.toArray(new FileType[0]));
            }

            topicDao.save(topic);
        }
    }
}
