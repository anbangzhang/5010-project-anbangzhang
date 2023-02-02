package world.base;

/**
 * Base class of weapon.
 * 
 * @author anbang
 * @date 2023-01-28 22:36
 */
public class BaseWeapon {

  /**
   * The index of space that the weapon is in.
   */
  private int space;

  /**
   * Damage of weapon.
   */
  private int damage;

  /**
   * Name of weapon.
   */
  private String name;

  /**
   * Constructor of base weapon.
   * 
   * @param base base weapon
   */
  public BaseWeapon(BaseWeapon base) {
    this.space = base.space;
    this.damage = base.damage;
    this.name = base.name;
  }

  /**
   * Constructor of base weapon.
   * 
   * @param space  space
   * @param damage damage
   * @param name   name
   * @throws IllegalArgumentException if param is invalid
   */
  public BaseWeapon(int space, int damage, String name) throws IllegalArgumentException {
    if (space < 0 || damage < 0) {
      throw new IllegalArgumentException();
    }
    this.space = space;
    this.damage = damage;
    this.name = name;
  }

  /**
   * Get the space index.
   * 
   * @return space index
   */
  public int getSpaceIndex() {
    return this.space;
  }

  /**
   * Getter of damage.
   *
   * @return damage
   */
  public int getDamage() {
    return this.damage;
  }

  /**
   * Getter of weapon name.
   *
   * @return name
   */
  public String getName() {
    return this.name;
  }
}
