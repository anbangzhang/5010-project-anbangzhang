package controller.impl;

import java.util.Objects;
import java.util.Scanner;
import controller.WorldController;
import world.World;

/**
 * WorldConsoleController class.
 * 
 * @author anbang
 * @date 2023-02-10 22:30
 */
public class WorldConsoleController implements WorldController {

  /**
   * Input.
   */
  private Readable in;

  /**
   * Output.
   */
  private Appendable out;

  /**
   * Turn limit.
   */
  private Integer turn;

  /**
   * Scanner.
   */
  private Scanner scan;

  /**
   * Constructor.
   * 
   * @param in   input
   * @param out  output
   * @param turn turn limit
   */
  public WorldConsoleController(Readable in, Appendable out, Integer turn) {
    if (Objects.isNull(in) || Objects.isNull(out)) {
      throw new IllegalArgumentException("Invalid input and output source.");
    }
    this.in = in;
    this.out = out;
    this.turn = turn;
    this.scan = new Scanner(this.in);
  }

  /**
   * Play the game.
   *
   * @param world world model
   */
  @Override
  public void playGame(World world) {

  }
}
