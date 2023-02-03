package world.model;

/**
 * Target class.
 * 
 * @author anbang
 * @date 2023-01-28 22:58
 */
public class Target {

  /**
   * Target health.
   */
  private int health;

  /**
   * Target Name.
   */
  private String name;

  /**
   * Position of target.
   */
  private int position;

  /**
   * Constructor of target.
   * 
   * @param health health
   * @param name   name
   * @throws IllegalArgumentException if health is invalid
   */
  public Target(int health, String name) throws IllegalArgumentException {
    if (health <= 0) {
      throw new IllegalArgumentException();
    }
    this.health = health;
    this.name = name;
    this.position = 0;
  }

  /**
   * Get the health of target.
   * 
   * @return health of target
   */
  public int getHealth() {
    return this.health;
  }

  /**
   * Decrease the target health by damage.
   * 
   * @param damage damage
   * @return health of target after decrease
   */
  public int decreaseHealth(int damage) {
    this.health = (this.health < damage) ? 0 : this.health - damage;
    return this.health;
  }

  /**
   * Get the target's name.
   * 
   * @return name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Get the position of target.
   * 
   * @return position
   */
  public int getPosition() {
    return this.position;
  }

  /**
   * Set the position of target.
   * 
   * @param pos position
   */
  public void setPosition(int pos) {
    this.position = pos;
  }
}
