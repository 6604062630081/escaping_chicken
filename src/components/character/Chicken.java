package components.character;

import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Chicken {
    public int x, y;
    private Image standingImage; 
    private Image jumpingImage;  
    private Image currentImage;  //tracking
    
    private boolean isJumping = false;
    private int jumpHeight = 60;        // How high the chicken jumps

    public Chicken(int x, int y) {
        this.x = x;
        this.y = y;
        loadChickenImages();  // Load imgs
        currentImage = standingImage;  // Start with the standing image
    }

    // Load img
    private void loadChickenImages() {
      standingImage = new ImageIcon("images/ChickenPack/gif/ChikcenWalking.gif").getImage();
      jumpingImage = new ImageIcon("images/ChickenPack/gif/ChickenJumping.gif").getImage();
    }

    // draw chick with current img
    public void draw(Graphics g) {
        g.drawImage(currentImage, x, y, 50, 50, null);
    }

    public int getWidth() {
      return currentImage.getWidth(null);  // Return the width of the current image
    }

    // Method to get the height of the current image
    public int getHeight() {
        return currentImage.getHeight(null);  // Return the height of the current image
    }

    // Method to initiate the jump (this can be triggered by pressing a key)
    public void jump(JPanel frame) {
        if (!isJumping) {  // Only jump if not already jumping
            isJumping = true;
            currentImage = jumpingImage;  // Switch to the jumping GIF
            // Start a jump animation with a simple y position change
            new Thread(() -> {
                // Move up during the jump (simulating the jump)
                for (int i = 0; i < jumpHeight; i++) {
                    if (!isJumping) return;
                    y -= 1;  // Move the chicken up
                    frame.repaint();  // Repaint the screen
                    try {
                        Thread.sleep(10);  // Adjust jump speed
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // After reaching the peak, come back down
                for (int i = 0; i < jumpHeight; i++) {
                    if (!isJumping) return;
                    y += 1;  // Move the chicken down
                    frame.repaint();  // Repaint the screen
                    try {
                        Thread.sleep(10);  // Adjust jump speed
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // Once back on the ground, reset the image and state
                isJumping = false;
                currentImage = standingImage;  // Switch back to standing image
                frame.repaint();  // Repaint the screen
            }).start();
        }
    }

}



