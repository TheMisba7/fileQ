package org.mansar.fileq.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mansar.fileq.dao.TopicDao;
import org.mansar.fileq.dao.TopicItemDao;
import org.mansar.fileq.dto.CompleteRequest;
import org.mansar.fileq.dto.FileResource;
import org.mansar.fileq.dto.PullResponse;
import org.mansar.fileq.dto.PushResponse;
import org.mansar.fileq.dto.TopicItemDTO;
import org.mansar.fileq.exceptions.FileQException;
import org.mansar.fileq.exceptions.TopicNotFoundException;
import org.mansar.fileq.exceptions.TopicUnActiveException;
import org.mansar.fileq.model.FileType;
import org.mansar.fileq.model.ItemStatus;
import org.mansar.fileq.model.Topic;
import org.mansar.fileq.model.TopicItem;
import org.mansar.fileq.service.IStorageService;
import org.mansar.fileq.service.ITopicService;
import org.mansar.fileq.utils.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TopicServiceImpl implements ITopicService {

    private final TopicDao topicDao;
    private final TopicItemDao topicItemDao;
    private final IStorageService storageService;

    private Topic getTopicByName(String topicName) {
        return topicDao.findByName(topicName)
                .orElseThrow(() -> new TopicNotFoundException(topicName));
    }
    @Override
    public Topic create(String name, FileType... types) {
        Topic topic = new Topic();
        topic.setName(name);
        topic.setAllowedTypes(types);
        return topicDao.save(topic);
    }

    @Override
    public PushResponse pushItem(String topicName, MultipartFile file, Map<String, String> metaData) {
        Topic topic = getTopicByName(topicName);
        if (!topic.isActive())
            throw new TopicUnActiveException(topicName);
        String extension = FileUtils.validateAndGetExtension(topic, file);

        TopicItem topicItem = new TopicItem();
        topicItem.setTopic(topic);
        topicItem.setStatus(ItemStatus.PENDING);
        topicItem.setFileSize(file.getSize());
        topicItem.setContentType(extension);
        topicItem.setMetaData(metaData);
        topicItem.setOriginalFilename(file.getOriginalFilename());

        try {
            String fileName = storageService.upload(topicName, file.getBytes(), extension);
            topicItem.setFilename(fileName);
            topicItem.setFilePath(topicName + "/" + fileName);
            topicItem = topicItemDao.save(topicItem);

            return PushResponse.fromTopicItem(topicItem);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new FileQException("Unexpected error during file upload");
        }
    }

    @Override
    public PullResponse pullNext(String topicName) {

        Optional<TopicItem> topicItem;
        if (topicName != null) {
            Topic topic = getTopicByName(topicName);
            topicItem = topicItemDao.pullNext(topic.getId(), ItemStatus.PENDING);
        } else
            topicItem = topicItemDao.pullNext(ItemStatus.PENDING);

        return topicItem.map(PullResponse::fromTopicItem).orElse(null);
    }

    @Override
    public FileResource download(String itemId) {
        Optional<TopicItem> item = topicItemDao.findById(itemId);

        return item.map(topicItem -> {
            File download = storageService.download(topicItem.getFilePath());
            if (download != null) {
                topicItem.setStatus(ItemStatus.PROCESSING);
                topicItem.setProcessingStartedAt(LocalDateTime.now());
                topicItemDao.save(topicItem);
                return new FileResource(new FileSystemResource(download), topicItem.getOriginalFilename());
            }
            return null;
        }).orElse(null);
    }

    @Override
    public void complete(CompleteRequest completeRequest) {
        Optional<TopicItem> item = topicItemDao.findById(completeRequest.getTopicItemId());
        item.ifPresent(topicItem -> {
            topicItem.setProcessingCompletedAt(LocalDateTime.now());
            if (Boolean.TRUE.equals(completeRequest.getIsSuccess()))
                topicItem.setStatus(ItemStatus.COMPLETED);
            else if (Boolean.FALSE.equals(completeRequest.getIsSuccess()))
                topicItem.setStatus(ItemStatus.FAILED);
            topicItem.setError(completeRequest.getError());

            topicItemDao.save(topicItem);
        });
    }

    @Override
    public List<TopicItemDTO> listItems(Optional<String> topicRq) {
        List<TopicItem> items;
        if (topicRq.isPresent()) {
            items = topicItemDao.findAllByTopicName(topicRq.get());
        } else {
            items = topicItemDao.findAll();
        }

        return items.parallelStream().map(TopicItemDTO::fromTopicItem).toList();
    }

    @Override
    public void cancel(String topicItemId, String reason) {
        Optional<TopicItem> item = topicItemDao.findById(topicItemId);

        item.ifPresent(topicItem -> {
            topicItem.setStatus(ItemStatus.CANCELED);
            topicItem.setError(reason);
            topicItemDao.save(topicItem);
        });
    }
}
