package com.rm.ums.common.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "header_mapping")
@Getter
@Setter
public class HeaderMappingEntity extends AbstractAuditEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "header_config_id", nullable = false)
    private HeaderConfigEntity headerConfig;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_menu_id", nullable = false)
    private RoleMenuEntity roleMenu;

    @Column(name = "editable", nullable = false)
    private boolean editable;

    @Column(name = "display_order", nullable = false)
    private BigDecimal displayOrder;

}
