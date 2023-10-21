package com.gitcodings.stack.billing.rest;

import com.gitcodings.stack.billing.model.request.CartRequest;
import com.gitcodings.stack.billing.model.response.CartResponse;
import com.gitcodings.stack.billing.service.CartService;
import com.gitcodings.stack.billing.util.AuthenticationUtil;
import com.gitcodings.stack.core.result.Result;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class CartController
{
    private final CartService service;
    private final AuthenticationUtil AuthUtil;

    @Autowired
    public CartController(CartService service, AuthenticationUtil authUtil)
    {
        this.service = service;
        AuthUtil = authUtil;
    }

    @PostMapping("/cart/insert")
    public ResponseEntity<CartResponse> insertItem(@AuthenticationPrincipal SignedJWT user, @RequestBody CartRequest request) {
        Long userId = AuthUtil.getUserId(user);
        CartResponse response = service.insertItemResponse(request.getMovieId(), request.getQuantity(), userId);
        return ResponseEntity.status(response.getResult().status()).body(response);
    }

    @PostMapping("/cart/update")
    public ResponseEntity<CartResponse> updateItem(@AuthenticationPrincipal SignedJWT user, @RequestBody CartRequest request) {
        Long userId = AuthUtil.getUserId(user);
        CartResponse response = service.updateItemResponse(request.getMovieId(), request.getQuantity(), userId);
        return ResponseEntity.status(response.getResult().status()).body(response);
    }



    @GetMapping("/test")
    public String test() {
        return "hello world";
    }
}
