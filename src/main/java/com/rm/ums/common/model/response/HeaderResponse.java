package com.rm.ums.common.model.response;

import lombok.Data;

@Data
public class HeaderResponse {

    private Long id;
    private String displayName;
    private String mappingName;
    private String headerType;
    private Long headerMappingId;
    private boolean editable;
    private boolean filterable;
    private boolean sortable;
    private String optionSource;

}
