package com.rm.ums.common.repositories;

import com.rm.ums.common.entities.HeaderConfigEntity;
import com.rm.ums.common.exceptions.UmsException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public interface HeaderConfigRepository extends JpaRepository<HeaderConfigEntity, Long> {
    Optional<HeaderConfigEntity> findByIdAndDeleteFlagIsFalse(Long id);

    default HeaderConfigEntity findByIdOrThrow(Long id){
        return findByIdAndDeleteFlagIsFalse(id)
                .orElseThrow(() -> new UmsException("Header config not found", HttpStatus.NOT_FOUND));

    }
}
