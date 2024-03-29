package com.prasad.banking.conf;

import java.math.BigDecimal;

public class BankingConstants {

    public static final String API_VERSION_V1 = "/api/v1";
    public static final Long INITIAL_ACCOUNT_NUMBER = 33554432L;
    public static final String INITIAL_CUSTOMER_ID_PREFIX = "CID_";
    public static final Long INITIAL_CUSTOMER_ID_SUFFIX = 50060020L;
    public static final BigDecimal TRANSACTION_LIMIT = BigDecimal.valueOf(25000L);
}
