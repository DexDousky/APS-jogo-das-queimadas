package main;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame();
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // pode ser fechada
            window.setResizable(false); // nao pode se redefinir o tamanho
            window.setTitle("FUNNY"); // titulo
            window.setSize(1280, 740);
            window.setLocationRelativeTo(null); // aparece no centro da tela
            
            // cria um painel pra imagem
            JPanel panel = new JPanel() {
                private BufferedImage image;
                
                {
                    try {
                        image = ImageIO.read(new File("assets/personagemprincipalplaceholder.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if (image != null) {
                        g.drawImage(image, 0, 0, this);
                    }
                }
            };
            
            window.add(panel);
            window.setVisible(true); // faz ficar visivel
        });
    }
}