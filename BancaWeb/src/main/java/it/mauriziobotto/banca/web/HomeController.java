package it.mauriziobotto.banca.web;

import it.mauriziobotto.banca.service.ClienteService;
import it.mauriziobotto.banca.service.ContoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ClienteService clienti;
    private final ContoService conti;

    public HomeController(ClienteService clienti, ContoService conti) {
        this.clienti = clienti;
        this.conti = conti;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("numeroClienti", clienti.elenco().size());
        model.addAttribute("numeroConti", conti.elenco().size());
        return "index";
    }
}
