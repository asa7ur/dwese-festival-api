package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendeeCreateDTO {
    @NotEmpty(message = "{msg.attendee.dni.notEmpty}")
    private String dni;

    @NotEmpty(message = "{msg.attendee.name.notEmpty}")
    @Size(max = 100, message = "{msg.attendee.name.size}")
    private String name;

    @NotEmpty(message = "{msg.attendee.phone.notEmpty}")
    @Size(max = 25, message = "{msg.attendee.phone.size}")
    private String phone;

    @NotEmpty(message = "{msg.attendee.email.notEmpty}")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "{msg.attendee.email.notValid}")
    @Size(max = 100, message = "{msg.attendee.email.size}")
    private String email;
}
