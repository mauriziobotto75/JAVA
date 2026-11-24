package it.mauriziobotto.biblioteca.repository;

import it.mauriziobotto.biblioteca.domain.Lettore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LettoreRepository extends JpaRepository<Lettore, Long> {
}
