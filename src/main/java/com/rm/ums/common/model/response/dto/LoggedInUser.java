package com.rm.ums.common.model.response.dto;

public record LoggedInUser(Long userId, Long roleId, String device) {

    public static LoggedInUser of(Long userId, Long roleId, String device) {
        return new LoggedInUser(userId, roleId, device);
    }

}
