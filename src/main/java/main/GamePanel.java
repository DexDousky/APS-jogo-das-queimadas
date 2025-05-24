package main;

// ==================================================
// IMPORTAÇÕES
// ==================================================

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
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

// ==================================================
// CLASSE PRINCIPAL DO JOGO
// ==================================================
class GamePanel extends JPanel implements KeyListener {
    // ==================================================
    // ESTADOS DO JOGO
    // ==================================================
    private enum GameState {
        TITULO,JOGANDO,HISTORIA,TUTORIAL,CREDITOS,EASTEREGG,GAMEOVER,VITORIA
    }
    
    // ==================================================
    // ELEMENTOS DE INTERFACE
    // ==================================================
    private int opcaoSelecionada = 0;
    private int numpg = 1;
    
    private final String[] opcoes = { "Iniciar", "Historia", "Tutorial", "Creditos" };
    private final String[] pessoas = {
            "Matheus Belarmino: @DexDousky",
            "João Victor: @Sr.DarkFrame",
            "Augusto: @GUGU369a",
            "Diogo Freitas: @Diogodefreitassavastano",
            "Maria: @Vortex"
    };

    // ==================================================
    // FONTES
    // ==================================================
    private Font FonteCustomizada;
    private Font SegFonteCustomizada;

    // ==================================================
    // SISTEMA DE JOGO
    // ==================================================
    private GameState EstadoAtual = GameState.TITULO;
    private int pontuacao = 0;
    private int tempoRestante = 120;
    private long ultimoTempoAtualizado = 0;
    private Timer gameTimer;
    private final boolean[] teclasPressionadas = new boolean[256];

    // ==================================================
    // ELEMENTOS DO JOGADOR E DO JOGO
    // ==================================================
    private int posX = 575;
    private int posY = 600;
    private final int VELOCIDADE = 9;
    private int HP = 5;
    private final int maxHP = 5;
    private ArrayList<Projetil> Projeteis = new ArrayList<>();
    private ArrayList<Arvore> Arvores = new ArrayList<>();
    private ArrayList<Faisca> Faiscas = new ArrayList<>();
    private boolean Invencibilidade = false;
    private long TempoDeI = 0;
    private static final long InvTempo = 500;

    // ==================================================
    // RECURSOS GRÁFICOS
    // ==================================================
    private BufferedImage TituloBG, CredBG, HistoriaBG;
    private BufferedImage jogador,invencivel, Grama;
    private ImageIcon Gato;
    private BufferedImage MatheusImagem, JoaoImagem, AugustoImagem, DiogoImagem, MariaImagem;
    private BufferedImage tabua, coracao, moldura, bagulho;
    private BufferedImage pgum, pgdois, pgtres, pgquatro, pgcinco, pgseis, pgsete, pgoito, pgnove, pgdez, pgonze, pgdoze, pgtreze, pgquatorze;
    private BufferedImage arvoren, arvoremq, arvoreq, arvorec;

    // ==================================================
    // INICIALIZAÇÃO DO JOGO
    // ==================================================
    public GamePanel() {
        this.gameTimer = null;
        CarregamentosdeAssets();
        configurarPainel();
        iniciarGameLoop();
        IniciarArvores();
    }    

