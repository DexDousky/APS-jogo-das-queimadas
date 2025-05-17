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
    private enum GameState { TITULO, JOGANDO, HISTORIA, CREDITOS, EASTEREGG }
    // estado inicial do jogo
    private GameState EstadoAtual = GameState.TITULO;

    private Font FonteCustomizada;
    private Font SegFonteCustomizada;
    
    // elementos da tela de título
    private final String[] opcoes = {
        "Iniciar", 
        "Historia",
        "Creditos"
    };
    private final String [] pessoas = {
        "Matheus Belarmino: @DexDousky ", 
        "João Victor: @Sr.DarkFrame ", 
        "Augusto: @GUGU369a", 
        "Diogo Freitas: @Diogodefreitassavastano", 
        "Maria: @Vortex"
    };

    private int opcaoSelecionada = 0;
    
    // elementos do jogo
    private int posX = 575;
    private int posY = 600;
    private final int VELOCIDADE = 9;
    private final boolean[] teclasPressionadas = new boolean[256];
    private Timer gameTimer;
    private int HP = 5;
    private int pontuacao = 0;
    private int tempo = 0;

    public GamePanel() {
        this.gameTimer = null;
        carregarRecursos();
        configurarPainel();
        iniciarGameLoop();
    }

    // fundos
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

    private void carregarRecursos() {
        try {
        personagemImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/personagemprincipalplaceholder.png"));
        TituloBG = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/TITULO_bg.png"));
        credBG = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/credfundo.png"));

        // carregamento dos icones dos Devs
        MatheusImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/icones/Matheus.png"));
        JoaoImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/icones/João.png"));
        AugustoImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/icones/Augusto.png"));
        DiogoImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/icones/Diogo.png"));
        MariaImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/icones/Maria.png"));
        Grama = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/grama.png"));
        Gato = new ImageIcon(getClass().getClassLoader().getResource("assets/yippe.gif"));
        
        
        // Carregar as fontes
        
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
        setFocusable(true);
        addKeyListener(this);
    }

    private void iniciarGameLoop() {
            
        
        gameTimer = new Timer(16, _ -> {
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
            case EASTEREGG:
                desenharEasterEgg(g);    
            break;
        }
    }
    // desenhos dos States

    // tela de titulo
    // aqui desenhamos a tela de titulo, com o fundo, o texto e as opcoes
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

    // easter egg
    // aqui desenhamos o easter egg, com o fundo, o texto e as opcoes
    private void desenharEasterEgg(Graphics g) {
        // fundo da tela de titulo
      
        if (Gato != null) {
            g.drawImage(Gato.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
          if (FonteCustomizada != null) {
            g.setFont(FonteCustomizada);
        }
        g.setColor(Color.WHITE);
        desenharTextoCentralizado(g, "yIIppiii", 300);
    }
    
    // tela de creditos
    // ... preciso mesmo explicar?
    private void desenharCreditos(Graphics g) {
            
            if (credBG != null) {
                g.drawImage(credBG, 0, 0, getWidth(), getHeight(), this);
            }
            if (FonteCustomizada != null) {
                g.setFont(FonteCustomizada);
            }

            // tipo de grafico (nova cor(Red: o tanto de vermelho, Green: o tanto de verde , Blue: o tanto de azul, Alpha: a opacidade "o quanto que vai tar visivel"))
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

            int Largura = 200;                                                                // Largura fixa
            int Altura = (int) (342.0 / 477 * Largura);                                       // mesmo alterado, matem a proporção original 477x342
            int EspacamentoVertical = 128;                                                    // espaço entre os ícones
            int yBase = 30;                                                                   // posição Y inicial do ícone
            int margemEsquerda = 20;                                                          // alto explicativo, "duh".
            
            

            // exibe cada pessoa e seu ícone
            if (SegFonteCustomizada != null) {
                g.setFont(SegFonteCustomizada);
            }

            FontMetrics fm = g.getFontMetrics();                                                // pega a medida da fonte escolhida acima
            
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
        g.drawString("Pressione ESC para voltar", 600, 690);
    }

    
    // tela de historia
    private void desenharTelaHistoria(Graphics g) {
        // sobreposição semi-transparente
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // texto
        g.setColor(Color.WHITE);
        
        if (SegFonteCustomizada != null) {
                g.setFont(SegFonteCustomizada);
            }

        desenharTextoCentralizado(g, "Place holder da historia", 300);
        desenharTextoCentralizado(g, "[Pressione ESC para voltar]", 600);
    }

    // tela de jogo
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
                case KeyEvent.VK_G:
                    EstadoAtual = GameState.CREDITOS;
                    break;
            }
        } else if (EstadoAtual == GameState.HISTORIA && tecla == KeyEvent.VK_ESCAPE) {
            EstadoAtual = GameState.TITULO;
        } else if (EstadoAtual == GameState.CREDITOS && tecla == KeyEvent.VK_ESCAPE) {
            EstadoAtual = GameState.TITULO;
        } else if (EstadoAtual == GameState.JOGANDO && tecla == KeyEvent.VK_ESCAPE) {
            EstadoAtual = GameState.TITULO;
        }  else if (EstadoAtual == GameState.EASTEREGG && tecla == KeyEvent.VK_ESCAPE) {
            EstadoAtual = GameState.TITULO;
        } else if (EstadoAtual == GameState.CREDITOS && tecla == KeyEvent.VK_Y) {
            EstadoAtual = GameState.EASTEREGG;
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