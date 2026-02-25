package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class StageDTO {
    private Long id;
    private String name;
    private BigInteger capacity;
}
