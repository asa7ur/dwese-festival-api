package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class StageCreateDTO {
    @NotEmpty(message = "{msg.stage.name.notEmpty}")
    @Size(max = 100, message = "{msg.stage.name.notEmpty}")
    private String name;

    @NotNull(message = "{msg.stage.capacity.notNull}")
    private BigInteger capacity;
}
