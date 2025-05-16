import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


//ImageIO.read(new File("assets/icones/IconeDesconhecido.png"));

class Projetil{
    int x, y;
    int velprojetil = 20;
    int largprojetil = 40, altprojetil = 40;

    public Projetil(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void mov(){
        y -= velprojetil;   // Move o projetil
    }

    public void desenho(Graphics g){
        g.setColor(Color.red);
        g.fillRect(x, y, largprojetil, altprojetil);
    
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, largprojetil, altprojetil);
    }

    public BufferedImage pegaImagem(){
        BufferedImage imgProjetil = null;
        try {
            imgProjetil = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/icones/IconeDesconhecido.png"));
       } catch ( Exception e) {
        e.printStackTrace();
        }
        return imgProjetil;
    }
}

//inacabado