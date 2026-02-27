package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.StageCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.StageDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.services.StageService;
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
@RequestMapping("/api/v1/stages")
@RequiredArgsConstructor
public class StageController {
    private static final Logger logger = LoggerFactory.getLogger(StageController.class);

    private final StageService stageService;

    @GetMapping
    public ResponseEntity<Page<StageDTO>> getAllStages(
            @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        logger.info("Solicitando todos los escenarios con paginación: página {}, tamaño {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<StageDTO> stages = stageService.getAllStages(pageable);
            logger.info("Se han encontrado {} escenarios", stages.getTotalElements());
            return ResponseEntity.ok(stages);
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud de listado de escenarios: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStageById(@PathVariable Long id) {
        logger.info("Buscando escenario con ID {}", id);
        try {
            Optional<StageDTO> stageDTO = stageService.getStageById(id);
            if (stageDTO.isPresent()) {
                logger.info("Escenario con ID {} encontrado", id);
                return ResponseEntity.ok(stageDTO.get());
            } else {
                logger.warn("Escenario no encontrado {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El escenario no existe.");
            }
        } catch (Exception e) {
            logger.error("Error al buscar el escenario con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el escenario");
        }
    }

    @PostMapping
    public ResponseEntity<?> createStage(
            @Valid @RequestBody StageCreateDTO stageCreateDTO,
            Locale locale
    ) {
        try {
            StageDTO createdStage = stageService.createStage(stageCreateDTO, locale);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al crear el escenario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el escenario");
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateStage(
            @PathVariable Long id,
            @Valid @RequestBody StageCreateDTO stageCreateDTO
    ) {
        try {
            StageDTO updatedStage = stageService.updateStage(id, stageCreateDTO);
            return ResponseEntity.ok(updatedStage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al actualizar el escenario con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el escenario");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStage(@PathVariable Long id) {
        logger.info("Eliminando escenario con ID {}", id);
        try {
            stageService.deleteStage(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al eliminar el escenario con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el escenario.");
        }
    }
}
