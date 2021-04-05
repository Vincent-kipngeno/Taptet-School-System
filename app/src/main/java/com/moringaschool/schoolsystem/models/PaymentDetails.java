package com.moringaschool.schoolsystem.models;

import com.google.firebase.database.Exclude;

public class PaymentDetails {
    public String amount, transactedTo, transactedBy, payer, mode, type, paymentSummary;
    public long time;
    @Exclude
    public String key;

    public PaymentDetails(long time, String amount, String transactedTo, String transactedBy, String payer, String mode, String type, String paymentSummary) {
        this.time = time;
        this.amount = amount;
        this.transactedTo = transactedTo;
        this.transactedBy = transactedBy;
        this.payer = payer;
        this.mode = mode;
        this.type = type;
        this.paymentSummary = paymentSummary;
    }

    public String getAmount() {
        return amount;
    }

    public String getTransactedTo() {
        return transactedTo;
    }

    public String getTransactedBy() {
        return transactedBy;
    }

    public String getPayer() {
        return payer;
    }

    public String getMode() {
        return mode;
    }

    public String getType() {
        return type;
    }

    public String getPaymentSummary() {
        return paymentSummary;
    }

    public long getTime() {
        return time;
    }

    public String getKey() {
        return key;
    }
}
