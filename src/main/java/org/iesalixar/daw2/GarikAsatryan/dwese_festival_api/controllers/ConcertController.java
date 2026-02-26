package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.ConcertCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.ConcertDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.services.ConcertService;
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
@RequestMapping("/api/v1/concerts")
@RequiredArgsConstructor
public class ConcertController {
    private static final Logger logger = LoggerFactory.getLogger(ConcertController.class);

    private final ConcertService concertService;

    @GetMapping
    public ResponseEntity<Page<ConcertDTO>> getAllConcerts(
            @PageableDefault(size = 10, sort = "name") Pageable pageable
    ) {
        logger.info("Solicitando todos los conciertos con paginación: página {}, tamaño {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<ConcertDTO> concerts = concertService.getAllConcerts(pageable);
            logger.info("Se han encontrado {} conciertos", concerts.getTotalElements());
            return ResponseEntity.ok(concerts);
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud de listado de conciertos: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getConcertById(@PathVariable Long id) {
        logger.info("Buscando concierto con ID {}", id);
        try {
            Optional<ConcertDTO> concertDTO = concertService.getConcertById(id);
            if (concertDTO.isPresent()) {
                logger.info("Concierto con ID {} encontrado", id);
                return ResponseEntity.ok(concertDTO.get());
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
    public ResponseEntity<?> createConcert(
            @Valid @RequestBody ConcertCreateDTO concertCreateDTO
    ) {
        try {
            ConcertDTO createdConcert = concertService.createConcert(concertCreateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdConcert);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al crear el concierto: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el concierto");
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateConcert(
            @PathVariable Long id,
            @Valid @RequestBody ConcertCreateDTO concertCreateDTO
    ) {
        try {
            ConcertDTO updatedConcert = concertService.updateConcert(id, concertCreateDTO);
            return ResponseEntity.ok(updatedConcert);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al actualizar el concierto con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el concierto");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteConcert(@PathVariable Long id) {
        logger.info("Eliminando concierto con ID {}", id);
        try {
            concertService.deleteConcert(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al eliminar el concierto con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el concierto.");
        }
    }
}
