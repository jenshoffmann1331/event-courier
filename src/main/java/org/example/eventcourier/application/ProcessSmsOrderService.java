package org.example.eventcourier.application;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.eventcourier.application.port.in.ProcessSmsOrderUseCase;
import org.example.eventcourier.domain.SmsOrder;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ProcessSmsOrderService implements ProcessSmsOrderUseCase {

  private static final Logger LOG = Logger.getLogger(ProcessSmsOrderService.class);

  @Override
  public void process(SmsOrder smsOrder) {
    LOG.infof(
        "Processing SmsOrder (dummy) correlationId=%s, to=%s, text=%s",
        smsOrder.correlationId().value(),
        smsOrder.recipient(),
        smsOrder.text()
    );

    // TODO: SMPP, Status-Queue, Retry, Timeout, ...
  }
}
