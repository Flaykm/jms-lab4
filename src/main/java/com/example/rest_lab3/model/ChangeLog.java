package com.example.rest_lab3.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ChangeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityName;
    private Long entityId;
    private String changeType;

    @Column(columnDefinition = "TEXT")
    private String changeDetails;

    private LocalDateTime changeTimestamp = LocalDateTime.now();

    public ChangeLog() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEntityName() { return entityName; }
    public void setEntityName(String entityName) { this.entityName = entityName; }

    public Long getEntityId() { return entityId; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }

    public String getChangeType() { return changeType; }
    public void setChangeType(String changeType) { this.changeType = changeType; }

    public String getChangeDetails() { return changeDetails; }
    public void setChangeDetails(String changeDetails) { this.changeDetails = changeDetails; }

    public LocalDateTime getChangeTimestamp() { return changeTimestamp; }
    public void setChangeTimestamp(LocalDateTime changeTimestamp) { this.changeTimestamp = changeTimestamp; }
}
