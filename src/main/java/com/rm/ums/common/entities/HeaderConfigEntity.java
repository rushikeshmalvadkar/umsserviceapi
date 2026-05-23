package com.rm.ums.common.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "header_config")
@Getter
@Setter
public class HeaderConfigEntity extends AbstractAuditEntity {

    @Column(name = "header_name")
    private String name;

    @Column(name = "header_type", nullable = false)
    private String headerType;

    @Column(name = "mapping_name", nullable = false)
    private String mappingName;

    @Column(name = "mapping_table")
    private String mappingTable;

    @Column(name = "mapping_column")
    private String mappingColumn;

    @Column(name = "sortable", nullable = false)
    private boolean sortable;

    @Column(name = "filterable", nullable = false)
    private boolean filterable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_source_id")
    private OptionSourceEntity optionSource;


    public String optionSourceName() {
        if (optionSource == null) return null;
        return this.optionSource.getMappingName();
    }

}
