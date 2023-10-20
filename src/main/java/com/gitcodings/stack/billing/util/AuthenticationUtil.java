package com.gitcodings.stack.billing.util;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class AuthenticationUtil {

    // NOTE: I checked what type id is in the IDM Service, and apparently it's Integer,
    // but I'm claiming here as Long. I think this won't be a problem until I reach
    // a certain number
    public Long getUserId(SignedJWT user) {
        Long userId = null;
        try {
            userId = user.getJWTClaimsSet().getLongClaim("id");
            System.out.println(userId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return userId;
    }

}
