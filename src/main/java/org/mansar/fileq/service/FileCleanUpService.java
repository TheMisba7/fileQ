package org.mansar.fileq.service;

import org.mansar.fileq.dao.TopicItemDao;
import org.mansar.fileq.model.TopicItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileCleanUpService {
    private final TopicItemDao topicItemDao;
    private final IStorageService storageService;

    @Value("${app.cleanup_days}")
    private int cleaningDays = 15;

    public FileCleanUpService(TopicItemDao topicItemDao, IStorageService storageService) {
        this.topicItemDao = topicItemDao;
        this.storageService = storageService;
    }


    @Scheduled(cron = "${app.cleanup.cron:0 0 2 1 * ?}")
    public void cleanup() {
        List<TopicItem> toCleanup = topicItemDao.findAllByCreatedAtBefore(LocalDateTime.now().minusDays(15));
        for (TopicItem item: toCleanup) {
            boolean delete = storageService.delete(item.getFilePath());
            if (delete)
                topicItemDao.deleteById(item.getId());
        }
    }
}
