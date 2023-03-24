package flowengine;

import flowengine.enums.Flow;
import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;
import flowengine.template.ServiceTemplate;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import world.World;
import world.base.BasePlayer;
import world.base.BaseWeapon;
import world.constant.Constants;
import world.context.Context;
import world.context.ContextBuilder;
import world.context.ContextHolder;
import world.enums.PlayerType;
import world.model.Player;

/**
 * ServiceTemplateTest.
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
      ctx.getPlayers()
          .add(new BasePlayer(1, "computerPlayer", 1, PlayerType.COMPUTER_CONTROLLED, 1));

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
    Assert.assertEquals("Player move to the neighbor space succeed.", result.getResult());
    Assert.assertEquals("Billiard Room", World.getSpace(ctx, player.getSpaceIndex()).getName());
    Assert.assertEquals("Please input a neighbor space name from the neighbors: [Billiard Room,"
        + " Dining Hall, Drawing Room]\n", log.toString());
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
    Assert.assertEquals("Player move to the neighbor space succeed.", result.getResult());
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
    Assert.assertEquals("Space: [Parlor] is not the neighbor of the current space.",
        result.getErrorMsg());
    Assert.assertEquals("Armory", World.getSpace(ctx, player.getSpaceIndex()).getName());
    Assert.assertEquals("Please input a neighbor space name from the neighbors: [Billiard Room,"
        + " Dining Hall, Drawing Room]\n", log.toString());
  }

  @Test
  public void testHumanPlayerPickUpWeapon() throws IOException {
    StringReader input = new StringReader("Crepe Pan\nGun\nSharp Knife\n");
    Scanner scan = new Scanner(input);
    StringBuilder log = new StringBuilder();
    ctx.set(Constants.SCANNER, scan);
    ctx.set(Constants.OUT, log);
    player.setSpaceIndex(8);

    BaseResult result = template.execute(Flow.PICK_UP_WEAPON.getDesc(), new BaseRequest(player));

    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals(1, player.getWeapons().size());
    Assert.assertEquals("Please input a weapon name from the weapons: [Crepe Pan, Sharp Knife]\n",
        log.toString());

    // pick up fail due to no such weapon
    result = template.execute(Flow.PICK_UP_WEAPON.getDesc(), new BaseRequest(player));

    Assert.assertFalse(result.isSuccess());
    Assert.assertEquals("Weapon: [Gun] is not in the current space.", result.getErrorMsg());
    Assert.assertEquals(1, player.getWeapons().size());
    Assert.assertEquals("Please input a weapon name from the weapons: [Crepe Pan, Sharp Knife]\n"
        + "Please input a weapon name from the weapons: [Sharp Knife]\n", log.toString());

    // pick up fail due to limit
    result = template.execute(Flow.PICK_UP_WEAPON.getDesc(), new BaseRequest(player));

    Assert.assertFalse(result.isSuccess());
    Assert.assertEquals("Player's weapons reach limit.", result.getErrorMsg());
    Assert.assertEquals(1, player.getWeapons().size());
    Assert.assertEquals("Please input a weapon name from the weapons: [Crepe Pan, Sharp Knife]\n"
        + "Please input a weapon name from the weapons: [Sharp Knife]\n"
        + "Please input a weapon name from the weapons: [Sharp Knife]\n", log.toString());
  }

  @Test
  public void testComputerPlayerPickUpWeapon() throws IOException {
    StringReader input = new StringReader("");
    Scanner scan = new Scanner(input);
    StringBuilder log = new StringBuilder();
    ctx.set(Constants.SCANNER, scan);
    ctx.set(Constants.OUT, log);

    BaseResult result = template.execute(Flow.PICK_UP_WEAPON.getDesc(),
        new BaseRequest(ctx.getPlayers().get(1), Boolean.TRUE));

    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals(1, ctx.getPlayers().get(1).getWeapons().size());
    Assert.assertEquals("", log.toString());

    // fail due to no weapon in the space
    result = template.execute(Flow.PICK_UP_WEAPON.getDesc(),
        new BaseRequest(ctx.getPlayers().get(1), Boolean.TRUE));

    Assert.assertFalse(result.isSuccess());
    Assert.assertEquals("There is no weapon in the space.", result.getErrorMsg());
    Assert.assertEquals(1, ctx.getPlayers().get(1).getWeapons().size());
    Assert.assertEquals("", log.toString());
  }

  @Test
  public void testLookAround() throws IOException {
    StringReader input = new StringReader("");
    Scanner scan = new Scanner(input);
    StringBuilder log = new StringBuilder();
    ctx.set(Constants.SCANNER, scan);
    ctx.set(Constants.OUT, log);

    ctx.getPlayers().add(new BasePlayer(2, "samplePlayer", 0, PlayerType.HUMAN_CONTROLLED, 2));

    BaseResult result = template.execute(Flow.LOOK_AROUND.getDesc(), new BaseRequest(player));

    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals(
        "Space: [Armory], occupiers: [humanPlayer, samplePlayer], weapons: [Revolver], "
            + "Target: [Doctor Lucky], Pet: [Fortune the Cat]\n" + "Neighbors: [\n"
            + "Space: [Billiard Room], occupiers: [computerPlayer], weapons: [Billiard Cue]\n"
            + "Space: [Dining Hall], occupiers: [], weapons: []\n"
            + "Space: [Drawing Room], occupiers: [], weapons: [Letter Opener]]",
        result.getResult());
    Assert.assertEquals("", log.toString());

    // the pet blocks the visibility of Armory
    result = template.execute(Flow.LOOK_AROUND.getDesc(),
        new BaseRequest(ctx.getPlayers().get(1), Boolean.TRUE));

    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals(
        "Space: [Billiard Room], occupiers: [computerPlayer], weapons: [Billiard Cue]\n"
            + "Neighbors: [\n" + "Space: [Armory], Pet: [Fortune the Cat]\n"
            + "Space: [Dining Hall], occupiers: [], weapons: []\n"
            + "Space: [Trophy Room], occupiers: [], weapons: [Duck Decoy, Monkey Hand]]",
        result.getResult());
    Assert.assertEquals("", log.toString());
  }

  @Test
  public void testMovePet() throws IOException {
    StringReader input = new StringReader("Spa Room\nArmory\nTrophy Room\n");
    Scanner scan = new Scanner(input);
    StringBuilder log = new StringBuilder();
    ctx.set(Constants.SCANNER, scan);
    ctx.set(Constants.OUT, log);

    // fail due to the space not exist
    BaseResult result = template.execute(Flow.MOVE_PET.getDesc(), new BaseRequest(player));

    Assert.assertFalse(result.isSuccess());
    Assert.assertEquals("Space: [Spa Room] is not in the world.", result.getErrorMsg());
    Assert.assertEquals(0, ctx.getPet().getSpaceIndex());
    Assert.assertEquals(
        "Please input a space name from the spaces: [Armory, Billiard Room, Carriage House, "
            + "Dining Hall, Drawing Room, Foyer, Green House, Hedge Maze, Kitchen, Lancaster Room, "
            + "Library, Lilac Room, Master Suite, Nursery, Parlor, Piazza, Servants' Quarters, "
            + "Tennessee Room, Trophy Room, Wine Cellar, Winter Garden]\n",
        log.toString());

    // fail due to moving to the current space
    result = template.execute(Flow.MOVE_PET.getDesc(), new BaseRequest(player));

    Assert.assertFalse(result.isSuccess());
    Assert.assertEquals("The pet is already in Space: [Armory].", result.getErrorMsg());
    Assert.assertEquals(0, ctx.getPet().getSpaceIndex());
    Assert.assertEquals(
        "Please input a space name from the spaces: [Armory, Billiard Room, Carriage House, "
            + "Dining Hall, Drawing Room, Foyer, Green House, Hedge Maze, Kitchen, Lancaster Room, "
            + "Library, Lilac Room, Master Suite, Nursery, Parlor, Piazza, Servants' Quarters, "
            + "Tennessee Room, Trophy Room, Wine Cellar, Winter Garden]\n"
            + "Please input a space name from the spaces: [Armory, Billiard Room, Carriage House, "
            + "Dining Hall, Drawing Room, Foyer, Green House, Hedge Maze, Kitchen, Lancaster Room, "
            + "Library, Lilac Room, Master Suite, Nursery, Parlor, Piazza, Servants' Quarters, "
            + "Tennessee Room, Trophy Room, Wine Cellar, Winter Garden]\n",
        log.toString());

    result = template.execute(Flow.MOVE_PET.getDesc(), new BaseRequest(player));

    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals("Player move the pet succeed.", result.getResult());
    Assert.assertEquals("Trophy Room", World.getSpace(ctx, ctx.getPet().getSpaceIndex()).getName());
    Assert.assertEquals(
        "Please input a space name from the spaces: [Armory, Billiard Room, Carriage House, "
            + "Dining Hall, Drawing Room, Foyer, Green House, Hedge Maze, Kitchen, Lancaster Room, "
            + "Library, Lilac Room, Master Suite, Nursery, Parlor, Piazza, Servants' Quarters, "
            + "Tennessee Room, Trophy Room, Wine Cellar, Winter Garden]\n"
            + "Please input a space name from the spaces: [Armory, Billiard Room, Carriage House, "
            + "Dining Hall, Drawing Room, Foyer, Green House, Hedge Maze, Kitchen, Lancaster Room, "
            + "Library, Lilac Room, Master Suite, Nursery, Parlor, Piazza, Servants' Quarters, "
            + "Tennessee Room, Trophy Room, Wine Cellar, Winter Garden]\n"
            + "Please input a space name from the spaces: [Armory, Billiard Room, Carriage House, "
            + "Dining Hall, Drawing Room, Foyer, Green House, Hedge Maze, Kitchen, Lancaster Room, "
            + "Library, Lilac Room, Master Suite, Nursery, Parlor, Piazza, Servants' Quarters, "
            + "Tennessee Room, Trophy Room, Wine Cellar, Winter Garden]\n",
        log.toString());
  }

  @Test
  public void testHumanAttackTarget() throws IOException {
    StringReader input = new StringReader("Revolver\n");
    Scanner scan = new Scanner(input);
    StringBuilder log = new StringBuilder();
    ctx.set(Constants.SCANNER, scan);
    ctx.set(Constants.OUT, log);

    ctx.getTarget().decreaseHealth(-3);

    /*
     * Attack without weapon when there's a pet in the space and no player looking
     * around in the neighbor space
     */
    BaseResult result = template.execute(Flow.ATTACK_TARGET.getDesc(), new BaseRequest(player));

    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals("Attack succeed.", result.getResult());
    Assert.assertEquals(5, ctx.getTarget().getHealth());

    // [computerPlayer] look around, but the pet block the visibility
    result = template.execute(Flow.LOOK_AROUND.getDesc(),
        new BaseRequest(ctx.getPlayers().get(1), Boolean.TRUE));
    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals(
        "Space: [Billiard Room], occupiers: [computerPlayer], weapons: [Billiard Cue]\n"
            + "Neighbors: [\n" + "Space: [Armory], Pet: [Fortune the Cat]\n"
            + "Space: [Dining Hall], occupiers: [], weapons: []\n"
            + "Space: [Trophy Room], occupiers: [], weapons: [Duck Decoy, Monkey Hand]]",
        result.getResult());
    Assert.assertEquals("", log.toString());
    /*
     * Attack without weapon when there's a pet in the space and a player looking
     * around in the neighbor space
     */
    result = template.execute(Flow.ATTACK_TARGET.getDesc(), new BaseRequest(player));
    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals("Attack succeed.", result.getResult());
    Assert.assertEquals(4, ctx.getTarget().getHealth());

    // [humanPlayer] pick up weapon [Revolver]
    BaseWeapon weapon = null;
    for (BaseWeapon baseWeapon : ctx.getWeapons()) {
      if (Objects.equals(baseWeapon.getName(), "Revolver")) {
        weapon = baseWeapon;
        World.pickUp(player, baseWeapon);
      }
    }
    /*
     * Attack with weapon when there's a pet in the space and a player looking
     * around in the neighbor space
     */
    result = template.execute(Flow.ATTACK_TARGET.getDesc(), new BaseRequest(player));
    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals("Attack succeed.", result.getResult());
    Assert.assertEquals(1, ctx.getTarget().getHealth());
    // weapon [Revolver] becomes evidence
    Assert.assertTrue(ctx.getEvidences().contains(weapon));
    Assert.assertFalse(ctx.getWeapons().contains(weapon));

    // no one look around
    ctx.setExposedSpaces(new HashSet<>());
    // [computerPlayer] move to other space(not neighbor space)
    ctx.getPlayers().get(1).setSpaceIndex(14);
    /*
     * Attack without weapon when there's no player looking round and no pet in the
     * space. [humanPlayer] win the game.
     */
    result = template.execute(Flow.ATTACK_TARGET.getDesc(), new BaseRequest(player));
    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals("Attack succeed.", result.getResult());
    Assert.assertEquals(0, ctx.getTarget().getHealth());
    Assert.assertEquals(player, ctx.get(Constants.WINNER));
  }

  @Test
  public void testComputerAttackTarget() throws IOException {
    StringReader input = new StringReader("");
    Scanner scan = new Scanner(input);
    StringBuilder log = new StringBuilder();
    ctx.set(Constants.SCANNER, scan);
    ctx.set(Constants.OUT, log);

    World.moveTarget(ctx);
    /*
     * Attack without weapon when there's no pet in the space and no player looking
     * around in the neighbor space
     */
    BaseResult result = template.execute(Flow.ATTACK_TARGET.getDesc(),
        new BaseRequest(ctx.getPlayers().get(1), Boolean.TRUE));

    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals("Attack succeed.", result.getResult());
    Assert.assertEquals(2, ctx.getTarget().getHealth());

    // [computerPlayer] pick up weapon [Billiard Cue]
    result = template.execute(Flow.PICK_UP_WEAPON.getDesc(),
        new BaseRequest(ctx.getPlayers().get(1), Boolean.TRUE));
    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals(1, ctx.getPlayers().get(1).getWeapons().size());

    /*
     * Attack with weapon when there's no pet in the space and no player looking
     * around in the neighbor space. [computerPlayer] win the game.
     */
    result = template.execute(Flow.ATTACK_TARGET.getDesc(),
        new BaseRequest(ctx.getPlayers().get(1), Boolean.TRUE));

    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals("Attack succeed.", result.getResult());
    Assert.assertEquals(0, ctx.getTarget().getHealth());
    Assert.assertEquals(ctx.getPlayers().get(1), ctx.get(Constants.WINNER));
  }

  @Test
  public void testAttackTargetFail() throws IOException {
    StringReader input = new StringReader("");
    Scanner scan = new Scanner(input);
    StringBuilder log = new StringBuilder();
    ctx.set(Constants.SCANNER, scan);
    ctx.set(Constants.OUT, log);

    ctx.getPlayers().add(new BasePlayer(2, "samplePlayer", 0, PlayerType.HUMAN_CONTROLLED, 2));

    /*
     * Attack seen by other player in the same space
     */
    BaseResult result = template.execute(Flow.ATTACK_TARGET.getDesc(), new BaseRequest(player));

    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals("Attack failed, the attack is seen.", result.getResult());
    Assert.assertEquals(3, ctx.getTarget().getHealth());

    // move pet to other space, move other player to other space
    ctx.getPet().setSpaceIndex(8);
    ctx.getPlayers().get(2).setSpaceIndex(8);

    // [humanPlayer] pick up weapon [Revolver]
    BaseWeapon weapon = null;
    for (BaseWeapon baseWeapon : ctx.getWeapons()) {
      if (Objects.equals(baseWeapon.getName(), "Revolver")) {
        weapon = baseWeapon;
        World.pickUp(player, baseWeapon);
      }
    }
    // [computerPlayer] look around and see humanPlayer
    result = template.execute(Flow.LOOK_AROUND.getDesc(),
        new BaseRequest(ctx.getPlayers().get(1), Boolean.TRUE));
    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals(
        "Space: [Billiard Room], occupiers: [computerPlayer], weapons: [Billiard Cue]\n"
            + "Neighbors: [\n" + "Space: [Armory], occupiers: [humanPlayer], weapons: [], "
            + "Target: [Doctor Lucky]\n" + "Space: [Dining Hall], occupiers: [], weapons: []\n"
            + "Space: [Trophy Room], occupiers: [], weapons: [Duck Decoy, Monkey Hand]]",
        result.getResult());
    /*
     * Attack seen by player in the neighbor space
     */
    result = template.execute(Flow.ATTACK_TARGET.getDesc(), new BaseRequest(player));
    Assert.assertTrue(result.isSuccess());
    Assert.assertEquals("Attack failed, the attack is seen.", result.getResult());
    Assert.assertEquals(3, ctx.getTarget().getHealth());
    // weapon [Revolver] becomes evidence
    Assert.assertTrue(ctx.getEvidences().contains(weapon));
    Assert.assertFalse(ctx.getWeapons().contains(weapon));

    /*
     * The target and the player are not in the same space
     */
    result = template.execute(Flow.ATTACK_TARGET.getDesc(),
        new BaseRequest(ctx.getPlayers().get(2)));
    Assert.assertFalse(result.isSuccess());
    Assert.assertEquals("The target and the player are not in the same space.",
        result.getErrorMsg());
    Assert.assertEquals(3, ctx.getTarget().getHealth());
  }

  @Test
  public void testPetDfs() throws IOException {
    StringReader input = new StringReader("");
    Scanner scan = new Scanner(input);
    StringBuilder log = new StringBuilder();
    ctx.set(Constants.SCANNER, scan);
    ctx.set(Constants.OUT, log);

    Set<Integer> set = new HashSet<>();
    set.add(ctx.getPet().getSpaceIndex());
    for (int i = 0; i < ctx.getSpaces().size(); i++) {
      BaseResult result = template.execute(Flow.PET_DFS.getDesc(), new BaseRequest(null));

      Assert.assertTrue(result.isSuccess());
      set.add(ctx.getPet().getSpaceIndex());
    }
    Assert.assertEquals(set.size(), ctx.getSpaces().size());
  }

}