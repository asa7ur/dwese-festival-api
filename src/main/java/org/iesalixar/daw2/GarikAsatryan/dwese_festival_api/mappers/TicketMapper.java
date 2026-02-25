package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.mappers;

import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.TicketCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.TicketDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Ticket;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketMapper {
    private final AttendeeMapper attendeeMapper;
    public TicketDTO toDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setPrice(ticket.getPrice());
        dto.setType(ticket.getType());
        dto.setUsed(ticket.isUsed());
        dto.setAttendee(this.attendeeMapper.toDTO(ticket.getAttendee()));
        return dto;
    }

    public Ticket toEntity(TicketDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setId(dto.getId());
        ticket.setPrice(dto.getPrice());
        ticket.setType(dto.getType());
        ticket.setUsed(dto.isUsed());
        ticket.setAttendee(this.attendeeMapper.toEntity(dto.getAttendee()));
        return ticket;
    }

    public Ticket toEntity(TicketCreateDTO dto){
        Ticket ticket = new Ticket();
        ticket.setPrice(dto.getPrice());
        ticket.setType(dto.getType());
        ticket.setUsed(dto.isUsed());
        return ticket;
    }
}
