package it.mauriziobotto.banca.web;

import it.mauriziobotto.banca.service.ClienteService;
import it.mauriziobotto.banca.service.ContoService;
import it.mauriziobotto.banca.service.RegolaApplicativaException;
import it.mauriziobotto.banca.web.form.ContoForm;
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

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
@RequestMapping("/conti")
public class ContiController {

    private final ContoService conti;
    private final ClienteService clienti;

    public ContiController(ContoService conti, ClienteService clienti) {
        this.conti = conti;
        this.clienti = clienti;
    }

    @GetMapping
    public String elenco(Model model) {
        model.addAttribute("conti", conti.elenco());
        return "conti";
    }

    @GetMapping("/nuovo")
    public String nuovo(Model model) {
        var form = new ContoForm();
        form.setDataApertura(LocalDate.now());
        form.setFido(BigDecimal.ZERO);
        model.addAttribute("form", form);
        caricaClienti(model);
        return "conto-form";
    }

    @PostMapping
    public String apri(@Valid @ModelAttribute("form") ContoForm form,
                       BindingResult bindingResult, Model model,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            caricaClienti(model);
            return "conto-form";
        }
        conti.apri(form.getNumero(), form.getCodiceCliente(), form.getAbi(), form.getCab(),
                form.getFiliale(), form.getDataApertura(), form.getFido());
        redirectAttributes.addFlashAttribute("messaggio", "Conto aperto.");
        return "redirect:/conti";
    }

    @PostMapping("/{numero}/fido")
    public String aggiornaFido(@PathVariable String numero, BigDecimal fido,
                               RedirectAttributes redirectAttributes) {
        if (fido == null || fido.signum() < 0 || fido.scale() > 2 || fido.precision() > 13) {
            throw new RegolaApplicativaException(
                    "Il fido deve essere positivo e avere al massimo 11 cifre intere e 2 decimali.");
        }
        conti.aggiornaFido(numero, fido);
        redirectAttributes.addFlashAttribute("messaggio", "Fido aggiornato.");
        return "redirect:/conti";
    }

    @PostMapping("/{numero}/chiudi")
    public String chiudi(@PathVariable String numero, RedirectAttributes redirectAttributes) {
        conti.chiudi(numero);
        redirectAttributes.addFlashAttribute("messaggio", "Conto chiuso.");
        return "redirect:/conti";
    }

    private void caricaClienti(Model model) {
        model.addAttribute("clienti", clienti.elenco());
    }
}
