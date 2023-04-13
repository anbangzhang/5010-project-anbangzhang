package model.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import model.context.Context;
import model.context.ContextHolder;
import model.enums.PlayerType;
import model.model.Player;

/**
 * BasePlayer class.
 * 
 * @author anbang
 * @date 2023-02-10 22:42
 */
public class BasePlayer implements Player {

  /**
   * Player order.
   */
  private int order;
  /**
   * Player name.
   */
  private String name;
  /**
   * Space index.
   */
  private int spaceIndex;
  /**
   * Player type.
   */
  private PlayerType type;
  /**
   * Weapon limit.
   */
  private Integer weaponLimit;

  /**
   * Constructor.
   * 
   * @param order       player order
   * @param name        player name
   * @param spaceIndex  position index
   * @param type        player type
   * @param weaponLimit weapon limit
   */
  public BasePlayer(int order, String name, int spaceIndex, PlayerType type, Integer weaponLimit) {
    if (order < 0) {
      throw new IllegalArgumentException("Order can't be smaller than 0.");
    }
    this.order = order;
    this.name = name;
    this.spaceIndex = spaceIndex;
    this.type = type;
    this.weaponLimit = weaponLimit;
  }

  /**
   * Get player order.
   *
   * @return order
   */
  @Override
  public int getOrder() {
    return this.order;
  }

  /**
   * Get name.
   *
   * @return name
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Set position index of player.
   *
   * @param index space index
   */
  @Override
  public void setSpaceIndex(int index) {
    this.spaceIndex = index;
  }

  /**
   * Get position index of player.
   *
   * @return space index
   */
  @Override
  public int getSpaceIndex() {
    return this.spaceIndex;
  }

  /**
   * Get weapon limit.
   *
   * @return weapon limit
   */
  @Override
  public Integer getWeaponLimit() {
    return this.weaponLimit;
  }

  /**
   * Get player type.
   *
   * @return player type
   */
  @Override
  public PlayerType getType() {
    return this.type;
  }

  @Override
  public Boolean addWeapon(BaseWeapon weapon) {
    List<BaseWeapon> weapons = getWeapons();
    if (Objects.nonNull(this.weaponLimit)) {
      return weapons.size() != this.weaponLimit;
    }
    return true;
  }

  @Override
  public List<BaseWeapon> getWeapons() {
    Context ctx = ContextHolder.get();
    if (Objects.isNull(ctx) || ctx.getWeapons().isEmpty()) {
      return new ArrayList<>();
    }
    return ctx.getWeapons().stream()
        .filter(
            weapon -> Objects.equals(weapon.getHolder(), String.format("player: %s", this.name)))
        .sorted((a, b) -> b.getDamage() - a.getDamage()).collect(Collectors.toList());
  }

}
