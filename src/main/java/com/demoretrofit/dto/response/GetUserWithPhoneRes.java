package com.demoretrofit.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserWithPhoneRes {

    private String user_status;
    private String access_token;
    private String refresh_token;
}
