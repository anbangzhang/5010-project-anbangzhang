package world.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import world.World;
import world.base.BaseSpace;
import world.base.BaseWeapon;
import world.container.Context;
import world.exception.BusinessException;
import world.model.Player;
import world.model.Space;

/**
 * WorldImpl class. Implementation of world interface.
 * 
 * @author anbang
 * @date 2023-01-28 22:51
 */
public class WorldImpl implements World {

  /**
   * Scale factor.
   */
  private static final int SCALE_FACTOR = 20;

  /**
   * World name.
   */
  private String name;

  /**
   * Constructor of World.
   *
   * @param name name
   */
  public WorldImpl(String name) {
    this.name = name;
  }

  @Override
  public List<String> getAllSpaces(Context context) {
    return context.getSpaces().stream().map(BaseSpace::getName).collect(Collectors.toList());
  }

  @Override
  public List<String> getNeighbors(Context context, String name) {
    return getNeighborNamesFromSpace(getSpace(context, name));
  }

  @Override
  public List<String> getNeighbors(Context context, Integer index) {
    return getNeighborNamesFromSpace(getSpace(context, index));
  }

  /**
   * Get neighbor's name list from space.
   * 
   * @param space space
   * @return neighbor's name list
   */
  private List<String> getNeighborNamesFromSpace(Space space) {
    List<String> result = new ArrayList<>();
    Optional.ofNullable(space).ifPresent(space1 -> result
        .addAll(space.getNeighbors().stream().map(Space::getName).collect(Collectors.toList())));
    return result;
  }

  @Override
  public Space getSpace(Context context, String name) {
    return context.getSpaces().stream().filter(item -> Objects.equals(item.getName(), name))
        .findFirst().orElse(null);
  }

  @Override
  public Space getSpace(Context context, Integer index) {
    List<BaseSpace> spaces = context.getSpaces();
    if (index < 0 || index >= spaces.size()) {
      return null;
    }
    return spaces.get(index);
  }

  @Override
  public Space getTargetPosition(Context context) {
    int pos = context.getTarget().getPosition();
    return context.getSpaces().get(pos);
  }

  @Override
  public Space moveTarget(Context context) {
    int pos = context.getTarget().getPosition();
    List<BaseSpace> spaces = context.getSpaces();
    int newPos = (pos + 1) % spaces.size();
    context.getTarget().setPosition(newPos);
    return spaces.get(newPos);
  }

  @Override
  public List<Player> getAllPlayers(Context context) {
    return context.getPlayers();
  }

  @Override
  public Player getPlayer(Context context, int index) {
    List<Player> players = context.getPlayers();
    if (index < 0 || index >= players.size()) {
      return null;
    }
    return players.get(index);
  }

  @Override
  public Boolean addPlayer(Context context, Player player) {
    boolean repeat = context.getPlayers().stream()
        .anyMatch(item -> item.getName().equals(player.getName()));
    if (repeat) {
      return false;
    }
    Space space = getSpace(context, player.getSpaceIndex());
    if (Objects.isNull(space)) {
      return false;
    }
    return context.getPlayers().add(player);
  }

  @Override
  public void movePlayer(Player player, Space space) throws BusinessException {
    if (Objects.isNull(space)) {
      throw new BusinessException("Invalid space.");
    }
    player.setSpaceIndex(space.getOrder());
  }

  @Override
  public void pickUp(Player player, BaseWeapon weapon) throws BusinessException {
    if (!player.addWeapon(weapon)) {
      throw new BusinessException("Player's weapons reach limit.");
    }
    weapon.setHolder(String.format("player: %s", player.getName()));
  }

  @Override
  public void showGraphicalImage(Context context, String directory) throws IOException {
    int m = context.getM();
    int n = context.getN();
    BufferedImage image = new BufferedImage(n * SCALE_FACTOR, m * SCALE_FACTOR,
        BufferedImage.TYPE_BYTE_GRAY);
    Graphics2D graphics = image.createGraphics();
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, n * SCALE_FACTOR, m * SCALE_FACTOR);
    graphics.setColor(Color.BLACK);

    Font font = new Font("Microsoft YaHei", Font.PLAIN, 10);
    for (BaseSpace space : context.getSpaces()) {
      int[] start = space.getStart();
      int[] end = space.getEnd();
      graphics.drawRect(start[1] * SCALE_FACTOR, start[0] * SCALE_FACTOR,
          (end[1] - start[1] + 1) * SCALE_FACTOR, (end[0] - start[0] + 1) * SCALE_FACTOR);
      graphics.setFont(font);
      int x = start[1] * SCALE_FACTOR + 3;
      int y = start[0] * SCALE_FACTOR + 10;
      int rowWidth = (end[1] - start[1]) * SCALE_FACTOR + 8;
      List<String> strings = composeString(space);
      for (String string : strings) {
        y = drawString(graphics, string, x, y, rowWidth);
      }
    }
    String fileName = (directory.endsWith("/")) ? String.format("%s%s.png", directory, this.name)
        : String.format("%s/%s.png", directory, this.name);
    File outputFile = new File(fileName);
    graphics.dispose();
    ImageIO.write(image, "png", outputFile);
  }

  /**
   * Compose string.
   * 
   * @param space space
   * @return string
   */
  private List<String> composeString(Space space) {
    if (space.getOccupiers().isEmpty()) {
      return Arrays.asList(space.getName());
    }
    return Arrays.asList(space.getName(), String.format("players: %s",
        space.getOccupiers().stream().map(Player::getName).collect(Collectors.toList())));
  }

  /**
   * Draw string and change a new line automatically.
   * 
   * @param g        graphics
   * @param text     text
   * @param x        x
   * @param y        y
   * @param rowWidth row width
   */
  private int drawString(Graphics2D g, String text, int x, int y, int rowWidth) {
    int stringWidth = g.getFontMetrics().stringWidth(text);
    if (stringWidth <= rowWidth) {
      g.drawString(text, x, y);
      return y + g.getFontMetrics().getHeight();
    }
    for (int i = 1;; i++) {
      String str = text.substring(0, i);
      int localWidth = g.getFontMetrics().stringWidth(str);
      if (localWidth >= rowWidth) {
        g.drawString(str, x, y);
        text = text.substring(i);
        break;
      }
    }
    int stringHeight = g.getFontMetrics().getHeight();
    return drawString(g, text, x, y + stringHeight, rowWidth);
  }
}
