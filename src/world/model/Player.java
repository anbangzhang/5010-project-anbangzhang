package world.model;

import java.util.List;
import world.base.BaseWeapon;
import world.context.Context;
import world.enums.PlayerType;

/**
 * Player interface.
 * 
 * @author anbang
 * @date 2023-02-10 22:35
 */
public interface Player {

  /**
   * Get player order.
   * 
   * @return order
   */
  int getOrder();

  /**
   * Get name.
   * 
   * @return name
   */
  String getName();

  /**
   * Set position index of player.
   * 
   * @param index space index
   */
  void setSpaceIndex(int index);

  /**
   * Get position index of player.
   * 
   * @return space index
   */
  int getSpaceIndex();

  /**
   * Get weapon limit.
   * 
   * @return weapon limit
   */
  Integer getWeaponLimit();

  /**
   * Get player type.
   * 
   * @return player type
   */
  PlayerType getType();

  /**
   * Add weapon.
   * 
   * @param weapon weapon
   * @return success or fail
   */
  Boolean addWeapon(BaseWeapon weapon);

  /**
   * Get weapons.
   * 
   * @return weapons
   */
  List<BaseWeapon> getWeapons();
}
