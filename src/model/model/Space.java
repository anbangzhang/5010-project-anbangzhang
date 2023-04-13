package model.model;

import java.util.List;
import model.base.BaseWeapon;

/**
 * Space.
 * 
 * @author anbang
 * @date 2023-03-15 23:20
 */
public interface Space {

  /**
   * Get name.
   * 
   * @return name
   */
  String getName();

  /**
   * Getter of order.
   *
   * @return order
   */
  int getOrder();

  /**
   * Get neighbors.
   * 
   * @return neighbors
   */
  List<Space> getNeighbors();

  /**
   * Get occupiers.
   * 
   * @return occupiers
   */
  List<Player> getOccupiers();

  /**
   * Get weapons.
   * 
   * @return weapons
   */
  List<BaseWeapon> getWeapons();

  /**
   * Is exposed.
   * 
   * @return is visible
   */
  Boolean isExposed();

  /**
   * Show the detail of current space.
   * 
   * @return detail
   */
  String showDetail();

}
