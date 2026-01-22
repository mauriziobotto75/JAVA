package it.botto.rubrica.util;

import it.botto.rubrica.model.Contatto;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CSVExporter {
    public static void export(List<Contatto> contatti, File file) throws IOException {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            pw.println("Nome,Cognome,Telefono,Email");
            for (Contatto c : contatti) {
                pw.printf(""%s","%s","%s","%s"%n",
                        c.getNome(), c.getCognome(), c.getTelefono(), c.getEmail());
            }
        }
    }
}