import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class Janelateste extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Desenha um fundo
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Desenha um círculo
        g.setColor(Color.ORANGE);
        g.fillOval(100, 100, 100, 100);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("TESTE DE JANELA");
        Janelateste jogo = new Janelateste();
        frame.add(jogo);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
