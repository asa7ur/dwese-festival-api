package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.mappers;

import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.AttendeeCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.AttendeeDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Attendee;
import org.springframework.stereotype.Component;

@Component
public class AttendeeMapper {
    public AttendeeDTO toDTO(Attendee attendee) {
        AttendeeDTO dto = new AttendeeDTO();
        dto.setId(attendee.getId());
        dto.setDni(attendee.getDni());
        dto.setName(attendee.getName());
        dto.setPhone(attendee.getPhone());
        dto.setEmail(attendee.getEmail());
        return dto;
    }

    public Attendee toEntity(AttendeeDTO dto) {
        Attendee attendee = new Attendee();
        attendee.setId(dto.getId());
        attendee.setDni(dto.getDni());
        attendee.setName(dto.getName());
        attendee.setPhone(dto.getPhone());
        attendee.setEmail(dto.getEmail());
        return attendee;
    }

    public Attendee toEntity(AttendeeCreateDTO dto){
        Attendee attendee = new Attendee();
        attendee.setDni(dto.getDni());
        attendee.setName(dto.getName());
        attendee.setPhone(dto.getPhone());
        attendee.setEmail(dto.getEmail());
        return attendee;
    }
}
