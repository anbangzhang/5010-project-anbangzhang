import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import world.Weapon;
import world.base.BaseSpace;
import world.base.BaseWeapon;

/**
 * Test class for BaseWeapon and Weapon.
 * 
 * @author anbang
 * @date 2023-01-30 18:44
 */
public class WeaponTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private BaseWeapon base;

  private Weapon weapon;

  @Before
  public void setUp() {
    base = new BaseWeapon(0, 10, "baseWeapon");
    weapon = new Weapon(base, new BaseSpace(0, 0, 2, 2, 5, "baseSpace"));
  }

  @Test
  public void testInitFail() {
    thrown.expect(IllegalArgumentException.class);
    base = new BaseWeapon(-5, 10, "baseWeapon");
    thrown.expect(IllegalArgumentException.class);
    base = new BaseWeapon(0, -5, "baseWeapon");
  }

  @Test
  public void testGetName() {
    Assert.assertEquals("baseWeapon", base.getName());
    Assert.assertEquals("baseWeapon", weapon.getName());
  }

  @Test
  public void testGetSpaceIndex() {
    Assert.assertEquals(0, base.getSpaceIndex());
    Assert.assertEquals(0, weapon.getSpaceIndex());
  }

  @Test
  public void testGetDamage() {
    Assert.assertEquals(10, base.getDamage());
    Assert.assertEquals(10, weapon.getDamage());
  }

  @Test
  public void testGetBelongTo() {
    Assert.assertEquals("baseSpace", weapon.getBelongTo().getName());
  }
}
