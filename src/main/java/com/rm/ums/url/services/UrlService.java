package com.rm.ums.url.services;

import com.rm.ums.common.HeaderHelper;
import com.rm.ums.common.enums.MenuEnum;
import com.rm.ums.common.model.response.CustomResponse;
import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.url.model.response.OnLoadCreateUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.rm.ums.common.enums.UmsResponseMessageEnum.FETCHED_SUCCESSFULLY;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlService {

    private final HeaderHelper headerHelper;

    public CustomResponse onLoadCreateUrl(LoggedInUser loggedInUser) {
        return CustomResponse.success(prepareCreateUrlLoadResponse(loggedInUser), FETCHED_SUCCESSFULLY);
    }

    private OnLoadCreateUrlResponse prepareCreateUrlLoadResponse(LoggedInUser loggedInUser) {
        OnLoadCreateUrlResponse onLoadCreateUrlResponse = new OnLoadCreateUrlResponse();
        onLoadCreateUrlResponse.setHeaders(headerHelper.findHeaders(loggedInUser, MenuEnum.CREATE_URL.id()));
        return onLoadCreateUrlResponse;
    }
}
