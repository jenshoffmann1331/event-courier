package org.example.eventcourier.domain;

public record SmsOrder(
    CorrelationId correlationId,
    String recipient,
    String text
) { }
