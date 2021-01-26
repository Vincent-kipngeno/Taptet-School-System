package com.moringaschool.schoolsystem.models;

import com.google.firebase.database.Exclude;

public class paymentDetails {
    public String time, amount, transactedTo, transactedBy, payer, mode, type, paymentSummary;

    @Exclude
    public String key;

    public paymentDetails(String time, String amount, String transactedTo, String transactedBy, String payer, String mode, String type, String paymentSummary) {
        this.time = time;
        this.amount = amount;
        this.transactedTo = transactedTo;
        this.transactedBy = transactedBy;
        this.payer = payer;
        this.mode = mode;
        this.type = type;
        this.paymentSummary = paymentSummary;
    }
}
