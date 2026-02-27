package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.AttendeeCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.AttendeeDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Attendee;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.mappers.AttendeeMapper;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.repositories.AttendeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private static final Logger logger = LoggerFactory.getLogger(AttendeeService.class);

    private final AttendeeRepository attendeeRepository;
    private final AttendeeMapper attendeeMapper;

    public Page<AttendeeDTO> getAllAttendees(Pageable pageable) {
        logger.info("Solicitando todas los attendees con paginación: página {}, tamaño {}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<Attendee> attendeePage = attendeeRepository.findAll(pageable);
            logger.info("Se han encontrado {} attendees en la página actual", attendeePage.getTotalElements());
            return attendeePage.map(attendeeMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al obtener todos los attendees: {}", e.getMessage());
            throw e;
        }
    }

    public Optional<AttendeeDTO> getAttendeeById(Long id) {
        try {
            logger.info("Buscando attendee con ID {}: ", id);
            return attendeeRepository.findById(id).map(attendeeMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al buscar attendee con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el attendee: ", e);
        }
    }

    public AttendeeDTO createAttendee(AttendeeCreateDTO attendeeCreateDTO, Locale locale) {
        Attendee attendee = attendeeMapper.toEntity(attendeeCreateDTO);
        Attendee savedAttendee = attendeeRepository.save(attendee);
        logger.info("Attendee {} creado con éxito", savedAttendee.getName());

        return attendeeMapper.toDTO(savedAttendee);
    }

    public AttendeeDTO updateAttendee(
            Long id,
            @Valid AttendeeCreateDTO attendeeCreateDTO
    ) {
        Attendee existingAttendee = attendeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attendee no encontrado"));


        existingAttendee.setDni(attendeeCreateDTO.getDni());
        existingAttendee.setName(attendeeCreateDTO.getName());
        existingAttendee.setPhone(attendeeCreateDTO.getPhone());
        existingAttendee.setEmail(attendeeCreateDTO.getEmail());

        Attendee updatedAttendee = attendeeRepository.save(existingAttendee);
        logger.info("Attendee con ID {} actualizada exitosamente.", updatedAttendee.getId());

        return attendeeMapper.toDTO(updatedAttendee);
    }

    public void deleteAttendee(Long id) {
        logger.info("Buscando attendee con ID {}", id);

        if (!attendeeRepository.existsById(id)) {
            throw new IllegalArgumentException("Attendee no encontrado.");
        }

        attendeeRepository.deleteById(id);
        logger.info("Attendee con ID {} eliminada exitosamente.", id);
    }
}
