package flowengine;

import flowengine.enums.Flow;
import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;
import flowengine.template.ServiceTemplate;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import world.World;
import world.base.BasePlayer;
import world.constant.Constants;
import world.context.Context;
import world.context.ContextBuilder;
import world.context.ContextHolder;
import world.enums.PlayerType;
import world.model.Player;

/**
 * flowengine.ServiceTemplateTest.
 * 
 * @author anbang
 * @date 2023-03-23 03:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FlowEngineApplication.class)
public class ServiceTemplateTest {

  @Autowired
  private ServiceTemplate template;

  private Player player;

  private Context ctx;

  @Before
  public void setUp() {
    try {
      Readable fileReader = new FileReader("./res/world specification/mansion.txt");
      ctx = ContextBuilder.build(fileReader);

      player = new BasePlayer(0, "humanPlayer", 0, PlayerType.HUMAN_CONTROLLED, 1);
      ctx.getPlayers().add(player);
      ctx.getPlayers().add(new BasePlayer(1, "humanPlayer", 1, PlayerType.COMPUTER_CONTROLLED, 1));

      ContextHolder.set(ctx);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testMoveHumanPlayer() throws IOException {
    StringReader input = new StringReader("Billiard Room");
    Scanner scan = new Scanner(input);
    StringBuilder log = new StringBuilder();
    ctx.set(Constants.SCANNER, scan);
    ctx.set(Constants.OUT, log);

    BaseResult result = template.execute(Flow.MOVE_PLAYER.getDesc(), new BaseRequest(player));

    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals("Billiard Room", World.getSpace(ctx, player.getSpaceIndex()).getName());
    Assert.assertEquals(
        "Please input a neighbor space name from the neighbors: [Billiard Room, Dining Hall, Drawing Room]\n",
        log.toString());
  }

  @Test
  public void testMoveComputerPlayer() throws IOException {
    StringReader input = new StringReader("");
    Scanner scan = new Scanner(input);
    StringBuilder log = new StringBuilder();
    ctx.set(Constants.SCANNER, scan);
    ctx.set(Constants.OUT, log);

    BaseResult result = template.execute(Flow.MOVE_PLAYER.getDesc(),
        new BaseRequest(ctx.getPlayers().get(1), Boolean.TRUE));

    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals("Armory",
        World.getSpace(ctx, ctx.getPlayers().get(1).getSpaceIndex()).getName());
    Assert.assertEquals("", log.toString());
  }

  @Test
  public void testMovePlayerFail() throws IOException {
    StringReader input = new StringReader("Parlor");
    Scanner scan = new Scanner(input);
    StringBuilder log = new StringBuilder();
    ctx.set(Constants.SCANNER, scan);
    ctx.set(Constants.OUT, log);

    BaseResult result = template.execute(Flow.MOVE_PLAYER.getDesc(), new BaseRequest(player));

    Assert.assertFalse(result.isSuccess());
    Assert.assertEquals("Armory", World.getSpace(ctx, player.getSpaceIndex()).getName());
    Assert.assertEquals(
        "Please input a neighbor space name from the neighbors: [Billiard Room, Dining Hall, Drawing Room]\n",
        log.toString());
  }

}
