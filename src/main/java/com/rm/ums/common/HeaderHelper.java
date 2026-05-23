package com.rm.ums.common;

import com.rm.ums.common.mapper.HeaderMapper;
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
    private final HeaderMapper headerMapper;

    public List<HeaderResponse> findHeaders(LoggedInUser loggedInUser, Long menuId) {
        return headerMapper.toHeaderResponseList(headerMappingRepo.findHeaderMapping(loggedInUser.roleId(), menuId));
    }
}
