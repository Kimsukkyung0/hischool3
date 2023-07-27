package com.green.secondproject.config.security.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserTokenEntity {
    private Long iuser;
    private String ip;
    private String accessToken;
    private String refreshToken;
    private String createdAt;
    private String updatedAt;
}
