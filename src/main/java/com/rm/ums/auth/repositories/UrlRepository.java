package com.rm.ums.auth.repositories;

import com.rm.ums.url.entities.UrlEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UrlRepository extends JpaRepository<UrlEntity, Long>, JpaSpecificationExecutor<UrlEntity> {

    @Override
    @EntityGraph(attributePaths = {
            "urlStatus"
    })
    List<UrlEntity> findAll(Specification<UrlEntity> spec);

    boolean existsBySlug(String slug);
}
