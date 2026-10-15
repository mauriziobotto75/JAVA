package it.mauriziobotto.banca.repository;

import it.mauriziobotto.banca.domain.Conto;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ContoRepository extends JpaRepository<Conto, String> {

    @Query("select c from Conto c join fetch c.cliente order by c.numero")
    List<Conto> findAllWithCliente();

    @Query("select c from Conto c join fetch c.cliente where c.numero = :numero")
    Optional<Conto> findWithClienteByNumero(@Param("numero") String numero);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Conto c join fetch c.cliente where c.numero = :numero")
    Optional<Conto> findWithClienteByNumeroForUpdate(@Param("numero") String numero);

    @Query("select c from Conto c join fetch c.cliente where c.saldo < :zero order by c.saldo")
    List<Conto> findWithSaldoNegativo(@Param("zero") BigDecimal zero);
}
