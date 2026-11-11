package it.mauriziobotto.assicurazioni.web;

import it.mauriziobotto.assicurazioni.polizze.Polizza;
import it.mauriziobotto.assicurazioni.polizze.PolizzaService;
import it.mauriziobotto.assicurazioni.polizze.CodicePolizzaDuplicatoException;
import jakarta.validation.Valid;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/polizze")
public class PolizzeController {

    private final PolizzaService service;

    public PolizzeController(PolizzaService service) {
        this.service = service;
    }

    @GetMapping
    public String elenco(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String stato,
            Model model) {
        model.addAttribute("polizze", service.cerca(q, stato));
        model.addAttribute("q", q == null ? "" : q);
        model.addAttribute("stato", stato == null ? "" : stato);
        model.addAttribute("titolo", "Archivio polizze");
        return "polizze/elenco";
    }

    @GetMapping("/nuova")
    public String nuova(Model model) {
        model.addAttribute("polizza", new Polizza());
        model.addAttribute("azione", "Nuova polizza");
        model.addAttribute("modifica", false);
        return "polizze/modulo";
    }

    @PostMapping
    public String crea(
            @Valid @ModelAttribute("polizza") Polizza polizza,
            BindingResult errors,
            Model model,
            RedirectAttributes redirect) {
        if (errors.hasErrors()) {
            model.addAttribute("azione", "Nuova polizza");
            model.addAttribute("modifica", false);
            return "polizze/modulo";
        }
        try {
            service.crea(polizza);
            redirect.addFlashAttribute("messaggio", "Polizza inserita correttamente.");
            return "redirect:/polizze/" + polizza.getCodicePolizza();
        } catch (CodicePolizzaDuplicatoException exception) {
            errors.rejectValue("codicePolizza", "polizza.nonValida", exception.getMessage());
            model.addAttribute("azione", "Nuova polizza");
            model.addAttribute("modifica", false);
            return "polizze/modulo";
        }
    }

    @GetMapping("/{codice}")
    public String dettaglio(@PathVariable String codice, Model model) {
        Polizza polizza = service.trova(codice);
        model.addAttribute("polizza", polizza);
        model.addAttribute("oggi", LocalDate.now());
        return "polizze/dettaglio";
    }

    @GetMapping("/{codice}/modifica")
    public String modifica(@PathVariable String codice, Model model) {
        model.addAttribute("polizza", service.trova(codice));
        model.addAttribute("azione", "Modifica polizza");
        model.addAttribute("modifica", true);
        return "polizze/modulo";
    }

    @PostMapping("/{codice}")
    public String aggiorna(
            @PathVariable String codice,
            @Valid @ModelAttribute("polizza") Polizza polizza,
            BindingResult errors,
            Model model,
            RedirectAttributes redirect) {
        if (errors.hasErrors()) {
            polizza.setCodicePolizza(codice);
            model.addAttribute("azione", "Modifica polizza");
            model.addAttribute("modifica", true);
            return "polizze/modulo";
        }
        service.aggiorna(codice, polizza);
        redirect.addFlashAttribute("messaggio", "Polizza aggiornata.");
        return "redirect:/polizze/" + codice;
    }

    @PostMapping("/{codice}/pagamento")
    public String registraPagamento(
            @PathVariable String codice,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPagamento,
            RedirectAttributes redirect) {
        service.registraPagamento(codice, dataPagamento);
        redirect.addFlashAttribute("messaggio", "Pagamento registrato.");
        return "redirect:/polizze/" + codice;
    }

    @PostMapping("/{codice}/annullamento")
    public String annulla(
            @PathVariable String codice,
            @RequestParam(required = false) String conferma,
            RedirectAttributes redirect) {
        if (!"S".equalsIgnoreCase(conferma)) {
            redirect.addFlashAttribute("errore", "Annullamento non confermato.");
            return "redirect:/polizze/" + codice;
        }
        service.annulla(codice);
        redirect.addFlashAttribute("messaggio", "Polizza annullata.");
        return "redirect:/polizze";
    }
}
