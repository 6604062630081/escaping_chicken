package components.character;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.Graphics;

import javax.swing.*;

public class Obstacle {
  public int x,y, width, height;
  public int speed;
  public int xStart;
  public Image obstacleImage;

  public Obstacle(int x, int y, int s, JPanel game) {
    this.x = x;
    this.y = y;
    this.xStart = x;
    this.speed = s;

    move(game);
  }

  public void move(JPanel game) {
    Timer timer = new Timer(50, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        x -= speed;
        game.repaint();
        if (x < 0) {
          x = xStart + 5;
        }
      }
    });
    timer.start();
  }

  public void loadImage() {
      
  }

  public void draw(Graphics g) {
    g.drawImage(obstacleImage, x, y, null);
  }

  public int getWidth() {
    return obstacleImage.getWidth(null);  
  }

  public int getHeight() {
    return obstacleImage.getHeight(null);
  }
}



