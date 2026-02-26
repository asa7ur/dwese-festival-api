package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.ArtistCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.ArtistDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Artist;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.mappers.ArtistMapper;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.repositories.ArtistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private static final Logger logger = LoggerFactory.getLogger(ArtistService.class);

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;
    private final FileStorageService fileStorageService;

    public Page<ArtistDTO> getAllArtists(Pageable pageable) {
        logger.info("Solicitando todas los artistas con paginación: página {}, tamaño {}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<Artist> artistPage = artistRepository.findAll(pageable);
            logger.info("Se han encontrado {} artistas en la página actual", artistPage.getTotalElements());
            return artistPage.map(artistMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al obtener todos los artistas: {}", e.getMessage());
            throw e;
        }
    }

    public Optional<ArtistDTO> getArtistById(Long id) {
        try {
            logger.info("Buscando artista con ID {}: ", id);
            return artistRepository.findById(id).map(artistMapper::toDTO);
        } catch (Exception e) {
            logger.error("Error al buscar artista con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al buscar el artista: ", e);
        }
    }

    public ArtistDTO createArtist(ArtistCreateDTO artistCreateDTO, Locale locale) {
        String fileName = null;
        if (artistCreateDTO.getImage() != null && !artistCreateDTO.getImage().isEmpty()) {
            fileName = fileStorageService.saveFile(artistCreateDTO.getImage());
            if (fileName == null) {
                throw new RuntimeException("Error al guardar la imagen");
            }
        }

        Artist artist = artistMapper.toEntity(artistCreateDTO);
        artist.setImage(fileName);
        Artist savedArtist = artistRepository.save(artist);
        logger.info("Artista {} creado con éxito", savedArtist.getName());

        return artistMapper.toDTO(savedArtist);
    }

    public ArtistDTO updateArtist(
            Long id,
            @Valid ArtistCreateDTO artistCreateDTO,
            Locale locale
    ) {
        Artist existingArtist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artista no encontrado"));

        String fileName = existingArtist.getImage();
        if (artistCreateDTO.getImage() != null && !artistCreateDTO.getImage().isEmpty()) {
            fileName = fileStorageService.saveFile(artistCreateDTO.getImage());
            if (fileName == null) {
                throw new RuntimeException("Error al guardar la imagen");
            }
        }

        existingArtist.setName(artistCreateDTO.getName());
        existingArtist.setGenre(artistCreateDTO.getGenre());
        existingArtist.setCountry(artistCreateDTO.getCountry());
        existingArtist.setImage(fileName);

        Artist updatedArtist = artistRepository.save(existingArtist);
        logger.info("Artista cin ID {} actualizada exitosamente.", updatedArtist.getId());

        return artistMapper.toDTO(updatedArtist);
    }

    public void deleteArtist(Long id) {
        logger.info("Buscando artista con ID {}", id);

        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artista no encontrado"));

        if (artist.getImage() != null && !artist.getImage().isEmpty()) {
            fileStorageService.deleteFile(artist.getImage());
            logger.info("Imagen asociada al artista con ID {} eliminada.", artist.getId());
        }

        artistRepository.deleteById(id);
        logger.info("Artista con ID {} eliminada exitosamente.", id);
    }
}
