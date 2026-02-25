package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.mappers;

import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.ConcertCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.ConcertDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Concert;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConcertMapper {
    private final ArtistMapper artistMapper;
    private final StageMapper stageMapper;

    public ConcertDTO toDTO(Concert concert) {
        ConcertDTO dto = new ConcertDTO();
        dto.setId(concert.getId());
        dto.setStartTime(concert.getStartTime());
        dto.setEndTime(concert.getEndTime());
        dto.setArtist(artistMapper.toDTO(concert.getArtist()));
        dto.setStage(stageMapper.toDTO(concert.getStage()));
        return dto;
    }

    public Concert toEntity(ConcertDTO dto) {
        Concert concert = new Concert();
        concert.setId(dto.getId());
        concert.setStartTime(dto.getStartTime());
        concert.setEndTime(dto.getEndTime());
        concert.setArtist(artistMapper.toEntity(dto.getArtist()));
        concert.setStage(stageMapper.toEntity(dto.getStage()));
        return concert;
    }

    public Concert toEntity(ConcertCreateDTO dto) {
        Concert concert = new Concert();
        concert.setStartTime(dto.getStartTime());
        concert.setEndTime(dto.getEndTime());
        return concert;
    }
}
