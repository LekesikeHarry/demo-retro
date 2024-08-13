package com.demoretrofit.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserWithPhoneReq {

    private String phone_id;
    private int phone_code;
    private String phone_number;
    private int os_type;

}
