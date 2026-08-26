package com.rm.ums.url.validater;

import com.rm.ums.common.exceptions.UmsException;
import com.rm.ums.url.entities.UrlEntity;
import com.rm.ums.url.enums.UrlExpirationStatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class UrlExpirationTimeValidator {


    public void validate(Instant startAt, Instant expireAt) {

        if (startDateMissing(startAt, expireAt)) {
            throw new UmsException("Start date is required", HttpStatus.BAD_REQUEST);
        }
        if (expireDateMissing(startAt, expireAt)) {
            throw new UmsException("Expiration date is required", HttpStatus.BAD_REQUEST);
        }
        if (isAfter(startAt, expireAt)) {
            throw new UmsException(
                    "Start date should not be after expiration date",
                    HttpStatus.BAD_REQUEST
            );
        }

    }


    public UrlExpirationStatusEnum canAccessStatus(UrlEntity url){

        if(hasNoExpirationTime(url.getStartAt(),url.getExpireAt())){
            return UrlExpirationStatusEnum.AVAILABLE;
        }
        if (isShortUrlNotAvailableYet(url.getStartAt())) {
            return UrlExpirationStatusEnum.NOT_AVAILABLE_YET;
        }
        if(isExpired(url.getExpireAt())){
            return UrlExpirationStatusEnum.EXPIRED;
        }
        return UrlExpirationStatusEnum.AVAILABLE;

    }

    private boolean isExpired(Instant expireAt) {
         return expireAt.isBefore(Instant.now());
    }


    private boolean isShortUrlNotAvailableYet(Instant startAt) {
        return startAt.isAfter(Instant.now());
    }

    private boolean hasNoExpirationTime(Instant startAt, Instant expireAt) {
        return startAt == null && expireAt == null;
    }

    private static boolean isAfter(Instant startAt, Instant expireAt) {
        return startAt.isAfter(expireAt);
    }

    private static boolean expireDateMissing(Instant startAt, Instant expireAt) {
        return expireAt == null && startAt != null;
    }

    private static boolean startDateMissing(Instant startAt, Instant expireAt) {
        return startAt == null && expireAt != null;
    }
}
