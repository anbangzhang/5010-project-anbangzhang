package flowengine.request;

import world.base.BaseWeapon;
import world.model.Player;

/**
 * AttackRequest.
 * 
 * @author anbang
 * @date 2023-03-18 08:46
 */
public class AttackRequest extends BaseRequest {

  /**
   * weapon.
   */
  private BaseWeapon weapon;

  /**
   * Constructor.
   * 
   * @param player player
   */
  public AttackRequest(Player player) {
    super(player);
  }

  /**
   * Get weapon.
   * 
   * @return weapon
   */
  public BaseWeapon getWeapon() {
    return weapon;
  }

  /**
   * Set weapon.
   * 
   * @param weapon weapon
   */
  public void setWeapon(BaseWeapon weapon) {
    this.weapon = weapon;
  }
}
