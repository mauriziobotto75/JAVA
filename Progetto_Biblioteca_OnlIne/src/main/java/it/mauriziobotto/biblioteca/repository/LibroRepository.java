package it.mauriziobotto.biblioteca.repository;

import it.mauriziobotto.biblioteca.domain.Libro;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select libro from Libro libro where libro.id = :id")
    Optional<Libro> findByIdForUpdate(@Param("id") Long id);
}
