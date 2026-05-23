package com.rm.ums.common.model.response.dto;

public record LoggedInUser(Long userId, Long roleId, String device) {
}
