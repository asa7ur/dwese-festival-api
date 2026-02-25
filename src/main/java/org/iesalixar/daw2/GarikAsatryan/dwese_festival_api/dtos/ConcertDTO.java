package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConcertDTO {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ArtistDTO artist;
    private StageDTO stage;
}
