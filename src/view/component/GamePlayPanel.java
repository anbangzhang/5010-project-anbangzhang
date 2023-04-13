package view.component;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import view.listener.MouseClickListener;
import world.World;
import world.base.BaseSpace;
import world.constant.Constants;
import world.context.Context;
import world.model.Player;

/**
 * GamePlayPanel.
 * 
 * @author anbang
 * @date 2023-04-07 02:33
 */
public class GamePlayPanel extends JScrollPane {

  private Map<String, JPanel> roomPanels;

  private final int scale = 25;

  private final int padding = 20;

  private final int offset = 100;

  /**
   * Constructor.
   */
  public GamePlayPanel() {
    roomPanels = new HashMap<>();
    this.setLayout(null);
    this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
        BorderFactory.createLoweredBevelBorder()));
  }

  /**
   * Display rooms.
   * 
   * @param context  context
   * @param listener listener
   */
  public void displayRooms(Context context, MouseClickListener listener) {
    this.removeAll();

    for (BaseSpace space : context.getSpaces()) {
      JPanel roomPanel = new JPanel();
      int[] start = space.getStart();
      int[] end = space.getEnd();

      int height = (end[0] - start[0] + 1) * scale;
      int width = (end[1] - start[1] + 1) * scale;
      roomPanel.setName(space.getName());
      roomPanel.addMouseListener(listener);
      roomPanel.setSize(new Dimension(width, height));
      roomPanel.setLocation(start[1] * scale + padding, start[0] * scale + padding + offset);
      roomPanel.setBorder(BorderFactory.createCompoundBorder(
          BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
      roomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

      // if not visible(pet in room)
      if (Objects.equals(context.getTarget().getPosition(), space.getOrder())) {
        roomPanel.setBackground(Color.GRAY);
      }
      JLabel roomInfo = new JLabel(space.getName());
      roomPanel.add(roomInfo);
      roomPanels.put(space.getName(), roomPanel);
      this.add(roomPanel);
    }
    this.revalidate();
    this.repaint();
  }

  /**
   * Display players.
   * 
   * @param context      context
   * @param playersColor playerColor
   * @param listener     listener
   */
  public void displayPlayers(Context context, Map<String, Color> playersColor,
      MouseClickListener listener) {

    for (Player player : context.getPlayers()) {
      JLabel playerLabel = new JLabel();
      Color playerColor = playersColor.get(player.getName());
      Icon img = new Icon(new File("res/images/player.png"));
      ImageIcon playerIcon = img.setIconColor(playerColor);
      playerLabel.setIcon(playerIcon);
      playerLabel.setName(player.getName());
      playerLabel.addMouseListener(listener);

      roomPanels.get(World.getSpace(context, player.getSpaceIndex()).getName()).add(playerLabel);
    }
  }

  /**
   * Display target.
   * 
   * @param context  context
   * @param listener listener
   */
  public void displayTarget(Context context, MouseClickListener listener) {
    JLabel targetLabel = new JLabel();
    ImageIcon targetIcon = new ImageIcon("res/images/target.png");
    targetLabel.setIcon(targetIcon);
    targetLabel.addMouseListener(listener);
    roomPanels.get(World.getSpace(context, context.getTarget().getPosition()).getName())
        .add(targetLabel);
  }

}
