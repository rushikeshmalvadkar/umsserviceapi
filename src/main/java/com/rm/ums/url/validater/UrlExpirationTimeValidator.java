package com.rm.ums.url.validater;

import com.rm.ums.common.exceptions.UmsException;
import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.enums.UrlExpirationStatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UrlExpirationTimeValidator {


    public void validate(LocalDate startAt, LocalDate expireAt) {

        if (userHasGivenOnlyExpiryDateButNotStartDate(startAt, expireAt)) {
            throw new UmsException("Start date is required", HttpStatus.BAD_REQUEST);
        }
        if (userHasGivenOnlyStartDateButNotExpiryDate(startAt, expireAt)) {
            throw new UmsException("Expiration date is required", HttpStatus.BAD_REQUEST);
        }
        if (userHasGivenWrongRangeOfStartAndExpiryDate(startAt, expireAt)) {
            throw new UmsException(
                    "Start date should not be after expiration date",
                    HttpStatus.BAD_REQUEST
            );
        }

    }

    private static boolean userHasGivenWrongRangeOfStartAndExpiryDate(LocalDate startAt, LocalDate expireAt) {
        return startAt.isAfter(expireAt);
    }

    public UrlExpirationStatusEnum canAccessStatus(UrlEntity url) {
        if (isShortUrlNotAvailableYet(url.getStartAt())) {
            return UrlExpirationStatusEnum.NOT_AVAILABLE_YET;
        }
        if (isExpired(url.getExpireAt())) {
            return UrlExpirationStatusEnum.EXPIRED;
        }
        return UrlExpirationStatusEnum.AVAILABLE;
    }

    private boolean isExpired(LocalDate expireAt) {
        return expireAt.isBefore(LocalDate.now());
    }


    private boolean isShortUrlNotAvailableYet(LocalDate startAt) {
        return startAt.isAfter(LocalDate.now());
    }

    private boolean hasNoExpirationTime(LocalDate startAt, LocalDate expireAt) {
        return startAt == null && expireAt == null;
    }

    private static boolean userHasGivenOnlyStartDateButNotExpiryDate(LocalDate startAt, LocalDate expireAt) {
        return expireAt == null && startAt != null;
    }

    private static boolean userHasGivenOnlyExpiryDateButNotStartDate(LocalDate startAt, LocalDate expireAt) {
        return startAt == null && expireAt != null;
    }
}
