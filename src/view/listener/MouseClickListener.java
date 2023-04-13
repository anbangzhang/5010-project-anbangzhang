package view.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * MouseClickListener.
 * 
 * @author anbang
 * @date 2023-04-05 00:25
 */
public class MouseClickListener extends MouseAdapter {

  private Map<Integer, Consumer<String>> map;

  /**
   * Constructor.
   */
  public MouseClickListener() {
    this.map = new HashMap<>();
  }

  /**
   * Set map.
   *
   * @param map map
   */
  public void setMouseClickActionMap(Map<Integer, Consumer<String>> map) {
    this.map = map;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    map.get(e.getButton()).accept(e.getComponent().getName());
  }

}
