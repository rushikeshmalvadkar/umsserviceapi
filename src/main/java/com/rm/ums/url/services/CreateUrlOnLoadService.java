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
public class CreateUrlOnLoadService {

    private final HeaderHelper headerHelper;

    public CustomResponse onLoad(LoggedInUser loggedInUser) {
        OnLoadCreateUrlResponse onLoadCreateUrlResponse = prepareOnLoadCreateUrlResponse(loggedInUser);
        return CustomResponse.success(onLoadCreateUrlResponse, FETCHED_SUCCESSFULLY);
    }

    private OnLoadCreateUrlResponse prepareOnLoadCreateUrlResponse(LoggedInUser loggedInUser) {
        OnLoadCreateUrlResponse onLoadCreateUrlResponse = new OnLoadCreateUrlResponse();
        onLoadCreateUrlResponse.setHeaders(headerHelper.findHeaders(loggedInUser, MenuEnum.CREATE_URL.id()));
        return onLoadCreateUrlResponse;
    }
}
