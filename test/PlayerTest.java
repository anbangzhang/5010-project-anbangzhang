import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import world.base.BasePlayer;
import world.base.BaseWeapon;
import world.enums.PlayerType;
import world.model.Player;
import world.model.Weapon;

/**
 * Test class for player.
 * 
 * @author anbang
 * @date 2023-02-11 01:43
 */
public class PlayerTest {

  private Player humanPlayer;

  private Player computerPlayer;

  /**
   * Set up the players.
   */
  @Before
  public void setUp() {
    humanPlayer = new BasePlayer(0, "player0", 0, PlayerType.HUMAN_CONTROLLED, 3);
    computerPlayer = new BasePlayer(1, "player1", 0, PlayerType.COMPUTER_CONTROLLED, null);
  }

  @Test
  public void testConstructFail() {
    try {
      humanPlayer = new BasePlayer(-1, "player0", 0, PlayerType.HUMAN_CONTROLLED, null);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Order can't be smaller than 0.", e.getMessage());
    }
  }

  @Test
  public void testName() {
    Assert.assertEquals("player0", humanPlayer.getName());
    Assert.assertEquals("player1", computerPlayer.getName());
  }

  @Test
  public void testOrder() {
    Assert.assertEquals(0, humanPlayer.getOrder());
    Assert.assertEquals(1, computerPlayer.getOrder());
  }

  @Test
  public void testSpaceIndex() {
    Assert.assertEquals(0, humanPlayer.getSpaceIndex());
    Assert.assertEquals(0, computerPlayer.getSpaceIndex());
    humanPlayer.setSpaceIndex(10);
    computerPlayer.setSpaceIndex(11);
    Assert.assertEquals(10, humanPlayer.getSpaceIndex());
    Assert.assertEquals(11, computerPlayer.getSpaceIndex());
  }

  @Test
  public void testType() {
    Assert.assertEquals(PlayerType.HUMAN_CONTROLLED, humanPlayer.getType());
    Assert.assertEquals(PlayerType.COMPUTER_CONTROLLED, computerPlayer.getType());
  }

  @Test
  public void testLimit() {
    Assert.assertNull(computerPlayer.getWeaponLimit());
    Assert.assertEquals(3, humanPlayer.getWeaponLimit().intValue());
  }

  @Test
  public void testWeapons() {
    for (int i = 0; i < 3; i++) {
      Weapon w = new Weapon(new BaseWeapon(i, i + 1, String.valueOf(i)));
      humanPlayer.addWeapon(w);
      computerPlayer.addWeapon(w);
    }
    Assert.assertEquals(3, humanPlayer.getWeapons().size());
    Assert.assertEquals(3, computerPlayer.getWeapons().size());
    Weapon w = new Weapon(new BaseWeapon(4, 5, "4"));
    Assert.assertFalse(humanPlayer.addWeapon(w));
    Assert.assertTrue(computerPlayer.addWeapon(w));
  }
}
