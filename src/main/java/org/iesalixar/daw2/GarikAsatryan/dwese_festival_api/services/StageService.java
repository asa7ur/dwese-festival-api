package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.StageCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.StageDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Stage;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.mappers.StageMapper;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.repositories.StageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StageService {
    private static final Logger logger = LoggerFactory.getLogger(StageService.class);

    private final StageRepository stageRepository;
    private final StageMapper stageMapper;

    public Page<StageDTO> getAllStages(Pageable pageable) {
        logger.info("Solicitando todos los escenarios con paginación: página {}, tamaño {}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<Stage> stagePage = stageRepository.findAll(pageable);
            logger.info("Se han encontrado {} sescenarios en la página actual", stagePage.getTotalElements());
            return stagePage.map(stageMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al obtener todos los escenarios: {}", e.getMessage());
            throw e;
        }
    }

    public Optional<StageDTO> getStageById(Long id) {
        try {
            logger.info("Buscando escenario con ID {}: ", id);
            return stageRepository.findById(id).map(stageMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al buscar escenario con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el escenario: ", e);
        }
    }

    public StageDTO createStage(StageCreateDTO stageCreateDTO, Locale locale) {
        Stage stage = stageMapper.toEntity(stageCreateDTO);
        Stage savedStage = stageRepository.save(stage);
        logger.info("Escenario {} creado con éxito", savedStage.getName());

        return stageMapper.toDTO(savedStage);
    }

    public StageDTO updateStage(
            Long id,
            @Valid StageCreateDTO stageCreateDTO
    ) {
        Stage existingStage = stageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Escenario no encontrado"));


        existingStage.setName(stageCreateDTO.getName());
        existingStage.setCapacity(stageCreateDTO.getCapacity());

        Stage updatedStage = stageRepository.save(existingStage);
        logger.info("Escenario cin ID {} actualizada exitosamente.", updatedStage.getId());

        return stageMapper.toDTO(updatedStage);
    }

    public void deleteStage(Long id) {
        logger.info("Buscando escenario con ID {}", id);

        if (!stageRepository.existsById(id)) {
            throw new IllegalArgumentException("Escenario no encontrado.");
        }

        stageRepository.deleteById(id);
        logger.info("Escenario con ID {} eliminada exitosamente.", id);
    }
}
