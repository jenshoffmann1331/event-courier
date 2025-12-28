package org.example.eventcourier.adapter.in;

import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.example.eventcourier.application.port.in.ProcessSmsOrderUseCase;
import org.example.eventcourier.domain.CorrelationId;
import org.example.eventcourier.domain.SmsOrder;

@Path("/debug/sms")
@RequestScoped
public class DebugSmsResource {

  private final ProcessSmsOrderUseCase useCase;

  public DebugSmsResource(ProcessSmsOrderUseCase useCase) {
    this.useCase = useCase;
  }

  public record DebugSmsRequest(
      @NotBlank String recipient,
      @NotBlank String text,
      String correlationId
  ) { }

  public record DebugSmsResponse(
      String correlationId
  ) { }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response post(@Valid @NotNull DebugSmsRequest request) {
    if (request == null) {
      return Response.status(Status.BAD_REQUEST)
          .entity("Request body must not be null")
          .build();
    }

    CorrelationId correlationId = (request.correlationId() == null || request.correlationId().isBlank())
        ? CorrelationId.generate()
        : new CorrelationId(request.correlationId());

    SmsOrder smsOrder = new SmsOrder(
        correlationId,
        request.recipient,
        request.text
    );

    useCase.process(smsOrder);

    return Response.ok(new DebugSmsResponse(correlationId.value())).build();
  }
}
