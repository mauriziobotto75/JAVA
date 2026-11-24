package it.mauriziobotto.biblioteca.repository;

import it.mauriziobotto.biblioteca.domain.Prestito;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestitoRepository extends JpaRepository<Prestito, Long> {

    boolean existsByLibroIdAndDataRestituzioneIsNull(Long libroId);

    List<Prestito> findAllByDataRestituzioneIsNullOrderByDataPrestitoDescIdDesc();

    List<Prestito> findAllByOrderByDataPrestitoDescIdDesc();
}
