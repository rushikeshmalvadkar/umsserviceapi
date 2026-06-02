package com.rm.ums.url.mapper;

import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.model.response.CreateUrlResponse;
import com.rm.ums.url.model.response.FetchUrlsDataResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UrlMapper {

    @Mapping(source = "urlStatus.id", target = "urlStatusId")
    @Mapping(source = "urlStatus.name", target = "urlStatusName")
    @Mapping(source = "createdOn", target = "createdDate")
    FetchUrlsDataResponse toFetchUrlDataResponse(UrlEntity entity);

    List<FetchUrlsDataResponse> toFetchUrlDataResponseList(List<UrlEntity> entities);

    @Mapping(source = "urlStatus.id", target = "urlStatusId")
    @Mapping(source = "createdBy.id", target = "createdByUserId")
    CreateUrlResponse toCreateUrlResponse(UrlEntity entity);
}
