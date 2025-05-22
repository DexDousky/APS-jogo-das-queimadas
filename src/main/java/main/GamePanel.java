package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


// Oque falta?

// Adicionar um botao de atirar: 
// que faz o player atirar um jato de agua, que se em contato com o mesmo x e y de qualquer arvore, ela muda de sprite e adiciona 10 pontos ao player
// (o jato sai do mesmo x e y do player, e vai para cima.)

// Adicionar as arvores:
// o comportamento das arvores é simples, aparecem pelo menos umas 3 na tela em alguns lugares aleatorios utilizando do sprite /assets/arvoreCarbonizada.png
// comforme o player vai atirando jatos de agua na arvore, ela vai mudando de sprite, até chegar na arvora/Normal.png
// depois disso, ela some da tela e o player ganha 100 pontos

// Adicionar faiscas de fogo:
// o comportamento das faiscas pode ser um pouco mais complicadinho mas, enfim, baiscamente elas saem de qualquer sprite de arvore que esteja queimada ( /assets/arvoreQueimando.png , /assets/arvoreMeioQueimada.png e /assets/arvoreCarbonizando.png)
// se atingir o mesmo X e Y do player, o player perde 1 de HP mas, uns segundinhos que voces podem definir ai pra ter invencibilidade
// Porque se atingir o msm X e Y do player, ele pode ficar diminuindo o HP infinitamente, oque faz o player fucking morrer

// se voce apagar todas as arvores, va pra tela de vitoria

//simples :D

//qualquer asset q voces precisarem eu posso fazer, de qlqr forma eu vou estar trabalhando no State de Historia.

class GamePanel extends JPanel implements KeyListener {

    // todos os States do jogo são definidos aqui

    private enum GameState { 
        TITULO, 
        JOGANDO, 
        HISTORIA, 
        CREDITOS, 
        EASTEREGG,
        GAMEOVER
    }

    // estado atual do jogo

    private GameState EstadoAtual = GameState.TITULO;
    
    // fontes de texto 

    private Font FonteCustomizada;
    private Font SegFonteCustomizada;
    
    // opções do menu

    private final String[] opcoes = {
        "Iniciar", 
        "Historia",
        "Creditos"
    };
    
    // pessoas que desenvolveram o jogo

    private final String[] pessoas = {
        "Matheus Belarmino: @DexDousky", 
        "João Victor: @Sr.DarkFrame", 
        "Augusto: @GUGU369a", 
        "Diogo Freitas: @Diogodefreitassavastano", 
        "Maria: @Vortex"
    };

    // variaveis dos controles do jogo

    private int opcaoSelecionada = 0;
    private int posX = 575;
    private int posY = 600;
    private final int VELOCIDADE = 9;
    private final boolean[] teclasPressionadas = new boolean[256];
    private Timer gameTimer;
    private int HP = 5;
    private final int maxHP = 5;
    private int pontuacao = 0;
    private int tempoRestante = 120;
    private long ultimoTempoAtualizado = 0;

    // assets do jogo.

    private BufferedImage TituloBG, credBG, personagemImagem, Grama;
    private ImageIcon Gato;
    private BufferedImage MatheusImagem, JoaoImagem, AugustoImagem, DiogoImagem, MariaImagem;
    private BufferedImage pagina1, pagina2, pagina3, pagina4, pagina5, pagina6, pagina7, hfundo, moldura, bagulho;
    private BufferedImage tabua, coracao;

    // construtor 
    public GamePanel() {
        this.gameTimer = null;
        CarregamentosdeAssets();
        configurarPainel();
        iniciarGameLoop();
    }

