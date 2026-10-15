package it.mauriziobotto.banca.repository;

import it.mauriziobotto.banca.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, String> {
}
