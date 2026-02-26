package org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.repositories;

import org.iesalixar.daw2.GarikAsatryan.dwese_festival_api.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