    // aqui todos os recursos do jogo são carregados, como imagens, fontes e etc
    private void CarregamentosdeAssets() {
        try {

            // hud
            tabua = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/hud/Tabua.png"));
            coracao = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/hud/Coracao.png"));

            // assets do jogo
            personagemImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/personagemprincipalplaceholder.png"));
            TituloBG = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/TITULO_bg.png"));
            credBG = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/credfundo.png"));
            Gato = new ImageIcon(getClass().getClassLoader().getResource("assets/yippe.gif"));
            Grama = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/grama.png"));
            
            // icones de dev
            MatheusImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/icones/Matheus.png"));
            JoaoImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/icones/João.png"));
            AugustoImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/icones/Augusto.png"));
            DiogoImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/icones/Diogo.png"));
            MariaImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/icones/Maria.png"));
            

            //assets o menu e hitori
            pagina1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pagina1.png"));
            pagina2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pagina2.png"));
            pagina3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pagina3.png"));
            pagina4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pagina4.png"));
            pagina5 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pagina5.png"));
            pagina6 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pagina6.png"));
            pagina7 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pagina7.png"));


            // fontes
            InputStream fonteStream = getClass().getClassLoader().getResourceAsStream("assets/fontes/Uicool.ttf");
            FonteCustomizada = Font.createFont(Font.TRUETYPE_FONT, fonteStream).deriveFont(50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(FonteCustomizada);

            InputStream fonteStream2 = getClass().getClassLoader().getResourceAsStream("assets/fontes/Jersey_15.ttf");
            SegFonteCustomizada = Font.createFont(Font.TRUETYPE_FONT, fonteStream2).deriveFont(50f);
            
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }


    private void configurarPainel() {
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
    }

    // iniciar o game loop, que vai ser o responsavel por atualizar o jogo
    private void iniciarGameLoop() {
        gameTimer = new Timer(16, _ -> {
            if (EstadoAtual == GameState.JOGANDO) {
                atualizarPosicao();
                atualizarTempo();
            }
            repaint();
        });
        gameTimer.start();
    }

    // aqui a gente vai atualizar o tempo do jogo, e verificar se o tempo acabou
    // se o tempo acabar, o jogo vai pra tela de game over e o tempo vai ser zerado
    // e tambem é logico.
    private void atualizarTempo() {
        long agora = System.currentTimeMillis();
        if (ultimoTempoAtualizado == 0) ultimoTempoAtualizado = agora;
        
        long decorrido = agora - ultimoTempoAtualizado;
        if (decorrido >= 1000) {
            tempoRestante--;
            ultimoTempoAtualizado = agora;
            
            if (tempoRestante <= 0) {
                EstadoAtual = GameState.GAMEOVER;
                tempoRestante = 0;
            }
        }
    }

    // aqui a gente vai desenhar tudo que precisa ser desenhado na tela
    // dependendo do estado atual do jogo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        switch (EstadoAtual) {
            case TITULO:
                desenharTelaTitulo(g);
                break;
            case HISTORIA:
                desenharTelaHistoria(g);
                break;
            case CREDITOS:
                desenharCreditos(g);
                break;
            case JOGANDO:
                desenharJogo(g);
                break;
            case EASTEREGG:
                desenharEasterEgg(g);
                break;
            case GAMEOVER:
                desenharGameOver(g);
                break;
        }
    }

    private void desenharJogo(Graphics g) {
        //desenhar o BG (Background)
        if (Grama != null) {
            g.drawImage(Grama, 0, 0, getWidth(), getHeight(), this);
        }

        // o personagem principal
        if (personagemImagem != null) {
            g.drawImage(personagemImagem, posX, posY, 100, 100, this);
        }

        // a hud
        g.drawImage(tabua, 0, 0, getWidth(), 78, this);
        
        // definir a cor e a fonte a serem utilizadas aqui nesse estado
        g.setColor(Color.WHITE);
        g.setFont(SegFonteCustomizada.deriveFont(50f));

        // desenhar a pontuação e o tempo na hud
        g.drawString("Pontos: " + pontuacao, 680, 53);
        String tempoFormatado = String.format("%02d:%02d", tempoRestante/60, tempoRestante%60);
        g.drawString("Tempo: " + tempoFormatado, 940, 53);

        // desenhar a vida do jogador com base no HP
        for (int i = 0; i < HP; i++) {
            g.drawImage(coracao, 70 + (i * 65), 15, 50, 50, this);
        }
    }

    // aqui é para a tela de titulo
    private void desenharTelaTitulo(Graphics g) {
        if (TituloBG != null) {
            g.drawImage(TituloBG, 0, 0, getWidth(), getHeight(), this);
        }

        if (FonteCustomizada != null) {
            g.setFont(FonteCustomizada);
        }

        for (int i = 0; i < opcoes.length; i++) {
            g.setColor(i == opcaoSelecionada ? Color.YELLOW : Color.WHITE);
            desenharTextoCentralizado(g, opcoes[i], 560 + i * 60);
        }
    }

