package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;


//inacabado (POR ENQUANTO!! ESTAMOS CHEGANDO LA!!)

class GamePanel extends JPanel implements KeyListener {
    private enum GameState { TITULO, JOGANDO, HISTORIA }
    private GameState currentState = GameState.TITULO;

    // recursos gráficos
    private BufferedImage tituloBackground;
    private BufferedImage personagemImagem;
    private ImageIcon fundoImagem;
    private Font customFont;
    
    // elementos da tela de título
    private final String[] opcoes = {"Iniciar", "Historia"};
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
        // carregar a fonte customizada que criamos para o jogo
        InputStream fonteStream = getClass().getClassLoader().getResourceAsStream("resources/main/assets/Uicool.ttf");
        if (fonteStream == null) {
            System.err.println("ERRO: Uicool.ttf não encontrado em main/assets/!");
        } else {
            customFont = Font.createFont(Font.TRUETYPE_FONT, fonteStream).deriveFont(40f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        }

        // carregar o background do titulo
        InputStream tituloStream = getClass().getClassLoader().getResourceAsStream("resources/main/assets/TITULO_bg.png");
        if (tituloStream == null) {
            System.err.println("ERRO: TITULO_bg.png não encontrado em main/assets/!");
        } else {
            tituloBackground = ImageIO.read(tituloStream);
        }

        // carregar o personagem
        InputStream personagemStream = getClass().getClassLoader().getResourceAsStream("resources/main/assets/personagemprincipalplaceholder.png");
        if (personagemStream == null) {
            System.err.println("ERRO: personagemprincipalplaceholder.png não encontrado em main/assets/!");
        } else {
            personagemImagem = ImageIO.read(personagemStream);
        }

        // Carregar o fundo animado
        java.net.URL fundoURL = getClass().getClassLoader().getResource("resources/main/assets/yippe.gif");
        if (fundoURL == null) {
            System.err.println("ERRO: yippe.gif não encontrado em main/assets/!");
        } else {
            fundoImagem = new ImageIcon(fundoURL);
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
            if (currentState == GameState.JOGANDO) {
                atualizarPosicao();
            }
            repaint();
        });
        gameTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        switch (currentState) {
            case TITULO:
                desenharTelaTitulo(g);
                break;
            case HISTORIA:
                desenharTelaHistoria(g);
                break;
            case JOGANDO:
                desenharJogo(g);
                break;
        }
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
            desenharTextoCentralizado(g, opcoes[i], 500 + i * 60);
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

        if (currentState == GameState.TITULO) {
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
        } else if (currentState == GameState.HISTORIA && codigo == KeyEvent.VK_SPACE) {
            currentState = GameState.TITULO;
        }
    }

    private void executarOpcao() {
        switch (opcaoSelecionada) {
            case 0: // iniciar
                currentState = GameState.JOGANDO;
                break;
            case 1: // história
                currentState = GameState.HISTORIA;
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