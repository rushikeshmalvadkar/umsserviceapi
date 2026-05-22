package com.rm.ums.common.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RoleEntity extends AbstractIdEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
}
