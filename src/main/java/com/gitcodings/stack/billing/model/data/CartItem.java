package com.gitcodings.stack.billing.model.data;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CartItem {
    BigDecimal unitPrice;
    Integer quantity;
    Long movieId;
    String movieTitle;
    String backdropPath;
    String posterPath;

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public CartItem setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice.setScale(2, RoundingMode.HALF_UP);
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CartItem setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Long getMovieId() {
        return movieId;
    }

    public CartItem setMovieId(Long movieId) {
        this.movieId = movieId;
        return this;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public CartItem setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
        return this;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public CartItem setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        return this;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public CartItem setPosterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }
}
