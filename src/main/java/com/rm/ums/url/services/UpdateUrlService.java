package com.rm.ums.url.services;

import com.rm.ums.common.aspects.EditPermissionCheck;
import com.rm.ums.common.entities.HeaderConfigEntity;
import com.rm.ums.common.enums.MenuEnum;
import com.rm.ums.common.model.response.CustomResponse;
import com.rm.ums.common.model.response.dto.EntityUpdateInput;
import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.common.repositories.HeaderConfigRepository;
import com.rm.ums.common.updater.GenericUpdater;
import com.rm.ums.url.model.request.UpdateUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.rm.ums.common.enums.UmsResponseMessageEnum.UPDATED_SUCCESSFULLY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UpdateUrlService {

    private final GenericUpdater genericUpdater;
    private final HeaderConfigRepository headerConfigRepo;

    @Transactional
    @EditPermissionCheck(MenuEnum.MY_SHORT_URLS)
    public CustomResponse updateUrl(UpdateUrlRequest updateUrlRequest, LoggedInUser loggedInUser) {
        HeaderConfigEntity headerConfig = headerConfigRepo.findByIdOrThrow(updateUrlRequest.headerConfigId());
        genericUpdater.update(prepareEntityUpdateInput(updateUrlRequest, loggedInUser, headerConfig));
        return CustomResponse.success(Map.of("id", updateUrlRequest.recordId()), UPDATED_SUCCESSFULLY);
    }

    private static EntityUpdateInput prepareEntityUpdateInput(UpdateUrlRequest updateUrlRequest, LoggedInUser loggedInUser, HeaderConfigEntity headerConfig) {
        return EntityUpdateInput.from(headerConfig, updateUrlRequest.recordId(), updateUrlRequest.value(), loggedInUser);
    }
}
