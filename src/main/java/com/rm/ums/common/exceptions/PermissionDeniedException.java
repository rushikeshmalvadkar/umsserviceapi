package com.rm.ums.common.exceptions;

import org.springframework.http.HttpStatus;

public class PermissionDeniedException extends UmsException{
    public PermissionDeniedException() {
        super("You don't have permission for this operation", HttpStatus.FORBIDDEN);
    }
}
