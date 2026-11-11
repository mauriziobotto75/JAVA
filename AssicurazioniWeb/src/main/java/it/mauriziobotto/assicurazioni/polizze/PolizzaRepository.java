package it.mauriziobotto.assicurazioni.polizze;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolizzaRepository extends JpaRepository<Polizza, String> {

    List<Polizza> findAllByOrderByScadenzaAscCodicePolizzaAsc();

    List<Polizza> findAllByStatoPagamentoOrderByScadenzaAscCodicePolizzaAsc(String statoPagamento);

    List<Polizza> findAllByCodicePolizzaContainingIgnoreCaseOrNomeClienteContainingIgnoreCaseOrderByScadenzaAscCodicePolizzaAsc(
            String codicePolizza, String nomeCliente);

    List<Polizza> findAllByStatoPagamentoAndCodicePolizzaContainingIgnoreCaseOrStatoPagamentoAndNomeClienteContainingIgnoreCaseOrderByScadenzaAscCodicePolizzaAsc(
            String stato1, String codicePolizza, String stato2, String nomeCliente);
}
