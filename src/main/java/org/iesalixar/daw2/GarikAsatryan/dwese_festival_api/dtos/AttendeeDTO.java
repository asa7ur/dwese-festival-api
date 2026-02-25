package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendeeDTO {
    private Long id;
    private String dni;
    private String name;
    private String phone;
    private String email;
}
