import javax.swing.JFrame;
import javax.swing.JPanel;

public class SegundoPrototipo extends JPanel{

    public static void main(String[] args){
    JFrame frame = new JFrame("Segundo Prototipo");
    SegundoPrototipo jogo = new SegundoPrototipo();
    frame.add(jogo);
    frame.setSize(1280,740);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    
    
    }
}
