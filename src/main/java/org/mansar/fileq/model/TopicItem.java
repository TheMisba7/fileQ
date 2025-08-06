package org.mansar.fileq.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "topic_items")
@Table(name = "topic_items")
@Getter @Setter
public class TopicItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "VARCHAR(36)")
    private String id;
    @Column(nullable = false)
    private String filename;
    @Column(name="original_filename", nullable = false)
    private String originalFilename;
    @Column(name="file_path", nullable = false)
    private String filePath;
    @Column(name="file_size", nullable = false)
    private Long fileSize;
    @Column(name = "content_type", nullable = false)
    private String contentType;
    @Column(nullable = false)
    private ItemStatus status;
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "processing_started_at")
    private LocalDateTime processingStartedAt;

    @Column(name = "processing_completed_at")
    private LocalDateTime processingCompletedAt;
}
