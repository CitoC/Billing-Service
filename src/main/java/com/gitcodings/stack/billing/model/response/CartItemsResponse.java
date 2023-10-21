package com.gitcodings.stack.billing.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gitcodings.stack.billing.model.data.CartItem;
import com.gitcodings.stack.core.result.Result;
import com.stripe.param.terminal.ReaderSetReaderDisplayParams;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CartItemsResponse {
    Result result;
    BigDecimal total;
    List<CartItem> items;

    public Result getResult() {
        return result;
    }

    public CartItemsResponse setResult(Result result) {
        this.result = result;
        return this;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public CartItemsResponse setTotal(BigDecimal total) {
        this.total = total.setScale(2, RoundingMode.HALF_UP);
        return this;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public CartItemsResponse setItems(List<CartItem> items) {
        this.items = items;
        return this;
    }
}
