package com.rm.ums.common.entities;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@MappedSuperclass
public class AbstractAuditEntity extends AbstractDeleteFlagEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;

    @Column(name = "created_date", nullable = false)
    private ZonedDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_updated_by")
    private UserEntity lastUpdatedBy;

    @Column(name = "last_updated_date")
    private ZonedDateTime lastUpdatedDate;

}