    // tela de game over
    private void desenharGameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.WHITE);
        
        if (FonteCustomizada != null) {
            g.setFont(FonteCustomizada);
        }
        desenharTextoCentralizado(g, "Game Over", 300);
        if (SegFonteCustomizada != null) {
            g.setFont(SegFonteCustomizada);
        }
        desenharTextoCentralizado(g, "[Pressione ESC para voltar]", 600);
    }

    // o easter egg
    private void desenharEasterEgg(Graphics g) {
        if (Gato != null) {
            g.drawImage(Gato.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
        if (FonteCustomizada != null) {
            g.setFont(FonteCustomizada);
        }
        g.setColor(Color.WHITE);
        desenharTextoCentralizado(g, "yIIppiii", 300);
    }

    // creditos
    private void desenharCreditos(Graphics g) {
        if (credBG != null) {
            g.drawImage(credBG, 0, 0, getWidth(), getHeight(), this);
        }
        
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        
        if (FonteCustomizada != null) {
            g.setFont(FonteCustomizada);
            desenharTextoCentralizado(g, "Creditos", 40);
        }

        BufferedImage[] imgs = {MatheusImagem, JoaoImagem, AugustoImagem, DiogoImagem, MariaImagem};
        
        if (SegFonteCustomizada != null) {
            g.setFont(SegFonteCustomizada);
        }

        int Largura = 200;
        int Altura = (int)(342.0 / 477 * Largura);
        int EspacamentoVertical = 128;
        int yBase = 30;
        int margemEsquerda = 20;
        
        FontMetrics fm = g.getFontMetrics(); // pega a fonte e suas medidas e calcular o tamanho dela pra poder desenhar o texto na tela
        
        for (int i = 0; i < pessoas.length; i++) {
            int xIcone = margemEsquerda;
            int yIcone = yBase + (i * EspacamentoVertical);
            
            if (imgs[i] != null) {
                g.drawImage(imgs[i], xIcone, yIcone, Largura, Altura, this);
            }
            
            int yTexto = yIcone + (Altura - fm.getHeight()) / 2 + fm.getAscent();
            int xTexto = xIcone + Largura + 20;
            g.drawString(pessoas[i], xTexto, yTexto);
        }

        g.drawString("Pressione ESC para voltar", 600, 690);
    }

    // e a tela de historia
    private void desenharTelaHistoria(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        
        if (SegFonteCustomizada != null) {
            g.setFont(SegFonteCustomizada);
        }
        desenharTextoCentralizado(g, "Place holder da historia", 300);
        desenharTextoCentralizado(g, "[Pressione ESC para voltar]", 600);
    }

    // funcao feita pra desenhar o texto centralizado na tela
    // ela calcula a largura do texto e o posiciona no meio da tela
    private void desenharTextoCentralizado(Graphics g, String texto, int y) {
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(texto)) / 2;
        g.drawString(texto, x, y);
    }

    // basicamente um ouvintes pro teclado em seus diferentes estados
    // aqui a gente vai definir o que acontece quando o jogador pressiona uma tecla
    // e o que acontece quando ele solta a tecla
    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        teclasPressionadas[tecla] = true;

        switch (EstadoAtual) {
            case TITULO:
                InputdoTitulo(tecla);
                break;
            case JOGANDO:
                InputdoJogo(tecla);
                break;
            default: // isso daqui simplifica pra toda vez que a tecla ESC for pressionada, o jogo volta pro menu principal, evitando aquele monte de elseif que ngm aguenta :sob:
                if (tecla == KeyEvent.VK_ESCAPE) {
                    EstadoAtual = GameState.TITULO;
                }
                break;
        }
        
        // condições pro easter egg abrir
        if (EstadoAtual == GameState.CREDITOS && tecla == KeyEvent.VK_Y) {
            EstadoAtual = GameState.EASTEREGG;
        }
        
        // tecla de testes ( plmrds lembra de remover isso depois que a gente acabar o jogo por tudo que ha de mais sagrado)
        if (EstadoAtual == GameState.JOGANDO && tecla == KeyEvent.VK_D) {
            HP--; // literalmente o i++ só q negativo E usando o HP
            if (HP <= 0) {
                EstadoAtual = GameState.GAMEOVER;
            }
        }
    }
    // mesma coisa de cima mas pro titulo
    private void InputdoTitulo(int tecla) {
        switch (tecla) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                opcaoSelecionada = Math.max(0, opcaoSelecionada - 1);
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                opcaoSelecionada = Math.min(opcoes.length - 1, opcaoSelecionada + 1);
                break;
            case KeyEvent.VK_ENTER:
                executarOpcao();
                break;
            case KeyEvent.VK_G:
                EstadoAtual = GameState.CREDITOS;
                break;
        }
    }

    private void InputdoJogo(int tecla) {
        if (tecla == KeyEvent.VK_ESCAPE) {
            EstadoAtual = GameState.TITULO;
        }
    }

    private void executarOpcao() {
        switch (opcaoSelecionada) {
            case 0: // iniciar
                EstadoAtual = GameState.JOGANDO;
                ResetarPontuacoes();
                break;
            case 1: // história
                EstadoAtual = GameState.HISTORIA;
                break;
            case 2: // créditos
                EstadoAtual = GameState.CREDITOS;
                break;
        }
    }

    private void ResetarPontuacoes() {
        HP = maxHP;
        pontuacao = 0;
        tempoRestante = 60;
        ultimoTempoAtualizado = 0;
        posX = 575;
        posY = 600;
    }

    private void atualizarPosicao() {
        
        // Limites da tela aplicados no personagem
        posX = Math.max(0, Math.min(posX, getWidth() - 100));
        posY = Math.max(0, Math.min(posY, getHeight() - 100));

        if (teclasPressionadas[KeyEvent.VK_UP]) posY -= VELOCIDADE;
        if (teclasPressionadas[KeyEvent.VK_DOWN]) posY += VELOCIDADE;
        if (teclasPressionadas[KeyEvent.VK_LEFT]) posX -= VELOCIDADE;
        if (teclasPressionadas[KeyEvent.VK_RIGHT]) posX += VELOCIDADE;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        teclasPressionadas[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}