package world;

import world.model.Space;

import java.io.IOException;
import java.util.List;

/**
 * The world interface of the game.
 * 
 * @author anbang
 * @date 2023-01-28 20:18
 */
public interface World {

  /**
   * Get the neighbors of the given space name. Spaces that share a "wall" are
   * neighbors.
   * 
   * @param space the space name
   * @return the list of neighbors
   */
  List<String> getNeighbors(String space);

  /**
   * Get the neighbors of the given space index. Spaces that share a "wall" are
   * neighbors.
   *
   * @param index the space index
   * @return the list of neighbors
   */
  List<String> getNeighbors(Integer index);

  /**
   * Get the space with neighbors and weapons.
   * 
   * @param name space name
   * @return space
   */
  Space getSpace(String name);

  /**
   * Get the space with neighbors and weapons.
   *
   * @param index space index
   * @return space
   */
  Space getSpace(Integer index);

  /**
   * Get target current position.
   * 
   * @return space of current position
   */
  Space getTargetPosition();

  /**
   * Move the target character.
   * 
   * @return the space that target is in
   */
  Space moveTarget();

  /**
   * Show the world in image.
   * 
   * @param directory directory of output
   * @throws IOException if write file fail
   */
  void showGraphicalImage(String directory) throws IOException;
}
