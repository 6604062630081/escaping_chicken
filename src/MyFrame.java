import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import components.Event.checkEvent;
import components.background.ParallaxBackground;
import components.character.*;
import components.font.FontLoader;

import java.util.ArrayList;
import java.util.Random;

public class MyFrame extends JPanel implements KeyListener {
  private static final int GAME_SPEED = 20; 
  private static final int JUMP_HEIGHT = 60;
  private static final int TIME_LIMIT = 20;
  public int gameSpeed = GAME_SPEED;

  Chicken chick = new Chicken(129, 314);

  private ArrayList<Obstacle> obstacles = new ArrayList<>();
  private Random random = new Random();

  //bg1
  private ParallaxBackground backgroundLayer21;
  private ParallaxBackground backgroundLayer22;
  private ParallaxBackground backgroundLayer23;
  private ParallaxBackground backgroundLayer24;
  private ParallaxBackground backgroundLayer25;
  private ParallaxBackground backgroundLayer26;
  private ParallaxBackground backgroundLayer27;
  private ParallaxBackground tileLayer2;
  //bg2
  private ParallaxBackground backgroundLayer11;
  private ParallaxBackground backgroundLayer12;
  private ParallaxBackground backgroundLayer13;
  private ParallaxBackground tileLayer1;

  private ParallaxBackground bgStart = new ParallaxBackground(0, 0, 1, "images/backgroud/bg2/bg2-start.png");
  private ParallaxBackground bgOver = new ParallaxBackground(0, 0, 1, "images/backgroud/bg1/bg1-over.png");
  private ParallaxBackground titleImage = new ParallaxBackground(90,-50,1,"images/backgroud/bg2/title1-2.png");
  private ParallaxBackground chickIdle = new ParallaxBackground(215,110, 0, "images/ChickenPack/gif/ChickenIdle.gif");

  // time limit
  private int timeRemaining = TIME_LIMIT;
  private Timer countdownTimer;

  // font
  private Font customFont = FontLoader.loadCustomFont("src/components/font/SuperMarioBros. NES.ttf", 16);

  // game over 
  private int hitCount = 0;
  private boolean gameOver = false;
  private boolean gameStarted = false;

  private Thread startScreenThread;

  public MyFrame() {
      setLayout(null);
      this.addKeyListener(this);
      this.setFocusable(true); 
      this.requestFocusInWindow();

      // Set up background layers
      backgroundLayer11 = new ParallaxBackground(0, 0, 0, "images/backgroud/bg2/bg2-1.png");
      backgroundLayer12 = new ParallaxBackground(0, 24, 2, "images/backgroud/bg2/bg2-2-cloud.png");
      backgroundLayer13 = new ParallaxBackground(0, 284, 3, "images/backgroud/bg2/bg2-3-Sea.png");
      tileLayer1 = new ParallaxBackground(0, 350, 5, "images/backgroud/bg2/bg2-5-tillsets.png");

      backgroundLayer21 = new ParallaxBackground(0, 0, 1, "images/backgroud/bg1/bg1.png");
      backgroundLayer22 = new ParallaxBackground(10, -10, 1, "images/backgroud/bg1/bg1-1-cloud.png");
      backgroundLayer23 = new ParallaxBackground(0, 195, 2, "images/backgroud/bg1/bg1-2.png");
      backgroundLayer24 = new ParallaxBackground(0, 270, 2, "images/backgroud/bg1/bg1-3-grass.png");
      backgroundLayer25 = new ParallaxBackground(20, 260, 2, "images/backgroud/bg1/bg1-4-bush.png");
      backgroundLayer26 = new ParallaxBackground(20, -30, 1, "images/backgroud/bg1/bg1-5-tree.png");
      backgroundLayer27 = new ParallaxBackground(0, 302, 2, "images/backgroud/bg1/bg1-6-grass.png");
      tileLayer2 = new ParallaxBackground(0, 350, 5, "images/backgroud/bg1/bg1-7-tillset.png");

      StartScreenHandler startScreenHandler = new StartScreenHandler(this);
      startScreenThread = new Thread(startScreenHandler);
      startScreenThread.start(); // Start the thread for the start screen

      // Initial random obstacle selection
      chooseRandomObstacle();

      Timer gameLoop = new Timer(30, e -> {
          updateGame();
          repaint();
      });
      gameLoop.start(); // Start the game loop
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);

    if (gameOver) {
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(Color.BLACK);
      bgOver.draw(g, 850, 450);
      g2.setFont(customFont);
      if (timeRemaining <= 0) {
          g2.drawString("YOU GOT OUT!", 350, 200);
          g2.drawString("", 350, 230);
      } else {
          g2.drawString("Game Over!", 350, 200);
          g2.drawString("YOU GOT CAPTURED BACK!", 260, 230);
      }
      return; // Stop further painting
    }

