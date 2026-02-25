package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistDTO {
    private Long id;
    private String name;
    private String genre;
    private String country;
    private String image;
}
