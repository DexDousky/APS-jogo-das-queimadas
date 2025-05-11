//Lembando q pode  ter algun errado de manipulação

package entidade;

//import main.(Painel do Jogo(ou algo assim))
import main.Teladojogo;

public class Jogador extends Entity {
    Janelateste jt;
    TeladoJogo TJ; 

    public Jogador (Janelateste jt, TeladoJogo TJ) {

        this.gt = gt;
        this.TJ = TJ; 
    }
    public void setDefaultValues () {
        
        x = 100;
        y = 100;
        speed = 4;
    }

    //Métodos o qual possibilita a movimentação do jogador em diferentes direções, sendo elas: cima, baixo, esquerda e direita
    public void update() {
        if (TJ.cimaPressed == true) {
            y -= speed; //Necessario uma mudança para movimento do jogador
        }
        else if (TJ.baixoPressed == true) {
            y += speed;
        }
        else if (TJ.esquerdaPressed == true) {
            x -= speed;
        }
        else if (TJ.direitaPressed == true) {
            x += speed;
        }
    }
    public void draw(GraficosBidimensional gb) {
        g2.setColor(Color.white);
    }
}

