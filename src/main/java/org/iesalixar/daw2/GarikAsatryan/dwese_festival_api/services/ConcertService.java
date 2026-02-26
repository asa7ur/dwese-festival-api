package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.ConcertCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.ConcertDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Concert;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.mappers.ConcertMapper;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.repositories.ConcertRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConcertService {
    private static final Logger logger = LoggerFactory.getLogger(ConcertService.class);

    private final ConcertRepository concertRepository;
    private final ConcertMapper concertMapper;

    public Page<ConcertDTO> getAllConcerts(Pageable pageable) {
        logger.info("Solicitando todas los concerts con paginación: página {}, tamaño {}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<Concert> concertPage = concertRepository.findAll(pageable);
            logger.info("Se han encontrado {} concerts en la página actual", concertPage.getTotalElements());
            return concertPage.map(concertMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al obtener todos los concerts: {}", e.getMessage());
            throw e;
        }
    }

    public Optional<ConcertDTO> getConcertById(Long id) {
        try {
            logger.info("Buscando concierto con ID {}: ", id);
            return concertRepository.findById(id).map(concertMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al buscar concierto con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el concierto: ", e);
        }
    }

    public ConcertDTO createConcert(ConcertCreateDTO concertCreateDTO) {
        Concert concert = concertMapper.toEntity(concertCreateDTO);
        Concert savedConcert = concertRepository.save(concert);
        logger.info("Concierto con ID {} creado con éxito", savedConcert.getId());

        return concertMapper.toDTO(savedConcert);
    }

    public ConcertDTO updateConcert(
            Long id,
            @Valid ConcertCreateDTO concertCreateDTO
    ) {
        Concert existingConcert = concertRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Concert no encontrado"));


        existingConcert.setStartTime(concertCreateDTO.getStartTime());
        existingConcert.setEndTime(concertCreateDTO.getEndTime());

        Concert updatedConcert = concertRepository.save(existingConcert);
        logger.info("Concierto con ID {} actualizado exitosamente.", updatedConcert.getId());

        return concertMapper.toDTO(updatedConcert);
    }

    public void deleteConcert(Long id) {
        logger.info("Buscando concierto con ID {}", id);

        if (!concertRepository.existsById(id)) {
            throw new IllegalArgumentException("Concierto no encontrado.");
        }

        concertRepository.deleteById(id);
        logger.info("Concierto con ID {} eliminado exitosamente.", id);
    }
}
