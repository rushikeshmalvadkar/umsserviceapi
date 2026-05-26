package com.rm.ums.common.mapper;

import com.rm.ums.common.entities.HeaderMappingEntity;
import com.rm.ums.common.model.response.HeaderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HeaderMapper {

    @Mapping(source = "headerConfig.id", target = "id")
    @Mapping(source = "headerConfig.name", target = "displayName")
    @Mapping(source = "headerConfig.mappingName", target = "mappingName")
    @Mapping(source = "headerConfig.headerType", target = "headerType")
    @Mapping(source = "id", target = "headerMappingId")
    @Mapping(source = "headerConfig.filterable", target = "filterable")
    @Mapping(source = "headerConfig.sortable", target = "sortable")
    @Mapping(source = "headerConfig.optionSource.mappingName", target = "optionSource")
    HeaderResponse toHeaderResponse(HeaderMappingEntity headerMapping);

    List<HeaderResponse>  toHeaderResponseList(List<HeaderMappingEntity> entities);

}
