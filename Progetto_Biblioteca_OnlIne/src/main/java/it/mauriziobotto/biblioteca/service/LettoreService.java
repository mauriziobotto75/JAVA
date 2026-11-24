package it.mauriziobotto.biblioteca.service;

import it.mauriziobotto.biblioteca.api.LettoreRequest;
import it.mauriziobotto.biblioteca.api.LettoreResponse;
import it.mauriziobotto.biblioteca.domain.Lettore;
import it.mauriziobotto.biblioteca.repository.LettoreRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LettoreService {

    private final LettoreRepository lettori;

    public LettoreService(LettoreRepository lettori) {
        this.lettori = lettori;
    }

    @Transactional(readOnly = true)
    public List<LettoreResponse> elenco() {
        return lettori.findAll(Sort.by("id")).stream().map(LettoreService::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public LettoreResponse trova(Long id) {
        Lettore lettore = lettori.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("Lettore", id));
        return toResponse(lettore);
    }

    @Transactional
    public LettoreResponse crea(LettoreRequest richiesta) {
        return toResponse(lettori.save(new Lettore(richiesta.nominativo())));
    }

    private static LettoreResponse toResponse(Lettore lettore) {
        return new LettoreResponse(lettore.getId(), lettore.getNominativo());
    }
}
