package world;

import world.base.BaseSpace;
import world.base.BaseWeapon;

/**
 * Weapon class.
 * 
 * @author anbang
 * @date 2023-01-28 22:40
 */
public class Weapon extends BaseWeapon {

  /**
   * The space it belong to.
   */
  private BaseSpace belongTo;

  /**
   * Constructor of weapon.
   * 
   * @param base     base weapon
   * @param belongTo space
   */
  public Weapon(BaseWeapon base, BaseSpace belongTo) {
    super(base);
    this.belongTo = belongTo;
  }

  /**
   * Getter of belong to.
   * 
   * @return belong to
   */
  public BaseSpace getBelongTo() {
    return this.belongTo;
  }

  /**
   * Setter of belong to.
   * 
   * @param belongTo belong to
   */
  public void setBelongTo(BaseSpace belongTo) {
    this.belongTo = belongTo;
  }
}
