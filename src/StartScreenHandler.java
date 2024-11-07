
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartScreenHandler implements Runnable {

    private MyFrame gameFrame;
    private boolean gameStarted;

    // Constructor that takes the game frame
    public StartScreenHandler(MyFrame gameFrame) {
        this.gameFrame = gameFrame;
        this.gameStarted = false;
    }

    @Override
    public void run() {
        gameFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!gameStarted) {
                    gameStarted = true;  
                    gameFrame.startGame();
                }
            }
        });
        while (!gameStarted) {
            try {
                Thread.sleep(50); // Sleep briefly to prevent maxing out the CPU while waiting
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
