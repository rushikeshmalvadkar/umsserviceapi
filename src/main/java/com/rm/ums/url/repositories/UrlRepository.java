package com.rm.ums.url.repositories;

import com.rm.ums.url.entities.UrlEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity, Long>, JpaSpecificationExecutor<UrlEntity> {

    @Override
    @EntityGraph(attributePaths = {
            "urlStatus"
    })
    List<UrlEntity> findAll(Specification<UrlEntity> spec);

    @Query("""
                     SELECT u
                     FROM UrlEntity u
                     WHERE u.slug = :slug
                     and u.deleteFlag = false
            """)
    Optional<UrlEntity> findOriginalUrlBy(@Param("slug") String slug);


    boolean existsBySlug(String slug);
}
