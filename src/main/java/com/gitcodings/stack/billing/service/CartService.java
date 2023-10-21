package com.gitcodings.stack.billing.service;

import com.gitcodings.stack.billing.model.data.CartItem;
import com.gitcodings.stack.billing.model.response.CartItemsResponse;
import com.gitcodings.stack.billing.model.response.CartResponse;
import com.gitcodings.stack.billing.repo.BillingRepo;
import com.gitcodings.stack.core.error.ResultError;
import com.gitcodings.stack.core.result.BillingResults;
import com.gitcodings.stack.core.result.Result;
import com.stripe.model.terminal.Reader;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

    public CartItemsResponse retrieveItemResponse(Long userId, boolean isPremium) {
        CartItemsResponse response = new CartItemsResponse();
        List<CartItem> items = repo.getItems(userId, isPremium);

        if (items == null || items.size() == 0 )
            response.setResult(BillingResults.CART_EMPTY);
        else {
            response.setResult(BillingResults.CART_RETRIEVED);
            response.setItems(items);
            response.setTotal(calcTotal(items));
        }
        return response;
    }

    private BigDecimal calcTotal(List<CartItem> items) {
        BigDecimal total = BigDecimal.ZERO; // Initialize the total to zero

        for (CartItem item : items) {
            // Calculate the subtotal for each item (unitPrice * quantity)
            BigDecimal itemTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

            // Add the item subtotal to the total
            total = total.add(itemTotal);
        }

        return total;
    }

    public CartResponse clearItemResponse(Long userId) {
        CartResponse response = new CartResponse();
        int rowAffected = 0;
        rowAffected = repo.clearItems(userId);

        if (rowAffected == 0) {
            response.setResult(BillingResults.CART_EMPTY);
        }
        else {
            response.setResult(BillingResults.CART_CLEARED);
        }
        return response;
    }
}
