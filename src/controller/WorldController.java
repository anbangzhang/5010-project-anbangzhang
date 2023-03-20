package controller;

import world.context.Context;

/**
 * WorldController interface.
 *
 * @author anbang
 * @date 2023-02-10 22:28
 */
public interface WorldController {

  /**
   * Set input.
   *
   * @param in input
   */
  void setIn(Readable in);

  /**
   * Set output.
   *
   * @param out output
   */
  void setOut(Appendable out);

  /**
   * Set limit.
   *
   * @param turn turn
   */
  void setTurn(Integer turn);

  /**
   * Play the game.
   * 
   * @param context context
   */
  void playGame(Context context);
}