    private void configurarPainel() {
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);
    }

    // ==================================================
    // CARREGAMENTO DE RECURSOS
    // ==================================================
    private void CarregamentosdeAssets() {
        try {
            tabua = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/hud/Tabua.png"));
            coracao = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/hud/Coracao.png"));
            moldura = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/moldura.png"));
            bagulho = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/bagulho.png"));

            TituloBG = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/TITULO_bg.png"));
            CredBG = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/credfundo.png"));
            HistoriaBG = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/background.png"));

            jogador = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/jogador.png"));
            invencivel = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/invencivel.png"));
            Gato = new ImageIcon(getClass().getClassLoader().getResource("assets/yippe.gif"));
            Grama = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/grama.png"));

            MatheusImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/icones/Matheus.png"));
            JoaoImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/icones/João.png"));
            AugustoImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/icones/Augusto.png"));
            DiogoImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/icones/Diogo.png"));
            MariaImagem = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/icones/Maria.png"));

            pgum = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pgum.png"));
            pgdois = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pgdois.png"));
            pgtres = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pgtres.png"));
            pgquatro = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pgquatro.png"));
            pgcinco = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pgcinco.png"));
            pgseis = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pgseis.png"));
            pgsete = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pgsete.png"));
            pgoito = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pgoito.png"));
            pgnove = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pgnove.png"));
            pgdez = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pgdez.png"));
            pgonze = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pgonze.png"));
            pgdoze = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pgdoze.png"));
            pgtreze = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pgtreze.png"));
            pgquatorze = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/historia/pgquatorze.png"));

            arvoren = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/arvoreNormal.png"));
            arvoremq = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/arvoreMeioQueimada.png"));
            arvoreq = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/arvoreQueimando.png"));
            arvorec = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/arvoreCarbonizando.png"));

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

    // ==================================================
    // LÓGICA PRINCIPAL DO JOGO
    // ==================================================
    private void iniciarGameLoop() {
        gameTimer = new Timer(16, _ -> {
            if (EstadoAtual == GameState.JOGANDO) {
                atualizarPosicaoJ();
                atualizarTempo();
                AtualizarProjeteis();
                atualizarFaiscas();
                ChequeDeColizoesdaArvore();
                chequeDeColizoesF();
                VerificarVitoria();
            }
            repaint();
        });
        gameTimer.start();
    }
    
    // ==================================================================
    // COLISÕES, ATUALIZAÇÕES, POSIÇÕES, CHEQUES E CLASSES ADICIONAIS
    // ==================================================================
    private void IniciarArvores() {
        int margem = 50;
        int larguraArea = getWidth() - margem * 2;
        int alturaArea = getHeight() - 200 - 78; // 78 é a altura da HUD, então só botei um -78 pra ajudar em não desenhar as arvores em baixo da mesma
        
        for (int i = 0; i < 10; i++) {
            boolean posicaoValida;
            int tentativas = 0;
            int x = 0, y = 0;
            
            do {
                posicaoValida = true;
                x = margem + (int) (Math.random() * larguraArea);
                y = 100 + (int) (Math.random() * alturaArea); // Começa abaixo da HUD
                
                // Verificar colisão com outras árvores
                for (Arvore a : Arvores) {
                    if (Math.abs(a.x - x) < 160 && Math.abs(a.y - y) < 160) {
                        posicaoValida = false;
                        break;
                    }
                }
                tentativas++;
            } while (!posicaoValida && tentativas < 100);
            
            Arvores.add(new Arvore(x, y));
        }
    }

    private void ResetarPontuacoes() {
        HP = maxHP;
        pontuacao = 0;
        tempoRestante = 120;
        ultimoTempoAtualizado = 0;
        posX = 575;
        posY = 600;
        Arvores.clear();
        IniciarArvores();
        Projeteis.clear();
        Faiscas.clear();
        Invencibilidade = false;
    }

    private void VerificarVitoria() {
        for(Arvore a : Arvores) {
            if(!a.curada) return;
        }
        EstadoAtual = GameState.VITORIA;
    }

    private void atualizarPosicaoJ() {
        posX = Math.max(0, Math.min(posX, getWidth() - 100));
        posY = Math.max(0, Math.min(posY, getHeight() - 100));

        if (teclasPressionadas[KeyEvent.VK_UP]) posY -= VELOCIDADE;
        if (teclasPressionadas[KeyEvent.VK_DOWN]) posY += VELOCIDADE;
        if (teclasPressionadas[KeyEvent.VK_LEFT]) posX -= VELOCIDADE;
        if (teclasPressionadas[KeyEvent.VK_RIGHT]) posX += VELOCIDADE;
    }

    private void AtualizarProjeteis() {
        for (int i = Projeteis.size() - 1; i >= 0; i--) {
            Projetil p = Projeteis.get(i);
            p.atualizarp();
            if (p.ForaDaTela()) {
                Projeteis.remove(i);
            }
        }
    }

    private void ChequeDeColizoesdaArvore() {
        for (int i = Projeteis.size() - 1; i >= 0; i--) {
            Projetil p = Projeteis.get(i);
            for (int j = Arvores.size() - 1; j >= 0; j--) {
                Arvore Arvore = Arvores.get(j);
                if (Arvore.ChequeDeColizoes(p.x, p.y)) {
                    Arvore.hit();
                    pontuacao += 10;
                    Projeteis.remove(i);
                    if (Arvore.contadorDeAcertos >= 5) {
                        pontuacao += 100;
                        
                    }
                    break;
                }
            }
        }
    }

    private void chequeDeColizoesF() {
        if (Invencibilidade && System.currentTimeMillis() - TempoDeI >= InvTempo) {
            Invencibilidade = false;
        }

        for (int i = Faiscas.size() - 1; i >= 0; i--) {
            Faisca s = Faiscas.get(i);
            if (s.ColideComJogador(posX, posY, 100, 100) && !Invencibilidade) {
                HP--;
                Invencibilidade = true;
                TempoDeI = System.currentTimeMillis();
                Faiscas.remove(i);
                if (HP <= 0) {
                    EstadoAtual = GameState.GAMEOVER;
                }
            }
        }
    }

    private void atualizarFaiscas() {
        for (Arvore Arvore : Arvores) {
            if (Arvore.SpriteAtual != arvoren && Math.random() < 0.02) {
                Faiscas.add(new Faisca(Arvore.x + 50, Arvore.y + 50, posX + 50, posY + 50));
            }
        }

        for (int i = Faiscas.size() - 1; i >= 0; i--) {
            Faisca s = Faiscas.get(i);
            s.atualizarp();
            if (s.ForaDaTela(getWidth(), getHeight())) {
                Faiscas.remove(i);
            }
        }
    }

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
    
    private class Arvore {
        int x, y;
        int contadorDeAcertos = 0;
        BufferedImage SpriteAtual;
        boolean curada = false;

        Arvore(int x, int y) {
            this.x = x;
            this.y = y;
            SpriteAtual = arvorec;
        }

        void hit() {
            if(curada) return;
            
            contadorDeAcertos++;
            if (contadorDeAcertos >= 5) {
                SpriteAtual = arvoren;
                if (HP < maxHP){
                    HP ++;
                }
                curada = true;
            } else if (contadorDeAcertos == 1) {
                SpriteAtual = arvoreq;
            } else if (contadorDeAcertos == 3) {
                SpriteAtual = arvoremq;
            }
        }

        boolean ChequeDeColizoes(int px, int py) {
            return px >= x && px <= x + 150 && py >= y && py <= y + 150;
        }
    }

    private class Faisca {
        float x, y;
        float dx, dy;
        static final float PVelocidade = 3.0f;

        Faisca(float Xinicial, float Yinicial, float AlvoX, float AlvoY) {
            this.x = Xinicial;
            this.y = Yinicial;
            float angle = (float) Math.atan2(AlvoY - Yinicial, AlvoX - Xinicial);
            dx = (float) (Math.cos(angle) * PVelocidade);
            dy = (float) (Math.sin(angle) * PVelocidade);
        }

        void atualizarp() {
            x += dx;
            y += dy;
        }

        boolean ForaDaTela(int width, int height) {
            return x < 0 || x > width || y < 0 || y > height;
        }

        boolean ColideComJogador(int px, int py, int pw, int ph) {
            return x >= px && x <= px + pw && y >= py && y <= py + ph;
        }
    }
    
    // ==================================================
    // RENDERIZAÇÃO GRÁFICA
    // ==================================================
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        switch (EstadoAtual) {
            case TITULO -> desenharTelaTitulo(g);
            case HISTORIA -> desenharTelaHistoria(g);
            case TUTORIAL -> desenharTutorial(g);
            case CREDITOS -> desenharCreditos(g);
            case JOGANDO -> desenharJogo(g);
            case EASTEREGG -> desenharEasterEgg(g);
            case GAMEOVER -> desenharGameOver(g);
            case VITORIA -> desenharVitoria(g);
        }
    }

    private void desenharTelaTitulo(Graphics g) {
        if (TituloBG != null) {
            g.drawImage(TituloBG, 0, 0, getWidth(), getHeight(), this);
        }

        if (FonteCustomizada != null) {
            g.setFont(FonteCustomizada);
        }

        for (int i = 0; i < opcoes.length; i++) {
            g.setColor(i == opcaoSelecionada ? Color.YELLOW : Color.WHITE);
            desenharTextoCentralizado(g, opcoes[i], 510 + i * 60);
        }
    }

    private void desenharJogo(Graphics g) {
        if (Grama != null) {
            g.drawImage(Grama, 0, 0, getWidth(), getHeight(), this);
        }

        for (Arvore Arvore : Arvores) {
            g.drawImage(Arvore.SpriteAtual, Arvore.x, Arvore.y, 150, 150, this);
        }

        for (Projetil p : Projeteis) {
            g.setColor(Color.BLUE);
            g.fillRect(p.x, p.y, 5, 10);
        }

        for (Faisca f : Faiscas) {
            g.setColor(Color.ORANGE);
            g.fillOval((int) f.x, (int) f.y, 10, 10);
        }

        if (jogador != null) {
            g.drawImage(jogador, posX, posY, 100, 100, this);
        }

        g.drawImage(tabua, 0, 0, getWidth(), 78, this);
        g.setColor(Color.WHITE);
        g.setFont(SegFonteCustomizada.deriveFont(50f));
        g.drawString("Pontos: " + pontuacao, 680, 53);
        String tempoFormatado = String.format("%02d:%02d", tempoRestante / 60, tempoRestante % 60);
        g.drawString("Tempo: " + tempoFormatado, 940, 53);

        for (int i = 0; i < HP; i++) {
            g.drawImage(coracao, 70 + (i * 65), 15, 50, 50, this);

        }

        if(Invencibilidade) {
            if (invencivel != null) {
            g.drawImage(invencivel, posX, posY, 100, 100, this);
            }
        }
    }

    private void desenharTelaHistoria(Graphics g) {
        g.drawImage(HistoriaBG, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(bagulho, 369, 550, 500, 100, this);
        BufferedImage[] paginas = { pgum, pgdois, pgtres, pgquatro, pgcinco, pgseis, pgsete, pgoito, pgnove, pgdez, pgonze, pgdoze, pgtreze, pgquatorze };

        if (numpg >= 1 && numpg <= paginas.length) {
            g.drawImage(paginas[numpg - 1], 380, 95, 490, 341, this);
        }

        g.drawImage(moldura, 365, 80, 543, 394, this);

        g.setColor(Color.WHITE);
        if (SegFonteCustomizada != null) {
            g.setFont(SegFonteCustomizada);
        }

        desenharTextoCentralizado(g, "Página: " + numpg, 610);

        g.setColor(Color.WHITE);
        if (FonteCustomizada != null) {
            g.setFont(FonteCustomizada);
        }

        desenharTextoCentralizado(g, "HISTORIA", 60);
        g.setColor(Color.WHITE);
        g.setFont(SegFonteCustomizada.deriveFont(30f));
        g.drawString("Pressione ESQUERDA ou DIREITA para mudar as páginas e ESC para voltar", 230, 690);
        
        if (numpg <11){
        desenharTextoCentralizado(g, "Desenho por: @DexDousky", 78);
        } else{
        desenharTextoCentralizado(g, "Desenho por: @Diogofreitassavastano", 78);
        }
    }

    private void desenharTutorial(Graphics g){

        if (TituloBG != null) {
            g.drawImage(TituloBG, 0, 0, getWidth(), getHeight(), this);
        }

        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);

        if (FonteCustomizada != null) {
            g.setFont(FonteCustomizada);
        }

        desenharTextoCentralizado(g, "TUTORIAL", 80);

        if (SegFonteCustomizada != null) {
            g.setFont(SegFonteCustomizada.deriveFont(28f));
        }

        g.drawString("Atire jatos de agua nas árvores, utilizando a Tecla ESPAÇO", 10, 200);
        g.drawString("e DESVIE das faiscas de fogo que as mesmas atiram em sua direção!!",10,230);
        g.drawString("Manuseie o seu movimento e mira com as SETAS para não ser atingido, senão...",10,260);
        g.drawString("VOCÊ TÁ FRITO!!",10,290);
        g.drawString("Essa é a representação da sua vida:",10,320);                                         g.drawImage(coracao, 380, 295, 30, 30, this);
        g.drawString("Se você for atingido por uma faisca voce perde um desses, mas se apagar ",10,350);
        g.drawString("o fogo de uma arvore você recupera um,então tome cuidado para não perder tudo",10,380);
        g.drawString("senão você MORRE.",10,410);

        if (SegFonteCustomizada != null) {
            g.setFont(SegFonteCustomizada);
        }
        desenharTextoCentralizado(g, "[Pressione ESC para voltar]", 600);
    }

    private void desenharCreditos(Graphics g) {
        if (CredBG != null) {
            g.drawImage(CredBG, 0, 0, getWidth(), getHeight(), this);
        }

        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);

        if (FonteCustomizada != null) {
            g.setFont(FonteCustomizada);
            desenharTextoCentralizado(g, "Creditos", 40);
        }

        BufferedImage[] imgs = { MatheusImagem, JoaoImagem, AugustoImagem, DiogoImagem, MariaImagem };

        if (SegFonteCustomizada != null) {
            g.setFont(SegFonteCustomizada);
        }

        int Largura = 200;
        int Altura = (int) (342.0 / 477 * Largura);
        int EspacamentoVertical = 128;
        int yBase = 30;
        int margemEsquerda = 20;

        FontMetrics fm = g.getFontMetrics();
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

        g.drawString("Pressione ESC para voltar", 740, 690);
    }

    private void desenharVitoria(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        if (FonteCustomizada != null) {
            g.setFont(FonteCustomizada);
        }
        desenharTextoCentralizado(g, "Vitoria", 300);
        if (SegFonteCustomizada != null) {
            g.setFont(SegFonteCustomizada);
        }
        desenharTextoCentralizado(g, "[Pressione ESC para voltar]", 600);
    }

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

    private void desenharTextoCentralizado(Graphics g, String texto, int y) {
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(texto)) / 2;
        g.drawString(texto, x, y);
    }
    
    // ==================================================
    // RECEPTORES E EXECUTORES DE INPUTS... ou entradas sei la
    // ==================================================
    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        teclasPressionadas[tecla] = true;

        switch (EstadoAtual) {
            case TITULO:
                InputdoTitulo(tecla);
                numpg = 1;
                break;
            case HISTORIA:
                InputdoHistoria(tecla);
                break;
            case JOGANDO:
                InputdoJogo(tecla);
                break;
            default:
                if (tecla == KeyEvent.VK_ESCAPE) {
                    EstadoAtual = GameState.TITULO;
                }
                break;
        }

        if (EstadoAtual == GameState.CREDITOS && tecla == KeyEvent.VK_Y) {
            EstadoAtual = GameState.EASTEREGG;
        }
    }

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
        }
    }

    private void InputdoHistoria(int tecla) {
        if (tecla == KeyEvent.VK_RIGHT || tecla == KeyEvent.VK_D) {
            numpg++;
        } else if (tecla == KeyEvent.VK_LEFT || tecla == KeyEvent.VK_A) {
            numpg--;
        }
        numpg = Math.max(1, Math.min(numpg, 14)); // AQUI SÓ FALA Q O NUMERO MINIMO DE PAGINAS É 1 E O MAXIMO É 11, SE NÃO PASSA DISSO E NÃO APARECE NENHUMA PAGINA NA MOLDURA
        if (tecla == KeyEvent.VK_ESCAPE) {
            EstadoAtual = GameState.TITULO;
        }
    }

    private void InputdoJogo(int tecla) {
        if (tecla == KeyEvent.VK_ESCAPE) {
            EstadoAtual = GameState.TITULO;
        }
        if (tecla == KeyEvent.VK_SPACE) {
            Projeteis.add(new Projetil(posX + 50, posY));
        }
    }

    private void executarOpcao() {
        switch (opcaoSelecionada) {
            case 0:
                EstadoAtual = GameState.JOGANDO;
                ResetarPontuacoes();
                break;
            case 1:
                EstadoAtual = GameState.HISTORIA;
                break;
            case 2:
                EstadoAtual = GameState.TUTORIAL;
                break;
            case 3:
                EstadoAtual = GameState.CREDITOS;
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        teclasPressionadas[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private class Projetil {
        int x, y;
        static final int PVelocidade = 10;

        Projetil(int Xinicial, int Yinicial) {
            this.x = Xinicial;
            this.y = Yinicial;
        }
        

        void atualizarp() {
            y -= PVelocidade;
        }

        boolean ForaDaTela() {
            return y < 0;
        }
    }
}