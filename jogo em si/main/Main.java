package main;
import javax.swing.JFrame;
public class Main {
    public static void main(String[] args){

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // da pra fechar
        window.setResizable(false); // nao da pra definir o tamanho dela fora do script
        window.setTitle(" FUNNY "); // nomea
        window.setSize(1280, 740);
        window.setLocationRelativeTo(null); // vai aparecer no centro da tela
        window.setVisible(true); // vai aparecer
    }
}
