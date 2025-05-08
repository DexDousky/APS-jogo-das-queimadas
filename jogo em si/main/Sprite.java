package main;

public class Sprite {
    private String name;
    private int x, y, wight, height;

    public Sprite(String name, int x, int y, int wight, int height){
        this.name = name;
        this.x = x;
        this.y = y;
        this.wight = wight;
        this.height = height;
    }
    public String getName(){ return name; }
    public int getX() {return x;}
    public int getY() {return y;}
    public int getWight() {return wight;}
    public int getHeight() {return height;}

}
