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
   * Holder, it can be space name or player name.
   */
  private String holder;

  /**
   * Constructor of base weapon.
   * 
   * @param space  space
   * @param damage damage
   * @param name   name
   * @throws IllegalArgumentException if param is invalid
   */
  public BaseWeapon(int space, int damage, String name) throws IllegalArgumentException {
    if (space < 0 || damage <= 0) {
      throw new IllegalArgumentException("Space index or damage is smaller than 0.");
    }
    this.space = space;
    this.damage = damage;
    this.name = name;
  }

  /**
   * Get the space index.
   *
   * @return space index.
   */
  public int getSpaceIndex() {
    return this.space;
  }

  /**
   * Set the holder.
   * 
   * @param holder holder
   */
  public void setHolder(String holder) {
    this.holder = holder;
  }

  /**
   * Get the holder.
   * 
   * @return holder
   */
  public String getHolder() {
    return this.holder;
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
