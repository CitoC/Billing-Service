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

//    @GetMapping("/movie/search")
//    public ResponseEntity<MovieResponse> movieSearch(@AuthenticationPrincipal SignedJWT user,
//                                                     MovieRequest request) throws ParseException
//    {
//        // check the roles of user
//        List<String> roles = user.getJWTClaimsSet().getStringListClaim(JWTManager.CLAIM_ROLES);
//        boolean canSeeHidden =  roles.contains("ADMIN") || roles.contains("EMPLOYEE");
//
//        List<Movie> movies = movieService.searchMovies(request, canSeeHidden);
//
//        MovieResponse response = new MovieResponse();
//        if (movies == null || movies.isEmpty()) {
//            response.setResult(MoviesResults.NO_MOVIES_FOUND_WITHIN_SEARCH);
//        } else {
//            response.setResult(MoviesResults.MOVIES_FOUND_WITHIN_SEARCH)
//                    .setMovies(movies);
//        }
//
//        return ResponseEntity.status(response.getResult().status())
//                .body(response);
//    }

    @GetMapping("/test")
    public String test() {
        return "hello world";
    }
}
