package org.mansar.fileq.dao;

import org.mansar.fileq.model.ItemStatus;
import org.mansar.fileq.model.TopicItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TopicItemDao extends JpaRepository<TopicItem, UUID> {

    @Query(value = "SELECT item FROM topic_items item where item.topic.id = :topicId and item.status = :satus order by item.createdAt asc limit 1")
    Optional<TopicItem> pullnext(@Param("topicId") UUID topicId, @Param("status") ItemStatus status);
}
