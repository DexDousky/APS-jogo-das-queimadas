package main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;


//inacabado (POR ENQUANTO!! ESTAMOS CHEGANDO LA!!) 
//Detalhe, agora está funcionando pra mim também! tá mostrando as Imagens em minha tela guys, GG, não tá dando mais o problema lá que deu, vamos terminar o game.

class GamePanel extends JPanel implements KeyListener {
    // estados do jogo
    //utilizameos private enum para evitar que o usuario
    private enum GameState { TITULO, JOGANDO, HISTORIA, CREDITOS }
    // estado inicial do jogo
    private GameState EstadoAtual = GameState.TITULO;

    // recursos gráficos
    private BufferedImage TituloBG;
    private BufferedImage credBG;
    private BufferedImage personagemImagem;
    private ImageIcon Gato;
    private BufferedImage Grama;
    private BufferedImage Arvore;

    // icones dos creditos
    private BufferedImage MatheusImagem;
    private BufferedImage JoaoImagem;
    private BufferedImage AugustoImagem;
    private BufferedImage DiogoImagem;
    private BufferedImage MariaImagem;


    private Font FonteCustomizada;
    private Font SegFonteCustomizada;
    
    // elementos da tela de título
    private final String[] opcoes = {
        "Iniciar", 
        "Historia",
        "Creditos"
    };
    private final String [] pessoas = {
        "Matheus Belarmino (DexDousky)", 
        "Joao Victor (Sr.DarkFrame)", 
        "Augusto ( GUGU369a )", 
        "Diogo Freitas (Siogodefreitassavastano)", 
        "Maria (Vortex)"
    };

    private int opcaoSelecionada = 0;
    
    // elementos do jogo
    private int posX = 575;
    private int posY = 600;
    private final int VELOCIDADE = 6;
    private final boolean[] teclasPressionadas = new boolean[256];
    private Timer gameTimer;

    public GamePanel() {
        this.gameTimer = null;
        carregarRecursos();
        configurarPainel();
        iniciarGameLoop();
    }

    private void carregarRecursos() {
    try {
        // carregar o personagem
        InputStream personagemStream = getClass().getClassLoader().getResourceAsStream("assets/personagemprincipalplaceholder.png");
        if (personagemStream == null) {
            System.err.println("ERRO: personagemprincipalplaceholder.png não encontrado em assets/!");
        } else {
            personagemImagem = ImageIO.read(personagemStream);
        }

        // carregar a fonte customizada que criamos para o jogo e outra
        InputStream fonteStream = getClass().getClassLoader().getResourceAsStream("assets/fontes/Uicool.ttf");
        if (fonteStream == null) {
            System.err.println("ERRO: Uicool.ttf não encontrado em assets/!");
        } else {
            FonteCustomizada = Font.createFont(Font.TRUETYPE_FONT, fonteStream).deriveFont(50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(FonteCustomizada);
        }

        InputStream fonteStream2 = getClass().getClassLoader().getResourceAsStream("assets/fontes/pixel-latin.ttf");
        if (fonteStream2 == null) {
            System.err.println("ERRO: pixel-latin.ttf não encontrado em assets/!");
        } else {
            SegFonteCustomizada = Font.createFont(Font.TRUETYPE_FONT, fonteStream2).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(SegFonteCustomizada);
        }

        // carregar o background do titulo
        InputStream tituloStream = getClass().getClassLoader().getResourceAsStream("assets/TITULO_bg.png");
        if (tituloStream == null) {
            System.err.println("ERRO: TITULO_bg.png não encontrado em assets/icones/!");
        } else {
            TituloBG = ImageIO.read(tituloStream);
        }

        InputStream credStream = getClass().getClassLoader().getResourceAsStream("assets/credfundo.png");
        if (credStream == null) {
            System.err.println("ERRO: TITULO_bg.png não encontrado em assets/icones/!");
        } else {
            credBG = ImageIO.read(credStream);
        }
        // icones dos creditos

        InputStream MatheusStream = getClass().getClassLoader().getResourceAsStream("assets/icones/Matheus.png");
        if (MatheusStream == null) {
            System.err.println("ERRO: Matheus.png não encontrado em assets/icones/!");
            getClass().getClassLoader().getResourceAsStream("assets/icones/IconeDesconhecido.png");
        } else {
            MatheusImagem = ImageIO.read(MatheusStream);
        }
        
        InputStream JoaoStream = getClass().getClassLoader().getResourceAsStream("assets/icones/João.png");
        if (JoaoStream == null) {
            System.err.println("ERRO: João.png não encontrado em assets/icones/!");
        } else {
            JoaoImagem = ImageIO.read(JoaoStream);
        }

        InputStream AugustoStream = getClass().getClassLoader().getResourceAsStream("assets/icones/Augusto.png");
        if (AugustoStream == null) {
            System.err.println("ERRO: Augusto.png não encontrado em assets/icones/!");
        } else {
            AugustoImagem = ImageIO.read(AugustoStream);
        }
        
        InputStream DiogoStream = getClass().getClassLoader().getResourceAsStream("assets/icones/Diogo.png");
        if (DiogoStream == null) {
            System.err.println("ERRO: Diogo.png não encontrado em assets/icones/.png!");
        } else {
            DiogoImagem = ImageIO.read(DiogoStream);
        }

        InputStream MariaStream = getClass().getClassLoader().getResourceAsStream("assets/icones/Maria.png");
        if (MariaStream == null) {
            System.err.println("ERRO: Maria.png não encontrado em assets/icones/!");
        } else {
            MariaImagem = ImageIO.read(MariaStream);
        }

        //
        if (MatheusImagem == null || JoaoImagem == null || AugustoImagem == null || DiogoImagem == null || MariaImagem == null) {
            System.err.println("ERRO: Aconselho não selecionar os créditos, pois as imagens não foram carregadas corretamente.");
            System.err.println("Atualize o caminho das imagens ou verifique se elas estão no diretório correto.");
        } else {
            // Aqui você pode adicionar o código para usar as imagens carregadas
        }

        // carregar a grama 
        InputStream gramaStream = getClass().getClassLoader().getResourceAsStream("assets/grama.png");
        if (gramaStream == null) {
            System.err.println("ERRO: grama.png não encontrado em assets/!");
        } else {
            Grama = ImageIO.read(gramaStream);
        }

        // Carregar o fundo animado
        java.net.URL Gatogif = getClass().getClassLoader().getResource("assets/yippe.gif");
        if (Gatogif == null) {
            System.err.println("ERRO: yippe.gif não encontrado em assets/!");
        } else {
            Gato = new ImageIcon(Gatogif);
        }
    } catch (IOException | FontFormatException e) {
        e.printStackTrace();
    }
}

    private void configurarPainel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
    }

