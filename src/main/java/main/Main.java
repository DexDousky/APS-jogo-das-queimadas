package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame janela = new JFrame("Queimada Crash");
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            janela.setResizable(false);
            janela.setSize(1260, 740);
            janela.setLocationRelativeTo(null);
            
            GamePanel gamePanel = new GamePanel();
            janela.add(gamePanel);
            janela.setVisible(true);
            gamePanel.requestFocusInWindow();
        });
    }
}