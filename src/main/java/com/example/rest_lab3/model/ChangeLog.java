package com.example.rest_lab3.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "change_log")
public class ChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityName;
    private Long entityId;
    private String changeType;

    @Column(columnDefinition = "TEXT")
    private String changeDetails;

    private LocalDateTime createdAt = LocalDateTime.now();

    public ChangeLog() {}

    public ChangeLog(String entityName, Long entityId, String changeType, String changeDetails) {
        this.entityName = entityName;
        this.entityId = entityId;
        this.changeType = changeType;
        this.changeDetails = changeDetails;
    }

    public Long getId() { return id; }
    public String getEntityName() { return entityName; }
    public void setEntityName(String entityName) { this.entityName = entityName; }
    public Long getEntityId() { return entityId; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }
    public String getChangeType() { return changeType; }
    public void setChangeType(String changeType) { this.changeType = changeType; }
    public String getChangeDetails() { return changeDetails; }
    public void setChangeDetails(String changeDetails) { this.changeDetails = changeDetails; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
