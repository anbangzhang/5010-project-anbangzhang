import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import world.base.BasePlayer;
import world.base.BaseSpace;
import world.base.BaseWeapon;
import world.container.Context;
import world.container.ContextHolder;
import world.enums.PlayerType;
import world.model.Player;
import world.model.Space;

/**
 * Test class for BaseSpace and Space.
 * 
 * @author anbang
 * @date 2023-01-29 04:52
 */
public class SpaceTest {

  private Space space;

  private Context ctx;

  /**
   * Set up baseSpace and space.
   */
  @Before
  public void setUp() {
    List<BaseSpace> baseSpaces = initNeighbors();
    space = new BaseSpace(1, 1, 2, 2, 0, "space");
    ctx = new Context();
    ctx.setNeighborMap(new HashMap<>());
    ctx.getNeighborMap().put((BaseSpace) space, baseSpaces);

    ctx.setWeapons(new ArrayList<>());
    BaseWeapon w1 = new BaseWeapon(0, 2, "weapon0");
    w1.setHolder("space: space");
    BaseWeapon w2 = new BaseWeapon(0, 3, "weapon1");
    w2.setHolder("space: space");
    ctx.getWeapons().add(w1);
    ctx.getWeapons().add(w2);

    ContextHolder.set(ctx);
  }

  private List<BaseSpace> initNeighbors() {
    List<BaseSpace> result = new ArrayList<>();
    result.add(new BaseSpace(0, 0, 1, 2, 1, "b1"));
    result.add(new BaseSpace(1, 0, 2, 1, 2, "b2"));
    return result;
  }

  @Test
  public void testInitFail() {
    try {
      space = new BaseSpace(-1, 0, 1, 1, 0, "fail");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid coordinates.", e.getMessage());
    }
    try {
      space = new BaseSpace(0, -10, 1, 1, 0, "fail");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid coordinates.", e.getMessage());
    }
    try {
      space = new BaseSpace(0, 0, -1, 1, 0, "fail");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid coordinates.", e.getMessage());
    }
    try {
      space = new BaseSpace(0, 0, 1, -1, 0, "fail");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid coordinates.", e.getMessage());
    }
    try {
      space = new BaseSpace(10, 10, 3, 30, 0, "fail");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid coordinates.", e.getMessage());
    }
    try {
      space = new BaseSpace(10, 10, 15, 1, 0, "fail");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid coordinates.", e.getMessage());
    }
    try {
      space = new BaseSpace(10, 10, 15, 18, -1, "fail");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid order.", e.getMessage());
    }
  }

  @Test
  public void testGetName() {
    Assert.assertEquals("space", space.getName());
  }

  @Test
  public void testGetStart() {
    BaseSpace baseSpace = (BaseSpace) space;
    int[] start = baseSpace.getStart();
    Assert.assertEquals(1, start[0]);
    Assert.assertEquals(1, start[1]);
  }

  @Test
  public void testGetEnd() {
    BaseSpace baseSpace = (BaseSpace) space;
    int[] end = baseSpace.getEnd();
    Assert.assertEquals(2, end[0]);
    Assert.assertEquals(2, end[1]);
  }

  @Test
  public void testGetOrder() {
    Assert.assertEquals(0, space.getOrder());
  }

  @Test
  public void testNeighbors() {
    List<Space> neighbors = space.getNeighbors();
    Assert.assertEquals(2, neighbors.size());
  }

  @Test
  public void testWeapons() {
    List<BaseWeapon> weapons = space.getWeapons();
    Assert.assertEquals(2, weapons.size());
  }

  @Test
  public void testOccupiers() {
    Assert.assertEquals(0, space.getOccupiers().size());

    List<Player> players = new ArrayList<>();
    players.add(new BasePlayer(0, "player0", 0, PlayerType.HUMAN_CONTROLLED, 4));
    players.add(new BasePlayer(1, "player1", 0, PlayerType.COMPUTER_CONTROLLED, 2));
    ctx.setPlayers(players);
    Assert.assertEquals(2, space.getOccupiers().size());
  }

}
