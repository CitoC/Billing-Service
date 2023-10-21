package com.gitcodings.stack.billing.service;

import com.gitcodings.stack.billing.model.response.CartResponse;
import com.gitcodings.stack.billing.repo.BillingRepo;
import com.gitcodings.stack.core.error.ResultError;
import com.gitcodings.stack.core.result.BillingResults;
import com.gitcodings.stack.core.result.Result;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final BillingRepo repo;

    public CartService(BillingRepo repo) {
        this.repo = repo;
    }

    // the pattern is that the Response method will call the private Result method
    public CartResponse insertItemResponse(Long movieId, Integer quantity, Long userId) {
        Result result = insertItemResult(movieId, quantity, userId);
        return new CartResponse().setResult(result);
    }

    private Result insertItemResult(Long movieId, Integer quantity, Long userId) {
        Result result;
        if (quantity <= 0) {
            result = BillingResults.INVALID_QUANTITY;
        }
        else if (quantity > 10) {
            result = BillingResults.MAX_QUANTITY;
        }
        else if (repo.itemExists(movieId, userId)) {
            result = BillingResults.CART_ITEM_EXISTS;
        }
        else {
            repo.insertItem(movieId, quantity, userId);
            result = BillingResults.CART_ITEM_INSERTED;
        }

        return result;
    }

    public CartResponse updateItemResponse(Long movieId, Integer quantity, Long userId) {
        Result result = updateItemResult(movieId, quantity, userId);
        return new CartResponse().setResult(result);
    }

    private Result updateItemResult(Long movieId, Integer quantity, Long userId) {
        Result result;
        if (quantity <= 0) {
            result = BillingResults.INVALID_QUANTITY;
        }
        else if (quantity > 10) {
            result = BillingResults.MAX_QUANTITY;
        }
        else if (!repo.itemExists(movieId, userId)) {
            result = BillingResults.CART_ITEM_DOES_NOT_EXIST;
        }
        else {
            repo.updateItem(movieId, quantity, userId);
            result = BillingResults.CART_ITEM_UPDATED;
        }
        return result;
    }

    public CartResponse deleteItemResponse(Long movieId, Long userId) {
        Result result = deleteItemResult(movieId, userId);
        return new CartResponse().setResult(result);
    }

    private Result deleteItemResult(Long movieId, Long userId) {
        Result result;

        if (!repo.itemExists(movieId, userId)) {
            result = BillingResults.CART_ITEM_DOES_NOT_EXIST;
        }
        else {
            repo.deleteItem(movieId, userId);
            result = BillingResults.CART_ITEM_DELETED;
        }
        return result;
    }
}
