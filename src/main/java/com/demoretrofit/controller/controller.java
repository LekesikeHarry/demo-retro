package com.demoretrofit.controller;

import com.demoretrofit.dto.request.GetUserWithPhoneReq;
import com.demoretrofit.dto.response.ErrorResponse;
import com.demoretrofit.dto.response.GetUserWithPhoneRes;
import com.demoretrofit.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class controller {

    private final PostService postService;
    @Autowired
    public controller(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(
            value = "/get-user-with-phone",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST
    )
    public ResponseEntity<Object> getUserWithPhone(@RequestBody GetUserWithPhoneReq getUserWithPhoneReq)  {
        Object o = null;
        try {
            o = postService.getUserWithPhoneNumber(getUserWithPhoneReq);
            if (o instanceof GetUserWithPhoneRes){
                return ResponseEntity.ok(o);
            } else if (o instanceof ErrorResponse) {

                ErrorResponse errorResponse = (ErrorResponse) o;
                return ResponseEntity.status(errorResponse.getCode()).body(errorResponse);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(o);


    }
}
