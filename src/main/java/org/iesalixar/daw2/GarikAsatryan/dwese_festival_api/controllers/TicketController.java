package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.TicketCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.TicketDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<Page<TicketDTO>> getAllTickets(
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        logger.info("Solicitando todos los conciertos con paginación: página {}, tamaño {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<TicketDTO> tickets = ticketService.getAllTickets(pageable);
            logger.info("Se han encontrado {} conciertos", tickets.getTotalElements());
            return ResponseEntity.ok(tickets);
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud de listado de conciertos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable Long id) {
        logger.info("Buscando concierto con ID {}", id);
        try {
            Optional<TicketDTO> ticketDTO = ticketService.getTicketById(id);
            if (ticketDTO.isPresent()) {
                logger.info("Concierto con ID {} encontrado", id);
                return ResponseEntity.ok(ticketDTO.get());
            } else {
                logger.warn("Concierto no encontrado {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El concierto no existe.");
            }
        } catch (Exception e) {
            logger.error("Error al buscar el concierto con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el concierto");
        }
    }

    @PostMapping
    public ResponseEntity<?> createTicket(
            @Valid @RequestBody TicketCreateDTO ticketCreateDTO
    ) {
        try {
            TicketDTO createdTicket = ticketService.createTicket(ticketCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al crear el concierto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el concierto");
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody TicketCreateDTO ticketCreateDTO
    ) {
        try {
            TicketDTO updatedTicket = ticketService.updateTicket(id, ticketCreateDTO);
            return ResponseEntity.ok(updatedTicket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al actualizar el concierto con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el concierto");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        logger.info("Eliminando concierto con ID {}", id);
        try {
            ticketService.deleteTicket(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al eliminar el concierto con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el concierto.");
        }
    }
}
