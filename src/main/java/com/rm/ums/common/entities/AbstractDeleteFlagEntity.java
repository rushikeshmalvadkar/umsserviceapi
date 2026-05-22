package com.rm.ums.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractDeleteFlagEntity extends AbstractIdEntity {

    @Column(name = "delete_flag", nullable = false)
    private boolean deleteFlag;
}
