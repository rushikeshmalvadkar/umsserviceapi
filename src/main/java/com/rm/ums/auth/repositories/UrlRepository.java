package com.rm.ums.auth.repositories;

import com.rm.ums.url.entities.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlEntity,Long> {
}
