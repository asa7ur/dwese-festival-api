package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistCreateDTO {
    @NotEmpty(message = "{msg.artist.name.notEmpty}")
    @Size(max = 100, message = "{msg.artist.name.notEmpty}")
    private String name;

    @NotEmpty(message = "{msg.artist.genre.notEmpty}")
    @Size(max = 100, message = "{msg.artist.genre.notEmpty}")
    private String genre;

    @NotEmpty(message = "{msg.artist.country.notEmpty}")
    @Size(max = 100, message = "{msg.artist.country.notEmpty}")
    private String country;

    private String image;
}
