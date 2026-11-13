package executorcod;

import telas.TelaConsultaMedicamento;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            TelaConsultaMedicamento tela = new TelaConsultaMedicamento();
            tela.setVisible(true);
        });
    }
}
