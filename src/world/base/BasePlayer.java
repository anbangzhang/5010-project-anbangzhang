package world.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import world.enums.PlayerType;
import world.model.Player;
import world.model.Weapon;

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
   * Weapons.
   */
  private List<Weapon> weapons;

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
    this.weapons = new ArrayList<>();
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

  /**
   * Add weapon.
   *
   * @param weapon weapon
   * @return success or fail
   */
  @Override
  public Boolean addWeapon(Weapon weapon) {
    if (Objects.nonNull(this.weaponLimit)) {
      if (this.weapons.size() == this.weaponLimit) {
        return false;
      }
    }
    return this.weapons.add(weapon);
  }

  /**
   * Get weapons.
   *
   * @return weapons
   */
  @Override
  public List<Weapon> getWeapons() {
    return this.weapons;
  }
}
