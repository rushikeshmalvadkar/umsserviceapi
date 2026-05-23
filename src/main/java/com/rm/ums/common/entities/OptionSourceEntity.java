package com.rm.ums.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "option_source")
public class OptionSourceEntity extends AbstractIdEntity {

    @Column(name="mapping_name",nullable = false)
    private String mappingName;
}