    if (!gameStarted) {
      // Draw start screen background
      bgStart.draw(g, 850, 450);
      titleImage.draw(g, 600, 300);
      chickIdle.draw(g, 45, 45);
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(Color.WHITE);
      g2.setFont(customFont);
      g2.drawString("Click anywhere or press SPACE to start!", 120, 230);
    }
    // Draw the background layers
    else {
      if (timeRemaining > TIME_LIMIT / 2) {
        backgroundLayer11.draw(g, 850, 450);
        backgroundLayer12.draw(g, 850, 368);
        backgroundLayer13.draw(g, 850, 139);
        tileLayer1.draw(g, 850, 72);
      } else {
        backgroundLayer21.draw(g, 850, 450);
        backgroundLayer22.draw(g, 850, 259);
        backgroundLayer23.draw(g, 850, 120);
        backgroundLayer24.draw(g, 850, 100);
        backgroundLayer25.draw(g, 850, 69);
        backgroundLayer26.draw(g, 850, 375);
        backgroundLayer27.draw(g, 850, 120);
        tileLayer2.draw(g, 850, 72);
      }
      // Draw the chicken and the obstacle (dog or dino)
      Graphics2D g2 = (Graphics2D) g;
      chick.draw(g);
      for (Obstacle obstacle : obstacles) {
          obstacle.draw(g); // Draw each obstacle in the list
      }

      g2.setColor(Color.BLACK);
      g2.setFont(customFont);
      g2.drawString("time: " + timeRemaining + "s", 700, 30);
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub
  }

  @Override
  public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE) {
          if (!gameStarted) {
              startGame();  // Start the game when the player presses a key to start
          } else {
              chick.jump(this);
              this.repaint();
          }
      }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // TODO Auto-generated method stub
  }

  public void startGame() {
    gameStarted = true;
    timeRemaining = 20; // Reset time on game start
    obstacles.clear();  // Clear obstacles from the previous game
    chooseRandomObstacle();  // Initial obstacle generation
    startCountdownTimer();  // Start countdown timer
  }

  public void updateGame() {
    backgroundLayer11.update();
    backgroundLayer12.update();
    backgroundLayer13.update();
    tileLayer1.update();

    backgroundLayer21.update();
    backgroundLayer22.update();
    backgroundLayer23.update();
    backgroundLayer24.update();
    backgroundLayer25.update();
    backgroundLayer26.update();
    backgroundLayer27.update();
    tileLayer2.update();

    ArrayList<Obstacle> toRemove = new ArrayList<>();
    for (Obstacle obstacle : obstacles) {
        obstacle.x -= obstacle.speed; // Move the obstacle to the left
        if (obstacle.x < 0) {
            toRemove.add(obstacle); // Remove obstacles that are off-screen
        }
    }
    obstacles.removeAll(toRemove);

    checkCollisions();
    if (random.nextInt(100) < 1) { 
        chooseRandomObstacle();
    }
  }

  private void chooseRandomObstacle() {
    int minDistance = 345;

    // Randomly choose the type of obstacle: 0 for Dog, 1 for Dino
    int obstacleChoice = random.nextInt(2);

    int startY = 315;
    // Random speed for obstacles
    int speed = 3 + random.nextInt(2);
    int startX = 900 + random.nextInt(300);
    boolean isTooClose = true;  // Start by assuming the obstacle will be too close
    int maxAttempts = 10; // Prevent infinite loop (failsafe)
    int attempts = 0;

    while (isTooClose && attempts < maxAttempts) {
      isTooClose = false;
      for (Obstacle obstacle : obstacles) {
          if (Math.abs(obstacle.x - startX) < minDistance) {
              isTooClose = true;
              break;
          }
      }

      if (isTooClose) {
          startX = 850 + random.nextInt(300);
      }

      attempts++; // Increment attempt counter
    }
    //give up if cannot find
    if (attempts >= maxAttempts) {
        // System.out.println("Warning: Unable to find a suitable position for a new obstacle.");
        return; // Skip obstacle creation
    }

    // Create the new obstacle
    Obstacle newObstacle;
    if (obstacleChoice == 0) {
        newObstacle = new Dog(startX, startY, speed, this);
    } else {
        newObstacle = new Dino(startX, startY - 5, speed, this);
    }

    newObstacle.loadImage();  // Load the image for the new obstacle
    obstacles.add(newObstacle);  // Add the new obstacle to the list
  }

  private void checkCollisions() {
    for (Obstacle obs : obstacles) {
      if (checkEvent.checkHit(chick, obs)) {
        // Increment hit count
        hitCount++;

        if (hitCount >= 1) {
          gameOver = true;
          countdownTimer.stop();
        }
        break; 
      }
    }
  }

  private void startCountdownTimer() {
      countdownTimer = new Timer(1000, e -> {
          if (timeRemaining > 0) {
              timeRemaining--;
          } else {
              gameOver = true;
              System.out.println("TIME'S UP");
              countdownTimer.stop();
          }
      });
      countdownTimer.start(); 
  }
}
