package org.example.eventcourier.application.port.in;

import org.example.eventcourier.domain.SmsOrder;

public interface ProcessSmsOrderUseCase {

  void process(SmsOrder smsOrder);
}
