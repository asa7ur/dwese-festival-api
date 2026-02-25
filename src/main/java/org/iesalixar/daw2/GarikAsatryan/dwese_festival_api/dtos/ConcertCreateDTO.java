package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ConcertCreateDTO {
    @NotNull(message = "{msg.concert.startTime.notNull}")
    private LocalDateTime startTime;

    @NotNull(message = "{msg.concert.endTime.notNull}")
    private LocalDateTime endTime;

    @NotNull(message = "{msg.concert.artist.notNull}")
    private Long artistId;

    @NotNull(message = "{msg.concert.stage.notNull}")
    private Long stageId;
}
