package flowengine.request;

import model.model.Player;

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
   * Skip input.
   */
  protected Boolean skipInput = Boolean.FALSE;

  /**
   * Constructor.
   * 
   * @param player player
   */
  public BaseRequest(Player player) {
    this.player = player;
  }

  /**
   * Constructor.
   * 
   * @param player    player
   * @param skipInput skip input
   */
  public BaseRequest(Player player, Boolean skipInput) {
    this.player = player;
    this.skipInput = skipInput;
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

  /**
   * Get skip input.
   * 
   * @return skip input
   */
  public Boolean getSkipInput() {
    return skipInput;
  }
}
