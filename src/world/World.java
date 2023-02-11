package world;

import java.io.IOException;
import java.util.List;
import world.exception.BusinessException;
import world.model.Player;
import world.model.Space;
import world.model.Weapon;

/**
 * The world interface of the game.
 * 
 * @author anbang
 * @date 2023-01-28 20:18
 */
public interface World {

  /**
   * Get the whole space name list.
   * 
   * @return space name list
   */
  List<String> getAllSpaces();

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
   * Get all the players.
   * 
   * @return players
   */
  List<Player> getAllPlayers();

  /**
   * Get player according to index.
   * 
   * @param index index
   * @return player
   */
  Player getPlayer(int index);

  /**
   * Add a player.
   * 
   * @param player player
   * @return if successful
   */
  Boolean addPlayer(Player player);

  /**
   * Move a player to a space.
   * 
   * @param player player
   * @param space  space
   * @throws BusinessException invalid space
   */
  void movePlayer(Player player, Space space) throws BusinessException;

  /**
   * Player pick up a weapon.
   * 
   * @param player player
   * @param weapon weapon
   * @throws BusinessException player's weapons reach limit
   */
  void pickUp(Player player, Weapon weapon) throws BusinessException;

  /**
   * Show the world in image.
   * 
   * @param directory directory of output
   * @throws IOException if write file fail
   */
  void showGraphicalImage(String directory) throws IOException;
}
