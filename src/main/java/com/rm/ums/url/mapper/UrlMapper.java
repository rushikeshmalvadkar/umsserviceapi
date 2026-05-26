package com.rm.ums.url.mapper;

import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.model.response.FetchUrlResponseData;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UrlMapper {

    List<FetchUrlResponseData> toFetchUrlResponseData(List<UrlEntity> urls);
}
