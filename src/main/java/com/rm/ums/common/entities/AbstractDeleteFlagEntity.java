package com.rm.ums.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class AbstractDeleteFlagEntity extends AbstractIdEntity {

    @Column(name = "delete_flag", nullable = false)
    private boolean deleteFlag;
}