    private void iniciarGameLoop() {
            
        
        gameTimer = new Timer(16, e -> {
            if (EstadoAtual == GameState.JOGANDO) {
                atualizarPosicao();
            }
            repaint();
        });
        gameTimer.start();
    }

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
        }
    }

 private void desenharCreditos(Graphics g) {
        
        if (credBG != null) {
            g.drawImage(credBG, 0, 0, getWidth(), getHeight(), this);
        }
        if (FonteCustomizada != null) {
            g.setFont(FonteCustomizada);
        }

        // tipo de grafico (nova cor(Red: tanto faz, Green: tanto faz, Blue: tanto faz, Alpha: tanto faz))
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        desenharTextoCentralizado(g, "Creditos", 40);

        // exibe cada pessoa e seu ícone
    
        BufferedImage[] imgs = new BufferedImage[] {
            MatheusImagem, 
            JoaoImagem, 
            AugustoImagem, 
            DiogoImagem, 
            MariaImagem
        };
        
        // configs do dimensionamento dos icones de credito

        int Largura = 200;                                      // Largura fixa
        int Altura = (int) (342.0 / 477 * Largura);             // mesmo alterado, matem a proporção original 477x342
        int EspacamentoVertical = 128;                          // espaço entre os ícones
        int yBase = 30;                                        // posição Y inicial do ícone
        int margemEsquerda = 50;                                // alto explicativo, "duh".
        
        

        // exibe cada pessoa e seu ícone
        if (SegFonteCustomizada != null) {
            g.setFont(SegFonteCustomizada);
        }

        FontMetrics fm = g.getFontMetrics();                    // pega a medida da fonte escolhida acima
        
        for (int i = 0; i < pessoas.length; i++) {
        
        // Posição do ícone
        int xIcone = margemEsquerda;
        int yIcone = yBase + (i * EspacamentoVertical);
        
        // Desenha o ícone redimensionado proporcionalmente
        if (imgs[i] != null) {
            g.drawImage(imgs[i], xIcone, yIcone, Largura, Altura, this);
        }
        
        // Calcula posição Y do texto (centralizado verticalmente com o ícone)
        int yTexto = yIcone + (Altura - fm.getHeight()) / 2 + fm.getAscent();
        
        // Posição X do texto (ícone + margem)
        int xTexto = xIcone + Largura + 20;
        
        g.drawString(pessoas[i], xTexto, yTexto);
    }

    // Mensagem de volta
    g.drawString("Pressione ESPAÇO para voltar", 600, 690);
}

    private void desenharTelaTitulo(Graphics g) {
        posX = 575;
        posY = 600;
        // fundo da tela de titulo
        if (TituloBG != null) {
            g.drawImage(TituloBG, 0, 0, getWidth(), getHeight(), this);
        }

        // título
        if (FonteCustomizada != null) {
            g.setFont(FonteCustomizada);
        }

        // opções
        for (int i = 0; i < opcoes.length; i++) {
            g.setColor(i == opcaoSelecionada ? Color.YELLOW : Color.WHITE);
            desenharTextoCentralizado(g, opcoes[i], 560 + i * 60);
        }
    }

    private void desenharTelaHistoria(Graphics g) {
        // sobreposição semi-transparente
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // texto
        g.setColor(Color.WHITE);

        desenharTextoCentralizado(g, "Place holder da historia", 300);
        desenharTextoCentralizado(g, "[Pressione ESPAÇO para voltar]", 600);
    }

    private void desenharJogo(Graphics g) {
        // gif placeholder de fundo
        if (Grama != null) {
            g.drawImage(Grama, 0, 0, getWidth(), getHeight(), this);
        }
        
        // jogador/player
        if (personagemImagem != null) {
            g.drawImage(personagemImagem, posX, posY, 100, 100, this);
        }
    }

    private void desenharTextoCentralizado(Graphics g, String texto, int y) {
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(texto)) / 2;
        g.drawString(texto, x, y);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        teclasPressionadas[tecla] = true;

        if (EstadoAtual == GameState.TITULO) {
            switch (tecla) {
                case KeyEvent.VK_W:
                    opcaoSelecionada = Math.max(0, opcaoSelecionada - 1);
                    break;
                case KeyEvent.VK_UP :
                    opcaoSelecionada = Math.max(0, opcaoSelecionada - 1);
                    break;
                case KeyEvent.VK_S:
                    opcaoSelecionada = Math.min(opcoes.length - 1, opcaoSelecionada + 1);
                    break;
                case KeyEvent.VK_DOWN:
                    opcaoSelecionada = Math.min(opcoes.length - 1, opcaoSelecionada + 1);
                    break;
                case KeyEvent.VK_ENTER:
                    executarOpcao();
                    break;
            }
        } else if (EstadoAtual == GameState.HISTORIA && tecla == KeyEvent.VK_SPACE) {
            EstadoAtual = GameState.TITULO;
        } else if (EstadoAtual == GameState.CREDITOS && tecla == KeyEvent.VK_SPACE) {
            EstadoAtual = GameState.TITULO;
        } else if (EstadoAtual == GameState.JOGANDO && tecla == KeyEvent.VK_SPACE) {
            EstadoAtual = GameState.TITULO;
        }
    }

    private void executarOpcao() {
        switch (opcaoSelecionada) {
            case 0: // iniciar
                EstadoAtual = GameState.JOGANDO;
                break;
            case 1: // história
                EstadoAtual = GameState.HISTORIA;
                break;
            case 2: // créditos
                EstadoAtual = GameState.CREDITOS;
                break;
        }
    }

    private void atualizarPosicao() {
        if (teclasPressionadas[KeyEvent.VK_UP]) posY -= VELOCIDADE;
        if (teclasPressionadas[KeyEvent.VK_DOWN]) posY += VELOCIDADE;
        if (teclasPressionadas[KeyEvent.VK_LEFT]) posX -= VELOCIDADE;
        if (teclasPressionadas[KeyEvent.VK_RIGHT]) posX += VELOCIDADE;

        // limites da tela
        posX = Math.max(0, Math.min(posX, getWidth() - 100));
        posY = Math.max(0, Math.min(posY, getHeight() - 100));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        teclasPressionadas[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}


//porque que java e haxe sao tao estranhos em questao de condicionais e algumas coisas?
//eu tenho que colocar chaves e dps deixar um else em cima das chaves do if???
//sei que provavelmente o senhor nao sabe exatamente o porque disso, ate porque todas as linguagens tem suas particularidades, mas .... nossa
//e sim eu to aprendendo a programar em haxe
//-- Matheus Belarmino