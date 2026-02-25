package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.mappers;

import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.StageCreateDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.dtos.StageDTO;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Stage;
import org.springframework.stereotype.Component;

@Component
public class StageMapper {
    public StageDTO toDTO(Stage stage) {
        StageDTO dto = new StageDTO();
        dto.setId(stage.getId());
        dto.setName(stage.getName());
        dto.setCapacity(stage.getCapacity());
        return dto;
    }

    public Stage toEntity(StageDTO dto) {
        Stage stage = new Stage();
        stage.setId(dto.getId());
        stage.setName(dto.getName());
        stage.setCapacity(dto.getCapacity());
        return stage;
    }

    public Stage toEntity(StageCreateDTO dto) {
        Stage stage = new Stage();
        stage.setName(dto.getName());
        stage.setCapacity(dto.getCapacity());
        return stage;
    }
}
