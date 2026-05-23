package com.rm.ums.url.services;

import com.rm.ums.common.HeaderHelper;
import com.rm.ums.common.enums.MenuEnum;
import com.rm.ums.common.enums.UmsResponseMessageEnum;
import com.rm.ums.common.model.response.CustomResponse;
import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.url.model.response.OnLoadCreateUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlService {

    private final HeaderHelper headerHelper;

    public CustomResponse onLoadCreateUrl(LoggedInUser user) {
      return CustomResponse.success(prepareCreateUrlLoadResponse(user), UmsResponseMessageEnum.FETCHED_SUCCESSFULLY) ;
    }

    private OnLoadCreateUrlResponse prepareCreateUrlLoadResponse(LoggedInUser user) {
        OnLoadCreateUrlResponse onLoadCreateUrlResponse = new OnLoadCreateUrlResponse();
        onLoadCreateUrlResponse.setHeaders(headerHelper.findHeaders(user, MenuEnum.CREATE_URL.id()));
        return onLoadCreateUrlResponse;
    }
}
