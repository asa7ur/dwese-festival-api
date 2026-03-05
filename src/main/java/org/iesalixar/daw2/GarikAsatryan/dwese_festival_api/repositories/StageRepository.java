package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.repositories;

import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Stage;
import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StageRepository extends JpaRepository<Stage, Long> {
    @Query("SELECT s FROM Stage s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Stage> searchStages(@Param("keyword") String keyword, Pageable pageable);
}
