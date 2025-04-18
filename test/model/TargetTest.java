package model;

import model.model.Target;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for Target.
 * 
 * @author anbang
 * @date 2023-01-30 21:41
 */
public class TargetTest {

  private Target target;

  /**
   * Set up target.
   */
  @Before
  public void setUp() {
    target = new Target(10, "target");
  }

  @Test
  public void testInitFail() {
    try {
      target = new Target(0, "fail");
    } catch (IllegalArgumentException e) {
      Assert.assertNull(e.getMessage());
    }
  }

  @Test
  public void testGetName() {
    Assert.assertEquals("target", target.getName());
  }

  @Test
  public void testHealth() {
    Assert.assertEquals(10, target.getHealth());
    Assert.assertEquals(9, target.decreaseHealth(1));
    Assert.assertEquals(0, target.decreaseHealth(11));
  }

  @Test
  public void testPosition() {
    Assert.assertEquals(0, target.getPosition());
    target.setPosition(10);
    Assert.assertEquals(10, target.getPosition());
  }
}
