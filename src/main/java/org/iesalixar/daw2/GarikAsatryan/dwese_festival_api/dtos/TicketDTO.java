package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos;

import lombok.Getter;
import lombok.Setter;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Ticket;

@Getter
@Setter
public class TicketDTO {
    private Long id;
    private Double price;
    private Ticket.Type type;
    private boolean used;
    private AttendeeDTO attendee;
}
