package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.TicketCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.TicketDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Attendee;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Ticket;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.mappers.TicketMapper;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.repositories.AttendeeRepository;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.repositories.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {
    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final AttendeeRepository attendeeRepository;

    public Page<TicketDTO> getAllTickets(Pageable pageable) {
        logger.info("Solicitando todas los tickets con paginación: página {}, tamaño {}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<Ticket> ticketPage = ticketRepository.findAll(pageable);
            logger.info("Se han encontrado {} tickets en la página actual", ticketPage.getTotalElements());
            return ticketPage.map(ticketMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al obtener todos los tickets: {}", e.getMessage());
            throw e;
        }
    }

    public Optional<TicketDTO> getTicketById(Long id) {
        try {
            logger.info("Buscando concierto con ID {}: ", id);
            return ticketRepository.findById(id).map(ticketMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al buscar concierto con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el concierto: ", e);
        }
    }

    public TicketDTO createTicket(TicketCreateDTO ticketCreateDTO) {
        Attendee attendee = attendeeRepository.findById(ticketCreateDTO.getAttendeeId())
                .orElseThrow(() -> new IllegalArgumentException("Attendee no encontrado"));

        Ticket ticket = ticketMapper.toEntity(ticketCreateDTO);
        ticket.setAttendee(attendee);

        Ticket savedTicket = ticketRepository.save(ticket);
        logger.info("Concierto con ID {} creado con éxito", savedTicket.getId());

        return ticketMapper.toDTO(savedTicket);
    }

    public TicketDTO updateTicket(
            Long id,
            @Valid TicketCreateDTO ticketCreateDTO
    ) {
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket no encontrado"));

        Attendee attendee = attendeeRepository.findById(ticketCreateDTO.getAttendeeId())
                .orElseThrow(() -> new IllegalArgumentException("Attendee no encontrado"));

        existingTicket.setPrice(ticketCreateDTO.getPrice());
        existingTicket.setType(ticketCreateDTO.getType());
        existingTicket.setUsed(ticketCreateDTO.isUsed());
        existingTicket.setAttendee(attendee);

        Ticket updatedTicket = ticketRepository.save(existingTicket);
        logger.info("Concierto con ID {} actualizado exitosamente.", updatedTicket.getId());

        return ticketMapper.toDTO(updatedTicket);
    }

    public void deleteTicket(Long id) {
        logger.info("Buscando concierto con ID {}", id);

        if (!ticketRepository.existsById(id)) {
            throw new IllegalArgumentException("Concierto no encontrado.");
        }

        ticketRepository.deleteById(id);
        logger.info("Concierto con ID {} eliminado exitosamente.", id);
    }
}
