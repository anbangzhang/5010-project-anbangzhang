package flowengine.request;

import world.model.Player;

/**
 * BaseRequest.
 * 
 * @author anbang
 * @date 2023-03-18 04:45
 */
public class BaseRequest {
  /**
   * Player.
   */
  protected Player player;

  /**
   * Input.
   */
  protected String input;

  /**
   * Constructor.
   * 
   * @param player player
   */
  public BaseRequest(Player player) {
    this.player = player;
  }

  /**
   * Get player.
   * 
   * @return player
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Set player.
   * 
   * @param player player
   */
  public void setPlayer(Player player) {
    this.player = player;
  }

  /**
   * Get input.
   * 
   * @return input
   */
  public String getInput() {
    return input;
  }

  /**
   * Set input.
   * 
   * @param input input
   */
  public void setInput(String input) {
    this.input = input;
  }

}
