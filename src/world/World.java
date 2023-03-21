package world;

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
import world.base.BaseSpace;
import world.base.BaseWeapon;
import world.context.Context;
import world.exception.BusinessException;
import world.model.Player;
import world.model.Space;

/**
 * World class.
 * 
 * @author anbang
 * @date 2023-01-28 22:51
 */
public class World {

  /**
   * Scale factor.
   */
  private static final int SCALE_FACTOR = 20;

  /**
   * Get the whole space name list.
   *
   * @param context context
   * @return space name list
   */
  public static List<String> getAllSpaces(Context context) {
    return context.getSpaces().stream().map(BaseSpace::getName).collect(Collectors.toList());
  }

  /**
   * Get the neighbors of the given space name. Spaces that share a "wall" are
   * neighbors.
   *
   * @param context context
   * @param name    the space name
   * @return the list of neighbors
   */
  public static List<String> getNeighbors(Context context, String name) {
    return getNeighborNamesFromSpace(getSpace(context, name));
  }

  /**
   * Get the neighbors of the given space index. Spaces that share a "wall" are
   * neighbors.
   *
   * @param context context
   * @param index   the space index
   * @return the list of neighbors
   */
  public static List<String> getNeighbors(Context context, Integer index) {
    return getNeighborNamesFromSpace(getSpace(context, index));
  }

  /**
   * Get neighbor's name list from space.
   * 
   * @param space space
   * @return neighbor's name list
   */
  private static List<String> getNeighborNamesFromSpace(Space space) {
    List<String> result = new ArrayList<>();
    Optional.ofNullable(space).ifPresent(space1 -> result
        .addAll(space.getNeighbors().stream().map(Space::getName).collect(Collectors.toList())));
    return result;
  }

  /**
   * Get the space with neighbors and weapons.
   *
   * @param context context
   * @param name    space name
   * @return space
   */
  public static Space getSpace(Context context, String name) {
    return context.getSpaces().stream().filter(item -> Objects.equals(item.getName(), name))
        .findFirst().orElse(null);
  }

  /**
   * Get the space with neighbors and weapons.
   *
   * @param context context
   * @param index   space index
   * @return space
   */
  public static Space getSpace(Context context, Integer index) {
    List<BaseSpace> spaces = context.getSpaces();
    if (index < 0 || index >= spaces.size()) {
      return null;
    }
    return spaces.get(index);
  }

  /**
   * Get target current position.
   *
   * @param context context
   * @return space of current position
   */
  public static Space getTargetPosition(Context context) {
    int pos = context.getTarget().getPosition();
    return context.getSpaces().get(pos);
  }

  /**
   * Move the target character.
   *
   * @param context context
   * @return the space that target is in
   */
  public static Space moveTarget(Context context) {
    int pos = context.getTarget().getPosition();
    List<BaseSpace> spaces = context.getSpaces();
    int newPos = (pos + 1) % spaces.size();
    context.getTarget().setPosition(newPos);
    return spaces.get(newPos);
  }

  /**
   * Get all the players.
   *
   * @param context context
   * @return players
   */
  public static List<Player> getAllPlayers(Context context) {
    return context.getPlayers();
  }

  /**
   * Get player according to index.
   *
   * @param context context
   * @param index   index
   * @return player
   */
  public static Player getPlayer(Context context, int index) {
    List<Player> players = context.getPlayers();
    if (index < 0 || index >= players.size()) {
      return null;
    }
    return players.get(index);
  }

  /**
   * Add a player.
   *
   * @param context context
   * @param player  player
   * @return if successful
   */
  public static Boolean addPlayer(Context context, Player player) {
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

  /**
   * Move a player to a space.
   *
   * @param player player
   * @param space  space
   * @throws BusinessException invalid space
   */
  public static void movePlayer(Player player, Space space) throws BusinessException {
    if (Objects.isNull(space)) {
      throw new BusinessException("Invalid space.");
    }
    player.setSpaceIndex(space.getOrder());
  }

  /**
   * Player pick up a weapon.
   *
   * @param player player
   * @param weapon weapon
   * @throws BusinessException player's weapons reach limit
   */
  public static void pickUp(Player player, BaseWeapon weapon) throws BusinessException {
    if (!player.addWeapon(weapon)) {
      throw new BusinessException("Player's weapons reach limit.");
    }
    weapon.setHolder(String.format("player: %s", player.getName()));
  }

  /**
   * Get image.
   * 
   * @param context context
   */
  public static BufferedImage getGraphicalImage(Context context) {
    return generateBufferedImage(context);
  }

  /**
   * Generate the world in image.
   *
   * @param context   context
   * @param directory directory of output
   * @throws IOException if write file fail
   */
  public static void generateGraphicalImage(Context context, String directory) throws IOException {
    BufferedImage image = generateBufferedImage(context);
    String fileName = (directory.endsWith("/"))
        ? String.format("%s%s.png", directory, context.getWorldName())
        : String.format("%s/%s.png", directory, context.getWorldName());
    File outputFile = new File(fileName);
    ImageIO.write(image, "png", outputFile);
  }

  private static BufferedImage generateBufferedImage(Context context) {
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
    graphics.dispose();

    return image;
  }

  /**
   * Compose string.
   * 
   * @param space space
   * @return string
   */
  private static List<String> composeString(Space space) {
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
  private static int drawString(Graphics2D g, String text, int x, int y, int rowWidth) {
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
