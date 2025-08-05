package org.mansar.fileq.dao;

import org.mansar.fileq.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TopicDao extends JpaRepository<Topic, UUID> {
    Optional<Topic> findByName(String name);
}
