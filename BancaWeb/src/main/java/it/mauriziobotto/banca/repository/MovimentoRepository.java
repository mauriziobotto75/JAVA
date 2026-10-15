package it.mauriziobotto.banca.repository;

import it.mauriziobotto.banca.domain.Movimento;
import it.mauriziobotto.banca.domain.TipoMovimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MovimentoRepository extends JpaRepository<Movimento, Long> {

    @Query("""
            select m from Movimento m join fetch m.conto c join fetch c.cliente
            where m.tipo = :tipo order by m.dataOperazione desc, m.numero desc
            """)
    List<Movimento> findByTipo(@Param("tipo") TipoMovimento tipo);

    @Query("""
            select m from Movimento m join fetch m.conto c join fetch c.cliente
            where m.dataOperazione between :dal and :al
            order by m.dataOperazione desc, m.numero desc
            """)
    List<Movimento> findBetweenDates(@Param("dal") LocalDate dal, @Param("al") LocalDate al);

    @Query("""
            select m from Movimento m join fetch m.conto c join fetch c.cliente
            where c.numero = :numero order by m.dataOperazione desc, m.numero desc
            """)
    List<Movimento> findByConto(@Param("numero") String numero);

    @Query("""
            select m from Movimento m join fetch m.conto c join fetch c.cliente
            where c.numero = :numero and m.dataOperazione between :dal and :al
            order by m.dataOperazione desc, m.numero desc
            """)
    List<Movimento> findByContoAndDateRange(
            @Param("numero") String numero,
            @Param("dal") LocalDate dal,
            @Param("al") LocalDate al);
}
