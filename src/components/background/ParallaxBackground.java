package components.background;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class ParallaxBackground {
    private int x, y;
    private int speed;
    private Image backgroundImage;

    public ParallaxBackground(int x, int y, int speed, String imagePath) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.backgroundImage = new ImageIcon(imagePath).getImage();
    }

    // Draw the background image
    public void draw(Graphics g, int panelWidth, int panelHeight) {
        // Draw the background image with scrolling effect
        g.drawImage(backgroundImage, x, y, panelWidth, panelHeight, null);
        g.drawImage(backgroundImage, x + backgroundImage.getWidth(null), y, panelWidth, panelHeight, null);
    }

    // Update the background position based on its speed
    public void update() {
        x -= speed;
        if (x <= -backgroundImage.getWidth(null)) {
            x = 0;
        }
    }

    // Getter for background image width
    public int getWidth() {
        return backgroundImage.getWidth(null);
    }
}
