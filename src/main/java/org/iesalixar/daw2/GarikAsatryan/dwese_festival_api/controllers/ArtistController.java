package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.ArtistCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.ArtistDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.services.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/artists")
@RequiredArgsConstructor
public class ArtistController {
    private static final Logger logger = LoggerFactory.getLogger(ArtistController.class);

    private final ArtistService artistService;

    @GetMapping
    public ResponseEntity<Page<ArtistDTO>> getAllArtists(
            @PageableDefault(size = 10, sort = "name") Pageable pageable
    ) {
        logger.info("Solicitando todos los artistas con paginación: página {}, tamaño {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Page<ArtistDTO> artists = artistService.getAllArtists(pageable);
            logger.info("Se han encontrado {} artistas", artists.getTotalElements());
            return ResponseEntity.ok(artists);
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud de listado de artistas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getArtistById(@PathVariable Long id) {
        logger.info("Buscando artista con ID {}", id);
        try {
            Optional<ArtistDTO> artistDTO = artistService.getArtistById(id);
            if (artistDTO.isPresent()) {
                logger.info("Artista con ID {} encontrado", id);
                return ResponseEntity.ok(artistDTO.get());
            } else {
                logger.warn("Artista no encontrado {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El artista no existe.");
            }
        } catch (Exception e) {
            logger.error("Error al buscar el artista con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al buscar el artista");
        }
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createArtist(
            @Valid @ModelAttribute ArtistCreateDTO artistCreateDTO,
            Locale locale
    ) {
        try {
            logger.info("Recibida solicitud para crear artista con imagen: {}",
                    (artistCreateDTO.getImage() != null ? artistCreateDTO.getImage().getOriginalFilename() : "No hay archivo"));
            ArtistDTO createdArtist = artistService.createArtist(artistCreateDTO, locale);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdArtist);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            logger.error("Error al guardar la imagen: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar la imagen");
        } catch (Exception e) {
            logger.error("Error al crear el artista: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el artista");
        }
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> updateArtist(
            @PathVariable Long id,
            @Valid @ModelAttribute ArtistCreateDTO artistCreateDTO,
            Locale locale
    ) {
        try {
            ArtistDTO updatedArtist = artistService.updateArtist(id, artistCreateDTO, locale);
            return ResponseEntity.ok(updatedArtist);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al actualizar el artista con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el artista");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable Long id) {
        logger.info("Eliminando artista con ID {}", id);
        try {
            artistService.deleteArtist(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error al eliminar el artista con ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el artista");
        }
    }
}
