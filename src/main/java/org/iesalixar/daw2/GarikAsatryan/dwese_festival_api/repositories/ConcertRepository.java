package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.repositories;

import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
}
