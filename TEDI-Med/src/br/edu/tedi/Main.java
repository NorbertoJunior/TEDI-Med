package br.edu.tedi;
import br.edu.tedi.view.TelaPrincipal;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.Font;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            for (Object chave : UIManager.getDefaults().keySet()) {
                if (UIManager.get(chave) instanceof FontUIResource) UIManager.put(chave,new FontUIResource("SansSerif",Font.PLAIN,17));
            }
            new TelaPrincipal().setVisible(true);
        });
    }
}
