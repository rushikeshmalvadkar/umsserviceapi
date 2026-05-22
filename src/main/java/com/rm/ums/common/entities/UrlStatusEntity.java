package com.rm.ums.common.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "url_status")
public class UrlStatusEntity extends AbstractAuditEntity {

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

}
