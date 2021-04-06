package com.moringaschool.schoolsystem.models;

import com.google.firebase.database.Exclude;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentDetails)) return false;
        PaymentDetails that = (PaymentDetails) o;
        return time == that.time &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(transactedTo, that.transactedTo) &&
                Objects.equals(transactedBy, that.transactedBy) &&
                Objects.equals(payer, that.payer) &&
                Objects.equals(mode, that.mode) &&
                Objects.equals(type, that.type) &&
                Objects.equals(paymentSummary, that.paymentSummary) &&
                Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, transactedTo, transactedBy, payer, mode, type, paymentSummary, time, key);
    }
}
