package com.gitcodings.stack.billing.service;

import com.gitcodings.stack.billing.repo.BillingRepo;
import com.gitcodings.stack.core.result.BillingResults;
import com.gitcodings.stack.core.result.Result;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final BillingRepo repo;

    public CartService(BillingRepo repo) {
        this.repo = repo;
    }

    //
    public Result insertItem(Long movieId, Integer quantity, Long userId) {
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
}
