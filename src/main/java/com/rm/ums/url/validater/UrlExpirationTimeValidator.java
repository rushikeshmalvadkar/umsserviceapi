package com.rm.ums.url.validater;

import com.rm.ums.common.exceptions.UmsException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class UrlExpirationTimeValidator {


    public void validate(Instant startAt, Instant expireAt) {

        if (startAt == null && expireAt != null) {
            throw new UmsException("Start date is required", HttpStatus.BAD_REQUEST);
        }
        if(expireAt == null && startAt!= null){
            throw new UmsException("Expiration date is required",HttpStatus.BAD_REQUEST);
        }
        if (startAt.isAfter(expireAt)) {
            throw new UmsException(
                    "Start date should not be after expiration date",
                    HttpStatus.BAD_REQUEST
            );
        }

    }
}
