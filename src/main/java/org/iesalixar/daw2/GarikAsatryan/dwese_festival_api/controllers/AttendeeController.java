package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.AttendeeCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.AttendeeDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.services.AttendeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/attendees")
@RequiredArgsConstructor
public class AttendeeController {
    private static final Logger logger = LoggerFactory.getLogger(AttendeeController.class);

    private final AttendeeService attendeeService;

    @GetMapping
    public ResponseEntity<Page<AttendeeDTO>> getAllAttendees(
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        logger.info("Solicitando todos los attendees con paginación: página {}, tamaño {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<AttendeeDTO> attendees = attendeeService.getAllAttendees(pageable);
            logger.info("Se han encontrado {} attendees", attendees.getTotalElements());
            return ResponseEntity.ok(attendees);
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud de listado de attendees: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAttendeeById(@PathVariable Long id) {
        logger.info("Buscando attendee con ID {}", id);
        try {
            Optional<AttendeeDTO> attendeeDTO = attendeeService.getAttendeeById(id);
            if (attendeeDTO.isPresent()) {
                logger.info("Attendee con ID {} encontrado", id);
                return ResponseEntity.ok(attendeeDTO.get());
            } else {
                logger.warn("Attendee no encontrado {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El attendee no existe.");
            }
        } catch (Exception e) {
            logger.error("Error al buscar el attendee con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el attendee");
        }
    }

    @PostMapping
    public ResponseEntity<?> createAttendee(
            @Valid @RequestBody AttendeeCreateDTO attendeeCreateDTO,
            Locale locale
    ) {
        try {
            AttendeeDTO createdAttendee = attendeeService.createAttendee(attendeeCreateDTO, locale);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAttendee);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al crear el attendee: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el attendee");
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateAttendee(
            @PathVariable Long id,
            @Valid @RequestBody AttendeeCreateDTO attendeeCreateDTO
    ) {
        try {
            AttendeeDTO updatedAttendee = attendeeService.updateAttendee(id, attendeeCreateDTO);
            return ResponseEntity.ok(updatedAttendee);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al actualizar el attendee con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el attendee");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAttendee(@PathVariable Long id) {
        logger.info("Eliminando attendee con ID {}", id);
        try {
            attendeeService.deleteAttendee(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al eliminar el attendee con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el attendee.");
        }
    }
}
