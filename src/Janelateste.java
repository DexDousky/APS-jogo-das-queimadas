import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Janelateste extends JPanel implements KeyListener {

    private int x = 160; // posição lateral
    private int y = 160; // posição da altura

    public Janelateste() {
        addKeyListener(this);
        setFocusable(true); // isso deixa o Jpanel detectar os inputs do teclado
        requestFocusInWindow();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight()); // cria o fundo preto

        // cria o circulo
        g.setColor(Color.ORANGE);
        g.fillOval(x, y, 60, 60);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // faz as teclas influenciarem no movimento da bola
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:  x -= 10; break; // esquerda
            case KeyEvent.VK_RIGHT: x += 10; break; // direita
        }
        repaint(); // isso meio q apaga e cria denovo o circulo com um "frame" novo fazendo que ele mude de posição de verdade
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("TESTE DE JANELA");//nome da janela
        Janelateste jogo = new Janelateste();
        frame.add(jogo);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
} 
