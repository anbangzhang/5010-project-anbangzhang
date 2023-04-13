package view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import model.World;
import model.context.Context;
import model.model.Player;

/**
 * SidePanel.
 * 
 * @author anbang
 * @date 2023-04-06 23:21
 */
public class SidePanel extends JPanel {

  private JPanel playersPanel;

  private JPanel targetPanel;

  private JPanel logPanel;

  private JPanel turnPanel;

  private JScrollPane scrollPlayers;

  private JScrollPane scrollTarget;

  private JScrollPane scrollLog;

  private JScrollPane scrollTurn;

  private GridBagConstraints constraint;

  /**
   * Constructor.
   */
  public SidePanel() {
    this.constraint = new GridBagConstraints();
    this.constraint.gridwidth = GridBagConstraints.REMAINDER;
    this.setLayout(new BorderLayout());

    this.turnPanel = new JPanel();
    this.scrollTurn = new JScrollPane(this.turnPanel);

    this.playersPanel = new JPanel();
    this.playersPanel.setLayout(new GridBagLayout());
    this.scrollPlayers = new JScrollPane(this.playersPanel);
    this.scrollPlayers.setPreferredSize(new Dimension(100, 450));

    this.targetPanel = new JPanel();
    this.targetPanel.setLayout(new GridBagLayout());
    this.scrollTarget = new JScrollPane(this.targetPanel);
    this.scrollTarget.setPreferredSize(new Dimension(100, 100));

    this.logPanel = new JPanel();
    this.logPanel.setLayout(new GridBagLayout());
    this.scrollLog = new JScrollPane(this.logPanel);
    this.scrollLog.setPreferredSize(new Dimension(100, 60));

    JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollTurn, scrollPlayers);
    sp.setEnabled(false);
    sp.setDividerSize(0);

    JSplitPane sp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sp, scrollTarget);
    sp2.setEnabled(false);
    sp2.setDividerSize(0);

    JSplitPane sp3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sp2, scrollLog);
    sp2.setEnabled(false);
    sp2.setDividerSize(0);

    this.add(sp3);
    this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),
        BorderFactory.createLoweredBevelBorder()));
  }

  /**
   * Display turns.
   * 
   * @param turns turns
   */
  public void displayTurns(int turns) {
    turnPanel.removeAll();
    JLabel turn = new JLabel("Turns: " + turns);
    turn.setFont(new Font(turn.getFont().getFontName(), Font.BOLD, 18));
    turn.setForeground(Color.RED);
    turnPanel.add(turn);
    turnPanel.revalidate();
    turnPanel.repaint();
  }

  /**
   * Display players.
   * 
   * @param context       context
   * @param currentPlayer currentPlayer
   * @param playersColor  playerColor
   */
  public void displayPlayers(Context context, Player currentPlayer,
      Map<String, Color> playersColor) {
    this.playersPanel.removeAll();

    JLabel head = new JLabel("Players");
    head.setFont(new Font(head.getFont().getFontName(), Font.BOLD, head.getFont().getSize()));
    this.playersPanel.add(head, constraint);
    this.playersPanel.add(Box.createVerticalStrut(50));
    URL url = this.getClass().getResource("/images/player.png");
    for (Player player : context.getPlayers()) {
      JLabel p = new JLabel(player.getName());
      Icon img = new Icon(url);

      ImageIcon playerIcon = img.setIconColor(playersColor.get(player.getName()));
      p.setIcon(playerIcon);
      p.setHorizontalAlignment(JLabel.LEFT);
      p.setVerticalAlignment(JLabel.TOP);
      p.setPreferredSize(new Dimension(200, 35));
      this.playersPanel.add(p, constraint);
      this.playersPanel.add(Box.createVerticalStrut(40));
      if (Objects.equals(player, currentPlayer)) {
        p.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
      }
    }
    this.playersPanel.revalidate();
    this.playersPanel.repaint();
  }

  /**
   * Display target details.
   * 
   * @param context context
   */
  public void displayTargetDetails(Context context) {
    this.targetPanel.removeAll();
    JLabel head = new JLabel("Target Details");
    head.setFont(new Font(head.getFont().getFontName(), Font.BOLD, head.getFont().getSize()));
    ImageIcon img = new ImageIcon(this.getClass().getResource("/images/target.png"));
    StringBuilder sb = new StringBuilder();
    sb.append("<html>").append("Target Name = ").append(context.getTarget().getName())
        .append("<br/>");
    sb.append("Health = ").append(context.getTarget().getHealth()).append("<br/>");
    sb.append("Target Location = ")
        .append(World.getSpace(context, context.getTarget().getPosition()).getName())
        .append("<br/>").append("</html>");
    JLabel targetLabel = new JLabel(sb.toString());
    targetLabel.setIcon(img);
    this.targetPanel.add(head, constraint);
    this.targetPanel.add(targetLabel, constraint);
    this.targetPanel.revalidate();
    this.targetPanel.repaint();
  }

  /**
   * Display logs.
   * 
   * @param message message
   */
  public void displayLogs(String message) {
    logPanel.removeAll();
    JLabel logLabel = new JLabel(message);
    logPanel.add(logLabel);
    this.logPanel.revalidate();
    this.logPanel.repaint();
  }

}
