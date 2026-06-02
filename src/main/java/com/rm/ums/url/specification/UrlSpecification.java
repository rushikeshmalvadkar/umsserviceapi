package com.rm.ums.url.specification;

import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.model.request.FetchUrlsRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class UrlSpecification {

    public static Specification<UrlEntity> byFilter(FetchUrlsRequest request, LoggedInUser loggedInUser) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("createdBy").get("id"),loggedInUser.userId()));
            predicates.add(cb.equal(root.get("deleteFlag"), false));
            if (request.hasUrlStatusId()) {
                predicates.add(cb.equal(root.get("urlStatus").get("id"),request.urlStatusId()));
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
