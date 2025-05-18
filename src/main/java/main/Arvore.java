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
        arvore[3] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/arvoreCarbonizada.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void desenhar(java.awt.Graphics g,int x,int y){
        if (arvore[0] != null){
            g.drawImage(arvore[0], x, y, null);
        }
        if (arvore[1] !=null) {
            g.drawImage(arvore[1], x, y, null);
        }
        if (arvore[2] != null){
            g.drawImage(arvore[2],x,y, null);
        }
        if (arvore[3] != null){
            g.drawImage(arvore[3], x, y, null);
        }
    }
    public void atualizar(int novaVar){

        this.variacao = Math.abs(novaVar) % arvore.length;
    }
}