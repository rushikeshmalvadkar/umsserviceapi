package com.rm.ums.auth.repositories;

import com.rm.ums.url.entities.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UrlRepository extends JpaRepository<UrlEntity, Long>, JpaSpecificationExecutor<UrlEntity> {
    @Query("""
            select u from UrlEntity u
            where u.urlStatus.id = :statusId
            and u.deleteFlag=false                        
            """)
    List<UrlEntity> fetchUrls(@Param("statusId") Long statusId);

    boolean existsBySlug(String slug);
}
