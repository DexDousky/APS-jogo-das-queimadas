import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Janelateste extends JPanel implements KeyListener {

    private int x = 100; // Coordenada X inicial
    private int y = 100; // Coordenada Y inicial

    public Janelateste() {
        addKeyListener(this);
        setFocusable(true); // Permite que o JPanel capture eventos de teclado
        requestFocusInWindow(); // Garante o foco no JPanel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Desenha um fundo
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Desenha um círculo
        g.setColor(Color.ORANGE);
        g.fillOval(x, y, 60, 60);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Move o círculo dependendo da tecla pressionada
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:  x -= 10; break; // Move para a esquerda
            case KeyEvent.VK_RIGHT: x += 10; break; // Move para a direita
        }
        repaint(); // Atualiza a tela para refletir a nova posição
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("TESTE DE JANELA");
        Janelateste jogo = new Janelateste();
        frame.add(jogo);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
