package it.mauriziobotto.banca.web;

import it.mauriziobotto.banca.domain.TipoMovimento;
import it.mauriziobotto.banca.service.ContoService;
import it.mauriziobotto.banca.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ConsultazioniController {

    private final ReportService report;
    private final ContoService conti;

    public ConsultazioniController(ReportService report, ContoService conti) {
        this.report = report;
        this.conti = conti;
    }

    @GetMapping("/consultazioni")
    public String consulta(
            @RequestParam(defaultValue = "") String vista,
            @RequestParam(required = false) TipoMovimento tipo,
            @RequestParam(defaultValue = "") String numero,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dal,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate al,
            Model model) {
        var dalEffettivo = dal == null ? LocalDate.now().minusMonths(1) : dal;
        var alEffettivo = al == null ? LocalDate.now() : al;
        model.addAttribute("conti", conti.elenco());
        model.addAttribute("tipi", TipoMovimento.values());
        model.addAttribute("vista", vista);
        model.addAttribute("tipoSelezionato", tipo);
        model.addAttribute("numero", numero);
        model.addAttribute("dal", dalEffettivo);
        model.addAttribute("al", alEffettivo);
        model.addAttribute("movimenti", List.of());
        model.addAttribute("contiNegativi", List.of());

        switch (vista) {
            case "saldo-negativo" -> model.addAttribute("contiNegativi", report.contiConSaldoNegativo());
            case "tipo" -> {
                if (tipo != null) {
                    model.addAttribute("movimenti", report.perTipo(tipo));
                }
            }
            case "date" -> model.addAttribute("movimenti", report.traDate(dalEffettivo, alEffettivo));
            case "conto" -> {
                if (!numero.isBlank()) {
                    model.addAttribute("movimenti", report.perConto(numero.trim()));
                }
            }
            case "conto-date" -> {
                if (!numero.isBlank()) {
                    model.addAttribute("movimenti",
                            report.perContoEIntervallo(numero.trim(), dalEffettivo, alEffettivo));
                }
            }
            default -> {
            }
        }
        return "consultazioni";
    }
}
