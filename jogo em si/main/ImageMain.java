import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageMain {
    public static void main(String[]args){
         // O bloco try-catch serve para tratar possíveis erros (ex.: imagem não encontrada)
         try {
            // Aqui, indicamos o caminho para a imagem. Troque "caminho/para/sua/imagem.png"
            // pelo caminho real onde a imagem está armazenada no seu computador.

            BufferedImage image = ImageIO.read(new File(""));
            // Aqui, você pode fazer algo com a imagem, como exibi-la em um JFrame ou processá-la de alguma forma.
            System.out.println("Imagem carregada com sucesso!");
            // Depois de carregar, você pode usar o objeto "image" para desenhar em um componente
            // gráfico, se desejar criar uma interface para exibi-la.
        } catch (IOException e) {
            // Caso ocorra algum problema, a mensagem de erro será exibida no console.
            System.err.println("Erro ao carregar a imagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}