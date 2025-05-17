package main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

class SpritesAparte {
    private BufferedImage[] arvore;
    private int frameAtual;
    private int variacao;

    public SpritesAparte() {
        arvore = new BufferedImage[4];
        carregarSprites();
        frameAtual = 0;
        variacao = 0;
    }

    private void carregarSprites() {
        try {
            
        arvore[0] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/arvoreNormal.png"));
        arvore[1] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/arvoreMeioQueimada.png"));
        arvore[2] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/arvoreQueimando.png"));
        arvore[1] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/arvoreCarbonizada.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void atualizar(int novaVar){

        this.variacao = Math.abs(novaVar) % arvore.length;
    }

}