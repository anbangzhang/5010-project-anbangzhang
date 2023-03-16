import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import world.base.BaseWeapon;

/**
 * Test class for BaseWeapon and Weapon.
 * 
 * @author anbang
 * @date 2023-01-30 18:44
 */
public class WeaponTest {

  private BaseWeapon base;

  /**
   * Set up baseWeapon and weapon.
   */
  @Before
  public void setUp() {
    base = new BaseWeapon(0, 10, "baseWeapon");
  }

  @Test
  public void testInitFail() {
    try {
      base = new BaseWeapon(-5, 10, "baseWeapon");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Space index or damage is smaller than 0.", e.getMessage());
    }
    try {
      base = new BaseWeapon(0, -5, "baseWeapon");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Space index or damage is smaller than 0.", e.getMessage());
    }
  }

  @Test
  public void testGetName() {
    Assert.assertEquals("baseWeapon", base.getName());
  }

  @Test
  public void testGetSpaceIndex() {
    Assert.assertEquals(0, base.getSpaceIndex());
  }

  @Test
  public void testGetDamage() {
    Assert.assertEquals(10, base.getDamage());
  }
}
