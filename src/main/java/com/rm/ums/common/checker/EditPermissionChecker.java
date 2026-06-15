package com.rm.ums.common.checker;

import com.rm.ums.common.entities.HeaderMappingEntity;
import com.rm.ums.common.exceptions.PermissionDeniedException;
import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.common.repositories.HeaderMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EditPermissionChecker {
    private final HeaderMappingRepository headerMappingRepo;

    public void check(Long headerConfigId, Long menuId, LoggedInUser loggedInUser){
        headerMappingRepo.findHeaderMappingBy(headerConfigId, menuId, loggedInUser.roleId())
                .filter(HeaderMappingEntity::isEditable)
                .orElseThrow(PermissionDeniedException::new);
    }
}
