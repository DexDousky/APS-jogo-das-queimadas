package main;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class Teladojogo extends JPanel{

  public int TelaW = 1280;
  public int TelaH = 740;


  public Teladojogo(){

    this.setPreferredSize(new Dimension(TelaW, TelaH));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
  }
    
}


