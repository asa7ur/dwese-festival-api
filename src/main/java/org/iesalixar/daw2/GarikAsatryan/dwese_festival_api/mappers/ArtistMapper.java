package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.mappers;

import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.ArtistCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.ArtistDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Artist;
import org.springframework.stereotype.Component;

@Component
public class ArtistMapper {
    public ArtistDTO toDTO(Artist artist){
        ArtistDTO dto = new ArtistDTO();
        dto.setId(artist.getId());
        dto.setName(artist.getName());
        dto.setGenre(artist.getGenre());
        dto.setCountry(artist.getCountry());
        dto.setImage(artist.getImage());
        return dto;
    }

    public Artist toEntity(ArtistDTO dto){
        Artist artist = new Artist();
        artist.setId(dto.getId());
        artist.setName(dto.getName());
        artist.setGenre(dto.getGenre());
        artist.setCountry(dto.getCountry());
        artist.setImage(dto.getImage());
        return artist;
    }

    public Artist toEntity(ArtistCreateDTO dto){
        Artist artist = new Artist();
        artist.setName(dto.getName());
        artist.setGenre(dto.getGenre());
        artist.setCountry(dto.getCountry());
        artist.setImage(dto.getImage());
        return artist;
    }
}
