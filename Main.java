
package com.example.magazzino;

import javax.swing.SwingUtilities;
import com.example.magazzino.ui.ArticoliFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ArticoliFrame().setVisible(true));
    }
}
