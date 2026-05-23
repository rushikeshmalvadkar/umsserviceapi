package com.rm.ums.common;

import com.rm.ums.common.entities.HeaderConfigEntity;
import com.rm.ums.common.entities.HeaderMappingEntity;
import com.rm.ums.common.model.response.HeaderResponse;
import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.common.repositories.HeaderMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HeaderHelper {

    private final HeaderMappingRepository headerMappingRepo;

    public List<HeaderResponse> findHeaders(LoggedInUser loggedInUser, Long menuId) {
        List<HeaderMappingEntity> headerMappings = headerMappingRepo.findHeaderMapping(loggedInUser.roleId(),menuId);
        return headerMappings.stream()
                .map(this::toHeaderResponse)
                .toList();
    }

    private HeaderResponse toHeaderResponse(HeaderMappingEntity headerMapping) {
        HeaderResponse headerResponse = new HeaderResponse();
        HeaderConfigEntity headerConfig = headerMapping.getHeaderConfig();
        headerResponse.setId(headerConfig.getId());
        headerResponse.setDisplayName(headerConfig.getName());
        headerResponse.setMappingName(headerConfig.getMappingName());
        headerResponse.setHeaderType(headerConfig.getHeaderType());
        headerResponse.setHeaderMappingId(headerMapping.getId());
        headerResponse.setEditable(headerMapping.isEditable());
        headerResponse.setFilterable(headerConfig.isFilterable());
        headerResponse.setSortable(headerConfig.isSortable());
        headerResponse.setOptionSource(headerConfig.optionSourceName());
        return headerResponse;
    }

}
