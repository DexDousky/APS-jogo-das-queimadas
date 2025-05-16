class SpritesAparte {
    private BufferedImage[] arvore;
    private int frameAtual;
    private int variacao;

    public SpritesAparte() {
        arvore = new BufferedImage[4];
        carregarSprites();
        frameAtual = 0;
        variacao = 0;
    }

    private void carregarSprites() {
        try {
            
        Arvore[0] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/arvoreNormal.png"));
        Arvore[1] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/arvoreMeioQueimada.png"));
        Arvore[2] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/arvoreQueimando.png"));
        Arvore[1] = ImageIO.read(getClass().getClassLoader().getResourceAsStream("assets/arvoreCarbonizada.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public atualizar(int novaVar){

        this.variacao = Math.abs(novaVar) % arvore.Length;
    }

}