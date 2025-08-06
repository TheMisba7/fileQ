package org.mansar.fileq.dao;

import org.mansar.fileq.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicDao extends JpaRepository<Topic, String> {
    Optional<Topic> findByName(String name);
}
