package world;

import java.util.List;

import world.base.BaseSpace;

/**
 * @author anbang
 * @date 2023-01-28 22:41
 */
public class Space extends BaseSpace {

  /**
   * Neighbors of space.
   */
  private List<BaseSpace> neighbors;

  /**
   * Weapons in the space.
   */
  private List<Weapon> weapons;

  /**
   * Constructor of space.
   *
   * @param baseSpace base space
   * @param neighbors neighbors
   * @param weapons   weapons
   */
  public Space(BaseSpace baseSpace, List<BaseSpace> neighbors, List<Weapon> weapons) {
    super(baseSpace);
    this.neighbors = neighbors;
    this.weapons = weapons;
  }

  /**
   * Get neighbors.
   * 
   * @return neighbors
   */
  public List<BaseSpace> getNeighbors() {
    return this.neighbors;
  }

  /**
   * Set neighbors.
   * 
   * @param neighbors neighbors
   */
  public void setNeighbors(List<BaseSpace> neighbors) {
    this.neighbors = neighbors;
  }

  /**
   * Get weapons.
   * 
   * @return weapons
   */
  public List<Weapon> getWeapons() {
    return this.weapons;
  }

  /**
   * Set Weapons.
   * 
   * @param weapons weapons
   */
  public void setWeapons(List<Weapon> weapons) {
    this.weapons = weapons;
  }
}
