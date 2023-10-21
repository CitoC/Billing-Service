package com.gitcodings.stack.billing.util;

import com.gitcodings.stack.core.error.ResultError;
import com.gitcodings.stack.core.result.IDMResults;
import com.gitcodings.stack.core.security.JWTManager;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

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

    public boolean isPremium(SignedJWT user) {
        List<String> roles = null;
        try {
            roles = user.getJWTClaimsSet().getStringListClaim(JWTManager.CLAIM_ROLES);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        // return true if contains premium role
        return roles.stream().anyMatch("PREMIUM"::equalsIgnoreCase);
    }

}
