package view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * ButtonListener.
 * 
 * @author anbang
 * @date 2023-04-05 00:10
 */
public class ButtonListener implements ActionListener {

  private Map<String, Runnable> map;

  /**
   * Constructor.
   */
  public ButtonListener() {
    this.map = new HashMap<>();
  }

  /**
   * Set map.
   * 
   * @param map map
   */
  public void setMap(Map<String, Runnable> map) {
    this.map = map;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (map.containsKey(e.getActionCommand())) {
      map.get(e.getActionCommand()).run();
    }
  }

}
