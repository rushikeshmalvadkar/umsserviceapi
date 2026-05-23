package com.rm.ums.common.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "menu")
public class MenuEntity extends AbstractIdEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private MenuEntity parent;

}
