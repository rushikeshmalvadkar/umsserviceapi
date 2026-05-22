package com.rm.ums.common.repositories;

import com.rm.ums.common.entities.UrlStatusEntity;
import com.rm.ums.common.model.response.dto.KeyValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UrlStatusRepository extends JpaRepository<UrlStatusEntity, Long> {

    @Query("""
            select
                new com.rm.ums.common.model.response.dto.KeyValue
                (
                    us.id,
                    us.name
                )
            from UrlStatusEntity us
            where us.deleteFlag = false
            """)
    List<KeyValue<Long, String>> findUrlStatusList();
}