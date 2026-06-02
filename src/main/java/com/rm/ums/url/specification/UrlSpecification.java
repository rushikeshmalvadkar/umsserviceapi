package com.rm.ums.url.specification;

import com.rm.ums.common.model.response.dto.LoggedInUser;
import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.model.request.FetchUrlsRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UrlSpecification {

    public static Specification<UrlEntity> fetchUrls(FetchUrlsRequest fetchUrlsRequest, LoggedInUser loggedInUser) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = buildUrlPredicates(fetchUrlsRequest, loggedInUser, root, query, criteriaBuilder);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static List<Predicate> buildUrlPredicates(FetchUrlsRequest fetchUrlsRequest, LoggedInUser loggedInUser, Root<UrlEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (hasUrlStatus(fetchUrlsRequest)) {
            predicates.add(criteriaBuilder.equal(root.get("urlStatus").get("id"), fetchUrlsRequest.urlStatusId()));
        }
        predicates.add(criteriaBuilder.equal(root.get("deleteFlag"), false));
        predicates.add(criteriaBuilder.equal(root.get("createdBy").get("id"),loggedInUser.userId()));
        return predicates;
    }

    private static boolean hasUrlStatus(FetchUrlsRequest fetchUrlsRequest) {
        return Objects.nonNull(fetchUrlsRequest.urlStatusId());
    }
}
