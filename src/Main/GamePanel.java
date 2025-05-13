package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;


//inacabado (POR ENQUANTO!! ESTAMOS CHEGANDO LA!!)

class GamePanel extends JPanel implements KeyListener {
    // estados do jogo
    //utilizameos private enum para evitar que o usuario
    private enum GameState { TITULO, JOGANDO, HISTORIA, CREDITOS }
    // estado inicial do jogo
    private GameState EstadoAtual = GameState.TITULO;

    // recursos gráficos
    private BufferedImage tituloBackground;
    private BufferedImage personagemImagem;
    private ImageIcon fundoImagem;
    private Font customFont;
    
    // elementos da tela de título
    private final String[] opcoes = {"Iniciar", "Historia","Creditos"};
    private final String [] pessoas = {"Matheus Belarmino (Dex Dousky)", "Joao Victor (Sr.DarkFrame)", "Augusto (Vortex)", "Diogo Freitas (BlueHollow)", "Maria (Vortex)"};
    private int opcaoSelecionada = 0;
    
    // elementos do jogo
    private int posX = 575;
    private int posY = 600;
    private final int VELOCIDADE = 20;
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
        // Fonte customizada
        InputStream fonteStream = getClass().getClassLoader().getResourceAsStream("assets/Uicool.ttf");
        if (fonteStream != null) {
            customFont = Font.createFont(Font.TRUETYPE_FONT, fonteStream).deriveFont(50f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);
        } else {
            System.err.println("ERRO: Uicool.ttf não encontrado em assets/!");
        }

        // Fundo do título
        tituloBackground = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/TITULO_bg.png"));

        // Personagens
        personagemImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/personagemprincipalplaceholder.png"));

        BufferedImage matheusImg = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/Matheus.png"));
        BufferedImage joaoImg = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/Joao.png")); 
        BufferedImage augustoImg = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/Augusto.png"));
        BufferedImage diogoImg = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/Diogo.png"));
        BufferedImage mariaImg = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/Maria.png"));

        // Fundo animado
        java.net.URL fundoURL = getClass().getClassLoader().getResource("assets/yippe.gif");
        fundoImagem = (fundoURL != null) ? new ImageIcon(fundoURL) : null;

    } catch (IOException | FontFormatException e) {
        e.printStackTrace();
    }
}

    // Configurações do painel

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
        // sobreposição semi-transparente
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // texto
        g.setColor(Color.WHITE);
        desenharTextoCentralizado(g, "Place holder dos creditos", 300);
        desenharTextoCentralizado(g, "[Pressione ESPAÇO para voltar]", 600);
    }

    private void desenharTelaTitulo(Graphics g) {
        // fundo da tela de titulo
        if (tituloBackground != null) {
            g.drawImage(tituloBackground, 0, 0, getWidth(), getHeight(), this);
        }

        // título
        if (customFont != null) {
            g.setFont(customFont);
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
        if (fundoImagem != null) {
            g.drawImage(fundoImagem.getImage(), 0, 0, getWidth(), getHeight(), this);
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
        int codigo = e.getKeyCode();
        teclasPressionadas[codigo] = true;

        if (EstadoAtual == GameState.TITULO) {
            switch (codigo) {
                case KeyEvent.VK_UP:
                    opcaoSelecionada = Math.max(0, opcaoSelecionada - 1);
                    break;
                case KeyEvent.VK_DOWN:
                    opcaoSelecionada = Math.min(opcoes.length - 1, opcaoSelecionada + 1);
                    break;
                case KeyEvent.VK_ENTER:
                    executarOpcao();
                    break;
            }
        } else if (EstadoAtual == GameState.HISTORIA && codigo == KeyEvent.VK_SPACE) {
            EstadoAtual = GameState.TITULO;
        } else if (EstadoAtual == GameState.CREDITOS && codigo == KeyEvent.VK_SPACE) {
            EstadoAtual = GameState.TITULO;
        } else if (EstadoAtual == GameState.JOGANDO && codigo == KeyEvent.VK_SPACE) {
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