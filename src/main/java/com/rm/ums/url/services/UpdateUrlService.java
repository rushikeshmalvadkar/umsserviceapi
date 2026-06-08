package com.rm.ums.url.services;

import com.rm.ums.common.entities.HeaderConfigEntity;
import com.rm.ums.common.entities.HeaderMappingEntity;
import com.rm.ums.common.model.response.CustomResponse;
import com.rm.ums.common.model.response.dto.EntityUpdateInput;
import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.common.repositories.HeaderMappingRepository;
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

    private final HeaderMappingRepository headerMappingRepo;
    private final GenericUpdater genericUpdater;

    @Transactional
    public CustomResponse updateUrl(UpdateUrlRequest updateUrlRequest, LoggedInUser loggedInUser) {
        HeaderMappingEntity headerMappingEntity = headerMappingRepo.findByIdOrThrow(updateUrlRequest.headerMappingId());
        HeaderConfigEntity headerConfig = headerMappingEntity.getHeaderConfig();
        EntityUpdateInput entityUpdateInput = EntityUpdateInput.from(headerConfig, updateUrlRequest.recordId(), updateUrlRequest.value(), loggedInUser);
        genericUpdater.update(entityUpdateInput);
        return CustomResponse.success(Map.of("id", updateUrlRequest.recordId()), UPDATED_SUCCESSFULLY);
    }


}
