package model.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import model.context.Context;
import model.context.ContextHolder;
import model.model.Player;
import model.model.Space;

/**
 * Base class of space.
 * 
 * @author anbang
 * @date 2023-01-28 22:30
 */
public class BaseSpace implements Space {
  /**
   * Start of row.
   */
  private int startX;
  /**
   * Start of col.
   */
  private int startY;
  /**
   * End of row.
   */
  private int endX;
  /**
   * End of col.
   */
  private int endY;
  /**
   * Index order.
   */
  private int order;
  /**
   * Space name.
   */
  private String name;

  /**
   * Constructor of base space.
   * 
   * @param startX startX
   * @param startY startY
   * @param endX   endX
   * @param endY   endY
   * @param order  ORDER
   * @param name   name
   * @throws IllegalArgumentException if param is invalid
   */
  public BaseSpace(int startX, int startY, int endX, int endY, int order, String name)
      throws IllegalArgumentException {
    if (startX < 0 || startY < 0 || endX < 0 || endY < 0 || endX <= startX || endY <= startY) {
      throw new IllegalArgumentException("Invalid coordinates.");
    }
    if (order < 0) {
      throw new IllegalArgumentException("Invalid order.");
    }
    this.startX = startX;
    this.startY = startY;
    this.endX = endX;
    this.endY = endY;
    this.order = order;
    this.name = name;
  }

  /**
   * Getter of start position.
   * 
   * @return start position
   */
  public int[] getStart() {
    return new int[] { this.startX, this.startY };
  }

  /**
   * Getter of end position.
   *
   * @return end position
   */
  public int[] getEnd() {
    return new int[] { this.endX, this.endY };
  }

  /**
   * Getter of order.
   * 
   * @return order
   */
  public int getOrder() {
    return this.order;
  }

  /**
   * Getter of space name.
   * 
   * @return name
   */
  public String getName() {
    return this.name;
  }

  @Override
  public List<Space> getNeighbors() {
    Context context = ContextHolder.get();
    Map<BaseSpace, List<BaseSpace>> neighborMap = context.getNeighborMap();
    return neighborMap.getOrDefault(this, new ArrayList<>()).stream().map(space -> (Space) space)
        .collect(Collectors.toList());
  }

  @Override
  public List<Player> getOccupiers() {
    Context context = ContextHolder.get();
    if (Objects.isNull(context)) {
      return new ArrayList<>();
    }
    return context.getPlayers().stream()
        .filter(player -> Objects.equals(player.getSpaceIndex(), this.order))
        .collect(Collectors.toList());
  }

  @Override
  public List<BaseWeapon> getWeapons() {
    Context context = ContextHolder.get();
    if (Objects.isNull(context)) {
      return new ArrayList<>();
    }
    return context.getWeapons().stream()
        .filter(weapon -> Objects.equals(weapon.getHolder(), String.format("space: %s", this.name)))
        .collect(Collectors.toList());
  }

  @Override
  public Boolean isExposed() {
    Context context = ContextHolder.get();
    if (Objects.isNull(context)) {
      return false;
    }
    return context.getExposedSpaces().contains(this);
  }

  @Override
  public String showDetail() {
    Context context = ContextHolder.get();
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Space: [%s], occupiers: %s, weapons: %s", this.name,
        getOccupiers().stream().map(Player::getName).collect(Collectors.toList()),
        getWeapons().stream().map(BaseWeapon::getName).collect(Collectors.toList())));
    if (Objects.equals(context.getTarget().getPosition(), this.order)) {
      sb.append(String.format(", Target: [%s]", context.getTarget().getName()));
    }
    if (Objects.equals(context.getPet().getSpaceIndex(), this.order)) {
      sb.append(String.format(", Pet: [%s]", context.getPet().getName()));
    }
    return sb.toString();
  }

  @Override
  public String toString() {
    return String.format("Space: [%s], neighbors: %s, weapons: %s, occupies: %s", this.name,
        getNeighbors().stream().map(Space::getName).collect(Collectors.toList()),
        getWeapons().stream().map(BaseWeapon::getName).collect(Collectors.toList()),
        getOccupiers().stream().map(Player::getName).collect(Collectors.toList()));
  }

}
