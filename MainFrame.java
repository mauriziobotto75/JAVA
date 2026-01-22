package it.botto.rubrica.ui;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Gestione Rubrica - PostgreSQL");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new JLabel("Progetto Maven Rubrica (PostgreSQL + CSV)", SwingConstants.CENTER));
    }
}