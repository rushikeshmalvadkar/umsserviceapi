package com.rm.ums.url.validater;

import com.rm.ums.common.exceptions.UmsException;
import com.rm.ums.url.entities.UrlEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class UrlExpirationTimeValidator {


    public void validate(Instant startAt, Instant expireAt) {

        if (startDateMissing(startAt, expireAt)) {
            throw new UmsException("Start date is required", HttpStatus.BAD_REQUEST);
        }
        if (expirDateMissing(startAt, expireAt)) {
            throw new UmsException("Expiration date is required", HttpStatus.BAD_REQUEST);
        }
        if (isAfter(startAt, expireAt)) {
            throw new UmsException(
                    "Start date should not be after expiration date",
                    HttpStatus.BAD_REQUEST
            );
        }

    }

    public boolean isExpired(UrlEntity url) {


        if (url.getExpireAt().isBefore(Instant.now())) {
            return true;
        }
        return true;
    }

    public boolean isShortUrlNotAvailableYet(UrlEntity url) {
        return url.getStartAt().isAfter(Instant.now());
    }

    public boolean hasNoExpirationTime(UrlEntity url) {
        return url.getStartAt() == null && url.getExpireAt() == null;
    }

    private static boolean isAfter(Instant startAt, Instant expireAt) {
        return startAt.isAfter(expireAt);
    }

    private static boolean expirDateMissing(Instant startAt, Instant expireAt) {
        return expireAt == null && startAt != null;
    }

    private static boolean startDateMissing(Instant startAt, Instant expireAt) {
        return startAt == null && expireAt != null;
    }
}
