
package com.example.magazzino.ui;

import com.example.magazzino.dao.ArticoloDAO;
import com.example.magazzino.model.Articolo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ArticoliFrame extends JFrame {

    private final ArticoloDAO dao = new ArticoloDAO();

    private DefaultTableModel model;
    private JTable table;

    private JTextField idField;
    private JTextField nomeField;
    private JTextField prezzoField;

    private JTextField searchField;
    private JButton searchBtn;
    private JButton resetSearchBtn;

    private JButton addBtn;
    private JButton updateBtn;
    private JButton deleteBtn;

    public ArticoliFrame() {
        setTitle("Gestione Articoli - Swing + JDBC");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        initUi();
        loadAll();
    }

    private void initUi() {
        // --- Tabella ---
        model = new DefaultTableModel(new String[]{"ID", "Nome", "Prezzo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(22);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                if (r != -1) {
                    idField.setText(String.valueOf(model.getValueAt(r, 0)));
                    nomeField.setText(String.valueOf(model.getValueAt(r, 1)));
                    prezzoField.setText(String.valueOf(model.getValueAt(r, 2)));
                }
            }
        });

        // --- Pannello ricerca ---
        searchField = new JTextField(20);
        searchBtn = new JButton("Cerca");
        resetSearchBtn = new JButton("Mostra Tutti");

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cerca per nome:"));
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(resetSearchBtn);

        searchBtn.addActionListener(e -> onSearch());
        resetSearchBtn.addActionListener(e -> { searchField.setText(""); loadAll(); });

        // --- Pannello input ---
        idField = new JTextField(6); idField.setEditable(false);
        nomeField = new JTextField(20);
        prezzoField = new JTextField(10);

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(Box.createHorizontalStrut(15));
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(nomeField);
        formPanel.add(Box.createHorizontalStrut(15));
        formPanel.add(new JLabel("Prezzo:"));
        formPanel.add(prezzoField);

        // --- Pulsanti CRUD ---
        addBtn = new JButton("Aggiungi");
        updateBtn = new JButton("Modifica");
        deleteBtn = new JButton("Elimina");

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionsPanel.add(addBtn);
        actionsPanel.add(updateBtn);
        actionsPanel.add(deleteBtn);

        addBtn.addActionListener(e -> onAdd());
        updateBtn.addActionListener(e -> onUpdate());
        deleteBtn.addActionListener(e -> onDelete());

        // --- Layout principale ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.NORTH);
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(actionsPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void onSearch() {
        String q = searchField.getText().trim();
        if (q.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inserisci parte del nome da cercare.", "Ricerca", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        List<Articolo> risultati = dao.searchByName(q);
        updateTable(risultati);
        clearForm();
    }

    private void onAdd() {
        String nome = nomeField.getText().trim();
        String prezzoStr = prezzoField.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Il nome è obbligatorio.", "Validazione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double prezzo;
        try {
            prezzo = Double.parseDouble(prezzoStr.replace(',', '.'));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Prezzo non valido. Usa un numero (es. 12.50).", "Validazione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Articolo nuovo = new Articolo(nome, prezzo);
        dao.insert(nuovo);
        loadAll();
        selectRowById(nuovo.getId());
        JOptionPane.showMessageDialog(this, "Articolo aggiunto con ID " + nuovo.getId(), "Inserimento", JOptionPane.INFORMATION_MESSAGE);
        clearForm();
    }

    private void onUpdate() {
        String idStr = idField.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleziona prima un articolo dalla tabella.", "Modifica", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nome = nomeField.getText().trim();
        String prezzoStr = prezzoField.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Il nome è obbligatorio.", "Validazione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double prezzo;
        try {
            prezzo = Double.parseDouble(prezzoStr.replace(',', '.'));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Prezzo non valido. Usa un numero (es. 12.50).", "Validazione", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Articolo a = new Articolo(Integer.parseInt(idStr), nome, prezzo);
        if (dao.update(a)) {
            loadAll();
            selectRowById(a.getId());
            JOptionPane.showMessageDialog(this, "Articolo aggiornato.", "Modifica", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Aggiornamento non riuscito.", "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onDelete() {
        String idStr = idField.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleziona prima un articolo dalla tabella.", "Elimina", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = Integer.parseInt(idStr);
        int conferma = JOptionPane.showConfirmDialog(this, "Eliminare l'articolo con ID " + id + "?", "Conferma", JOptionPane.YES_NO_OPTION);
        if (conferma == JOptionPane.YES_OPTION) {
            if (dao.delete(id)) {
                loadAll();
                JOptionPane.showMessageDialog(this, "Articolo eliminato.", "Elimina", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Eliminazione non riuscita.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadAll() {
        List<Articolo> lista = dao.getAll();
        updateTable(lista);
    }

    private void updateTable(List<Articolo> lista) {
        model.setRowCount(0);
        for (Articolo a : lista) {
            model.addRow(new Object[]{a.getId(), a.getNome(), a.getPrezzo()});
        }
        table.clearSelection();
    }

    private void clearForm() {
        idField.setText("");
        nomeField.setText("");
        prezzoField.setText("");
    }

    private void selectRowById(int id) {
        for (int r = 0; r < model.getRowCount(); r++) {
            Object val = model.getValueAt(r, 0);
            if (val instanceof Integer && (Integer) val == id) {
                table.setRowSelectionInterval(r, r);
                table.scrollRectToVisible(table.getCellRect(r, 0, true));
                break;
            }
        }
    }
}
