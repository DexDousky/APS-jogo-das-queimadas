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
        window.setSize(1280, 740);
        window.setLocationRelativeTo(null);
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.setVisible(true);
        gamePanel.requestFocusInWindow();
    }
}

class GamePanel extends JPanel implements KeyListener {
    private BufferedImage imagem;
    private int posX = 602;
    private int posY = 630;
    private final int VELOCIDADE = 10;
    private final boolean[] keysPressed = new boolean[256];
    private final Timer gameTimer;

    public GamePanel() {
        // Carregar imagem
        carregarImagem();
        
        // Configurações do painel
        setBackground(Color.BLACK);
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
            InputStream inputStream = getClass().getResourceAsStream("main.assets.personagemprincipalplaceholder.png");
            if (inputStream != null) {
                imagem = ImageIO.read(inputStream);
            } else {
                System.err.println("Erro: Imagem não encontrada! Criando placeholder...");
                criarPlaceholder();
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar imagem:");
            e.printStackTrace();
            criarPlaceholder();
        }
    }

    private void criarPlaceholder() {
        imagem = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = imagem.createGraphics();
        g2d.setColor(Color.RED);
        g2d.fillRect(0, 0, 50, 50);
        g2d.dispose();
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
            posX = Math.max(0, Math.min(posX, getWidth() - imagem.getWidth()));
            posY = Math.max(0, Math.min(posY, getHeight() - imagem.getHeight()));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagem != null) {
            g.drawImage(imagem, posX, posY, this);
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