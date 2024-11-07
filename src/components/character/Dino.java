package components.character;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Dino extends Obstacle {

  public Dino(int x, int y, int s, JPanel game) {
    super(x, y, s, game);
  }
  @Override
    public void draw(Graphics g) {

        // Draw the scaled image at its current x, y position
        g.drawImage(obstacleImage, x, y, 75, 75, null);
    }
  @Override
    public void loadImage() {
      obstacleImage = new ImageIcon("images/character/dino-idle1.gif").getImage();
    }
  
}


