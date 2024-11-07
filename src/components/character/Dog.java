package components.character;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Dog extends Obstacle {
  public Dog(int x, int y, int s, JPanel game) {
    super(x, y, s, game);

  }

  @Override
  public void loadImage() {
    obstacleImage = new ImageIcon("images/character/dog-1.gif").getImage(); 

    this.width = obstacleImage.getWidth(null);
    this.height = obstacleImage.getHeight(null);
  }
  
}


