package com.rm.ums.common.repositories;

import com.rm.ums.common.entities.HeaderConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeaderConfigRepository extends JpaRepository<HeaderConfigEntity, Long> {
    Optional<HeaderConfigEntity> findByIdAndDeleteFlagIsFalse(Long id);

    default HeaderConfigEntity findByIdOrThrow(Long id){
        return findByIdAndDeleteFlagIsFalse(id)
                .orElseThrow(() -> new RuntimeException("Header config not found"));
    }
}
