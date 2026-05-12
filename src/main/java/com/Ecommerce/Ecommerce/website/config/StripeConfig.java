package com.Ecommerce.Ecommerce.website.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    @Value("${app.payment.stripe.secret-key:}")
    private String stripeSecretKey;

    @Value("${app.payment.stripe.webhook-secret:}")
    private String stripeWebhookSecret;

    public String getStripeSecretKey() {
        return stripeSecretKey;
    }

    public String getStripeWebhookSecret() {
        return stripeWebhookSecret;
    }
}

