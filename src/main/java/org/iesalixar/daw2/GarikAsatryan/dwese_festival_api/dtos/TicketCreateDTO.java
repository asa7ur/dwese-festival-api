package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Ticket;

@Getter
@Setter
public class TicketCreateDTO {
    @NotNull(message = "{msg.ticket.price.notNull}")
    @Positive(message = "{msg.ticket.price.positive}")
    private Double price;

    @NotNull(message = "{msg.ticket.type.notNull}")
    private Ticket.Type type;

    private boolean used;

    @NotNull
    private Long attendeeId;
}
