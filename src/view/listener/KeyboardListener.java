package view.listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * KeyboardListener.
 * 
 * @author anbang
 * @date 2023-04-05 00:21
 */
public class KeyboardListener extends KeyAdapter {

  private Map<Integer, Runnable> map;

  /**
   * Constructor.
   */
  public KeyboardListener() {
    this.map = new HashMap<>();
  }

  /**
   * Set map.
   *
   * @param map map
   */
  public void setMap(Map<Integer, Runnable> map) {
    this.map = map;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (map.containsKey(e.getKeyCode())) {
      map.get(e.getKeyCode()).run();
    }
  }
}
