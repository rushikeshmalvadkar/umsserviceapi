package com.rm.ums.common.helpers;

import com.rm.ums.common.mapper.HeaderMapper;
import com.rm.ums.common.model.response.HeaderResponse;
import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.common.repositories.HeaderMappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class HeaderHelper {

    private final HeaderMappingRepository headerMappingRepo;
    private final HeaderMapper headerMapper;

    public List<HeaderResponse> findHeaders(LoggedInUser loggedInUser, Long menuId) {
        List<HeaderResponse> headerMappings = headerMapper.toHeaderResponseList(headerMappingRepo.findHeaderMapping(loggedInUser.roleId(), menuId));
        log.debug("Total Headers :: {} for role id :: {} and menu id :: {}", headerMappings.size(), loggedInUser.roleId(), menuId);
        return headerMappings;
    }
}
