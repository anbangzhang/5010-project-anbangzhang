package controller.gameengine;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import flowengine.context.FlowContext;
import flowengine.enums.Flow;
import flowengine.process.BaseProcessCallBack;
import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;
import flowengine.template.ServiceTemplate;
import model.World;
import model.constant.Constants;
import model.context.Context;
import model.context.ContextHolder;
import model.exception.BusinessException;
import model.model.Player;

/**
 * GameEngine.
 * 
 * @author anbang
 * @date 2023-04-12 22:46
 */
public class GameEngine {

  private int count;

  private int currentPlayer;

  private int turnAmount;

  private Boolean gameOver = Boolean.FALSE;

  private Context context;

  /**
   * Constructor.
   * 
   * @param context    context
   * @param turnAmount turnAmount
   */
  public GameEngine(Context context, int turnAmount) {
    this.count = 0;
    this.currentPlayer = 0;
    this.turnAmount = turnAmount;
    this.context = context;
  }

  /**
   * Get current player.
   *
   * @return currentPlayer
   */
  public Player getCurrentPlayer() {
    List<Player> players = context.getPlayers();
    if (players.size() == 0) {
      throw new BusinessException("No players in the game.");
    }
    return players.get(currentPlayer);
  }

  /**
   * Get turn.
   * 
   * @return turn
   */
  public Integer getTurn() {
    List<Player> players = context.getPlayers();
    if (players.size() == 0) {
      throw new BusinessException("No players in the game.");
    }
    return count / players.size() + 1;
  }

  /**
   * Is game over.
   * 
   * @return gameOver
   */
  public Boolean gameOver() {
    return this.gameOver;
  }

  /**
   * Update state.
   * 
   * @return if it's a new turn
   */
  public Boolean updateState() {
    if (context.getPlayers().size() == 0) {
      throw new BusinessException("No players in the game.");
    }
    count++;
    currentPlayer = (currentPlayer + 1) % context.getPlayers().size();

    boolean result = false;

    // new turn
    if (count % context.getPlayers().size() == 0) {

      result = true;
    }

    if (Objects.nonNull(this.context.get(Constants.WINNER))) {
      this.gameOver = Boolean.TRUE;
    } else if (count == this.context.getPlayers().size() * turnAmount) {
      this.gameOver = Boolean.TRUE;
      result = true;
    }
    return result;
  }

}
