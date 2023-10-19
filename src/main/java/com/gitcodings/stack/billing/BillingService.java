package com.gitcodings.stack.billing;

import com.gitcodings.stack.billing.config.BillingServiceConfig;
import com.gitcodings.stack.core.spring.SecuredStackService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@SecuredStackService
@EnableConfigurationProperties({
    BillingServiceConfig.class
})
public class BillingService
{
    public static void main(String[] args)
    {
        SpringApplication.run(BillingService.class, args);
    }
}
