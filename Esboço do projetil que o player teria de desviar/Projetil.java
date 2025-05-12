class projetil{
    int x, y;
    int velprojetil = 20;
    int largprojetil = 40, alt = 40;

    public projetil(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void mov(){
        y += velprojetil;   // Move o projetil
    }

    public void desenho(Graphics g){
        g.setColor(Color.red)
    
    }
}



//inacabado