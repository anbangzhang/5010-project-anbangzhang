package world;

import java.io.IOException;
import java.util.List;

import world.base.BaseWeapon;
import world.container.Context;
import world.exception.BusinessException;
import world.model.Player;
import world.model.Space;

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
   * @param context context
   * @return space name list
   */
  List<String> getAllSpaces(Context context);

  /**
   * Get the neighbors of the given space name. Spaces that share a "wall" are
   * neighbors.
   *
   * @param context context
   * @param space   the space name
   * @return the list of neighbors
   */
  List<String> getNeighbors(Context context, String space);

  /**
   * Get the neighbors of the given space index. Spaces that share a "wall" are
   * neighbors.
   *
   * @param context context
   * @param index   the space index
   * @return the list of neighbors
   */
  List<String> getNeighbors(Context context, Integer index);

  /**
   * Get the space with neighbors and weapons.
   *
   * @param context context
   * @param name    space name
   * @return space
   */
  Space getSpace(Context context, String name);

  /**
   * Get the space with neighbors and weapons.
   *
   * @param context context
   * @param index   space index
   * @return space
   */
  Space getSpace(Context context, Integer index);

  /**
   * Get target current position.
   *
   * @param context context
   * @return space of current position
   */
  Space getTargetPosition(Context context);

  /**
   * Move the target character.
   *
   * @param context context
   * @return the space that target is in
   */
  Space moveTarget(Context context);

  /**
   * Get all the players.
   *
   * @param context context
   * @return players
   */
  List<Player> getAllPlayers(Context context);

  /**
   * Get player according to index.
   *
   * @param context context
   * @param index   index
   * @return player
   */
  Player getPlayer(Context context, int index);

  /**
   * Add a player.
   *
   * @param context context
   * @param player  player
   * @return if successful
   */
  Boolean addPlayer(Context context, Player player);

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
  void pickUp(Player player, BaseWeapon weapon) throws BusinessException;

  /**
   * Show the world in image.
   *
   * @param context   context
   * @param directory directory of output
   * @throws IOException if write file fail
   */
  void showGraphicalImage(Context context, String directory) throws IOException;
}
