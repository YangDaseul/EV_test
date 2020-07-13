package com.genesis.apps.comm.net.ga;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public @Data
class BeanLoginInfo {
    private String accessToken;
    private String refreshToken;
    private long expiresDate;
    private BeanUserProfile profile;
    private long refreshTokenExpriesDate;
    private String tokenCode;
}
