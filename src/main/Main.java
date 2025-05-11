package main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Queimada Crash");
        window.setSize(1260, 740);
        window.setLocationRelativeTo(null);
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.setVisible(true);
        gamePanel.requestFocusInWindow();

        // Janela do game
    }
}
class GamePanel extends JPanel implements KeyListener {
    private BufferedImage imagem;
    private int posX = 575;
    private int posY = 600;
    private final int VELOCIDADE = 20;
    private final boolean[] keysPressed = new boolean[256];
    private final Timer gameTimer;

    //Local do personagem

    public GamePanel() {
        carregarImagem(); // Carregar imagem
        
        setBackground(Color.BLACK);// Configurações do painel
        setFocusable(true);
        addKeyListener(this);
        
        // Configurar game loop (60 FPS)
        gameTimer = new Timer(16, e -> {
            atualizarPosicao();
            repaint();
        });
        gameTimer.start();
    }

    private void carregarImagem() {
        try {
            // Caminho corrigido para a imagem
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("main/assets/personagemprincipalplaceholder.png");
            if (inputStream != null) {
                imagem = ImageIO.read(inputStream);
            } else {
                System.err.println("Erro: Imagem não encontrada! ");
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagem != null) {
            g.drawImage(imagem, posX, posY, 100, 100, this);
        }
    }
    
    private void atualizarPosicao() {
        // Movimento vertical
        if (keysPressed[KeyEvent.VK_UP]) posY -= VELOCIDADE;
        if (keysPressed[KeyEvent.VK_DOWN]) posY += VELOCIDADE;
        
        // Movimento horizontal
        if (keysPressed[KeyEvent.VK_LEFT]) posX -= VELOCIDADE;
        if (keysPressed[KeyEvent.VK_RIGHT]) posX += VELOCIDADE;
        
        // Manter dentro dos limites da tela
        if (imagem != null) {

            int UpdAlt = 100;
            int UpdLarg = 100;
            posX = Math.max(0, Math.min(posX, getWidth() - UpdLarg));
            posY = Math.max(0, Math.min(posY, getHeight() - UpdAlt));
       
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}