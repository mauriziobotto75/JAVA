package it.mauriziobotto.banca.web;

import it.mauriziobotto.banca.domain.Cliente;
import it.mauriziobotto.banca.service.ClienteService;
import it.mauriziobotto.banca.web.form.ClienteForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clienti")
public class ClientiController {

    private final ClienteService clienti;

    public ClientiController(ClienteService clienti) {
        this.clienti = clienti;
    }

    @GetMapping
    public String elenco(Model model) {
        model.addAttribute("clienti", clienti.elenco());
        return "clienti";
    }

    @GetMapping("/nuovo")
    public String nuovo(Model model) {
        model.addAttribute("form", new ClienteForm());
        model.addAttribute("modifica", false);
        return "cliente-form";
    }

    @GetMapping("/{codice}/modifica")
    public String modifica(@PathVariable String codice, Model model) {
        Cliente cliente = clienti.trova(codice);
        var form = new ClienteForm();
        form.setCodice(cliente.getCodice());
        form.setNome(cliente.getNome());
        form.setIndirizzo(cliente.getIndirizzo());
        model.addAttribute("form", form);
        model.addAttribute("modifica", true);
        return "cliente-form";
    }

    @PostMapping
    public String salva(@Valid @ModelAttribute("form") ClienteForm form,
                        BindingResult bindingResult, Model model,
                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("modifica", clienti.elenco().stream()
                    .anyMatch(cliente -> cliente.getCodice().equals(form.getCodice())));
            return "cliente-form";
        }
        clienti.salva(form.getCodice(), form.getNome(), form.getIndirizzo());
        redirectAttributes.addFlashAttribute("messaggio", "Cliente salvato.");
        return "redirect:/clienti";
    }
}
