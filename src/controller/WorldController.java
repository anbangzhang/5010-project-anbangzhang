package controller;

import model.context.Context;

/**
 * WorldController interface.
 *
 * @author anbang
 * @date 2023-02-10 22:28
 */
public interface WorldController {

  /**
   * Set limit.
   *
   * @param turn turn
   */
  void setTurn(Integer turn);

  /**
   * Set max players.
   * 
   * @param amount amount
   */
  void setMaxPlayerAmount(Integer amount);

  /**
   * Play the game.
   * 
   * @param context context
   */
  void playGame(Context context);

  /**
   * Create a new game.
   */
  void createNewGame();

  /**
   * Restart the game.
   */
  void restartGame();

  /**
   * Quit game.
   */
  void quitGame();
}
