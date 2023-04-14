package controller;

import controller.gameengine.GameEngine;
import java.io.FileNotFoundException;
import java.io.FileReader;
import model.base.BasePlayer;
import model.context.Context;
import model.context.ContextBuilder;
import model.context.ContextHolder;
import model.enums.PlayerType;
import model.exception.BusinessException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * GameEngineTest.
 * 
 * @author anbang
 * @date 2023-04-13 19:45
 */
public class GameEngineTest {

  private Context context;

  private GameEngine gameEngine;

  @Before
  public void setUp() {
    try {
      /* Construct model with file */
      Readable fileReader = new FileReader("./res/world specification/mansion.txt");
      context = ContextBuilder.build(fileReader);
      ContextHolder.set(context);

      gameEngine = new GameEngine(context, 3);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetTurn() {
    try {
      Assert.assertEquals(1, gameEngine.getTurn().intValue());
    } catch (BusinessException e) {
      Assert.assertEquals("No players in the game.", e.getMessage());
    }

    context.getPlayers().add(new BasePlayer(0, "P0", 0, PlayerType.HUMAN_CONTROLLED, 10));
    context.getPlayers().add(new BasePlayer(1, "P1", 0, PlayerType.COMPUTER_CONTROLLED, 10));

    Assert.assertEquals(1, gameEngine.getTurn().intValue());
    Assert.assertEquals("P0", gameEngine.getCurrentPlayer().getName());
    gameEngine.updateState();
    Assert.assertEquals("P1", gameEngine.getCurrentPlayer().getName());
    gameEngine.updateState();
    Assert.assertEquals(2, gameEngine.getTurn().intValue());
  }

  @Test
  public void testGameOver() {
    context.getPlayers().add(new BasePlayer(0, "P0", 0, PlayerType.HUMAN_CONTROLLED, 10));
    context.getPlayers().add(new BasePlayer(1, "P1", 0, PlayerType.COMPUTER_CONTROLLED, 10));

    Assert.assertFalse(gameEngine.gameOver());

    for (int i = 0; i < 3; i++) {
      Assert.assertFalse(gameEngine.updateState());
      Assert.assertTrue(gameEngine.updateState());
    }

    Assert.assertTrue(gameEngine.gameOver());
  }

}
