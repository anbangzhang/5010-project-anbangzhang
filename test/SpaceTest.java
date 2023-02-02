import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import world.Space;
import world.Weapon;
import world.base.BaseSpace;
import world.base.BaseWeapon;

/**
 * Test class for BaseSpace and Space.
 * 
 * @author anbang
 * @date 2023-01-29 04:52
 */
public class SpaceTest {

  private BaseSpace base;

  private Space space;

  /**
   * Set up baseSpace and space.
   */
  @Before
  public void setUp() {
    List<BaseSpace> baseSpaces = initNeighbors();
    base = new BaseSpace(1, 1, 2, 2, 0, "base");
    space = new Space(base, baseSpaces,
        Arrays.asList(new Weapon(new BaseWeapon(0, 2, "weapon0"), base),
            new Weapon(new BaseWeapon(0, 3, "weapon1"), base)));
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
      base = new BaseSpace(-1, 0, 1, 1, 0, "fail");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid coordinates.", e.getMessage());
    }
    try {
      base = new BaseSpace(0, -10, 1, 1, 0, "fail");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid coordinates.", e.getMessage());
    }
    try {
      base = new BaseSpace(0, 0, -1, 1, 0, "fail");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid coordinates.", e.getMessage());
    }
    try {
      base = new BaseSpace(0, 0, 1, -1, 0, "fail");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid coordinates.", e.getMessage());
    }
    try {
      base = new BaseSpace(10, 10, 3, 30, 0, "fail");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid coordinates.", e.getMessage());
    }
    try {
      base = new BaseSpace(10, 10, 15, 1, 0, "fail");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid coordinates.", e.getMessage());
    }
    try {
      base = new BaseSpace(10, 10, 15, 18, -1, "fail");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid order.", e.getMessage());
    }
  }

  @Test
  public void testGetName() {
    Assert.assertEquals("base", base.getName());
    Assert.assertEquals("base", space.getName());
  }

  @Test
  public void testGetStart() {
    int[] start = base.getStart();
    Assert.assertEquals(1, start[0]);
    Assert.assertEquals(1, start[1]);
    start = space.getStart();
    Assert.assertEquals(1, start[0]);
    Assert.assertEquals(1, start[1]);
  }

  @Test
  public void testGetEnd() {
    int[] end = base.getEnd();
    Assert.assertEquals(2, end[0]);
    Assert.assertEquals(2, end[1]);
    end = space.getEnd();
    Assert.assertEquals(2, end[0]);
    Assert.assertEquals(2, end[1]);
  }

  @Test
  public void testGetOrder() {
    Assert.assertEquals(0, base.getOrder());
    Assert.assertEquals(0, space.getOrder());
  }

  @Test
  public void testNeighbors() {
    List<BaseSpace> neighbors = space.getNeighbors();
    Assert.assertEquals(2, neighbors.size());
    List<BaseSpace> list = new ArrayList<>(neighbors);
    list.add(new BaseSpace(0, 2, 2, 3, 3, "b3"));
    space.setNeighbors(list);
    Assert.assertEquals(3, space.getNeighbors().size());
    Assert.assertEquals("b3", space.getNeighbors().get(2).getName());
  }

  @Test
  public void testWeapons() {
    List<Weapon> weapons = space.getWeapons();
    Assert.assertEquals(2, weapons.size());
    List<Weapon> list = new ArrayList<>(weapons);
    list.remove(1);
    space.setWeapons(list);
    Assert.assertEquals(1, space.getWeapons().size());
    Assert.assertEquals("weapon0", space.getWeapons().get(0).getName());
  }

}
