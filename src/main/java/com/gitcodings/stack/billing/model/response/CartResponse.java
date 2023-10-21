package com.gitcodings.stack.billing.model.response;

import com.gitcodings.stack.core.result.Result;

public class CartResponse {
    Result result;

    public Result getResult() {
        return result;
    }

    public CartResponse setResult(Result result) {
        this.result = result;
        return this;
    }
}
