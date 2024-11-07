package components.Event;

import components.character.*;

public class checkEvent {
  
  public static boolean checkHit(Chicken c, Obstacle obs) {
    boolean horiHit = (c.x + c.getWidth() > obs.x) && (c.x < obs.x + obs.getWidth());
    boolean vertHit = (c.y + c.getHeight() > obs.y) && (c.y < obs.y + obs.getHeight());
    return horiHit && vertHit;
  }
}
