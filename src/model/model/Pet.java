package model.model;

/**
 * Pet.
 * 
 * @author anbang
 * @date 2023-03-15 02:54
 */
public interface Pet {

  /**
   * Get the pet name.
   * 
   * @return name
   */
  String getName();

  /**
   * Get current space.
   * 
   * @return space index
   */
  int getSpaceIndex();

  /**
   * Set space index.
   * 
   * @param index index
   */
  void setSpaceIndex(int index);
}
