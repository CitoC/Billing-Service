package com.gitcodings.stack.billing.model.request;

public class CartRequest {
    Long movieId;
    Integer quantity;

    public Long getMovieId() {
        return movieId;
    }

    public CartRequest setMovieId(Long movieId) {
        this.movieId = movieId;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CartRequest setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
