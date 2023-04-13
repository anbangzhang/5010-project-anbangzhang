package model.base;

import model.model.Pet;

/**
 * BasePet.
 * 
 * @author anbang
 * @date 2023-03-15 02:55
 */
public class BasePet implements Pet {

  /**
   * Name.
   */
  private String name;

  /**
   * Space index.
   */
  private int spaceIndex;

  /**
   * Constructor.
   * 
   * @param name pet name
   */
  public BasePet(String name) {
    this.name = name;
    this.spaceIndex = 0;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getSpaceIndex() {
    return this.spaceIndex;
  }

  @Override
  public void setSpaceIndex(int index) {
    this.spaceIndex = index;
  }
}
