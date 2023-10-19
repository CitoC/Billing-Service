package com.gitcodings.stack.billing.rest;

import com.gitcodings.stack.billing.repo.BillingRepo;
import com.gitcodings.stack.billing.util.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController
{
    private final BillingRepo repo;
    private final Validate    validate;

    @Autowired
    public OrderController(BillingRepo repo,Validate validate)
    {
        this.repo = repo;
        this.validate = validate;
    }
}
