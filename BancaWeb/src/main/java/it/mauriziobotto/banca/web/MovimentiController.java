package it.mauriziobotto.banca.web;

import it.mauriziobotto.banca.domain.TipoMovimento;
import it.mauriziobotto.banca.service.ContoService;
import it.mauriziobotto.banca.service.MovimentoService;
import it.mauriziobotto.banca.web.form.MovimentoForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/movimenti")
public class MovimentiController {

    private final MovimentoService movimenti;
    private final ContoService conti;

    public MovimentiController(MovimentoService movimenti, ContoService conti) {
        this.movimenti = movimenti;
        this.conti = conti;
    }

    @GetMapping("/nuovo")
    public String nuovo(Model model) {
        var form = new MovimentoForm();
        form.setDataOperazione(LocalDate.now());
        form.setDataValuta(LocalDate.now());
        model.addAttribute("form", form);
        caricaDati(model);
        return "movimento-form";
    }

    @PostMapping
    public String registra(@Valid @ModelAttribute("form") MovimentoForm form,
                           BindingResult bindingResult, Model model,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            caricaDati(model);
            return "movimento-form";
        }
        movimenti.registra(form.getNumeroConto(), form.getTipo(), form.getImporto(),
                form.getCausale(), form.getDataOperazione(), form.getDataValuta());
        redirectAttributes.addFlashAttribute("messaggio", "Movimento registrato e saldo aggiornato.");
        return "redirect:/consultazioni?vista=conto&numero=" + form.getNumeroConto();
    }

    private void caricaDati(Model model) {
        model.addAttribute("conti", conti.elenco());
        model.addAttribute("tipi", TipoMovimento.values());
    }
}
