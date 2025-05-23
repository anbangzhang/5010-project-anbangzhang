package model;

import model.base.BasePet;
import model.model.Pet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * PetTest.
 * 
 * @author anbang
 * @date 2023-03-23 04:30
 */
public class PetTest {

  private Pet pet;

  @Before
  public void setUp() {
    pet = new BasePet("pet");
  }

  @Test
  public void testGetName() {
    Assert.assertEquals("pet", pet.getName());
  }

  @Test
  public void testSpaceIndex() {
    Assert.assertEquals(0, pet.getSpaceIndex());

    pet.setSpaceIndex(10);
    Assert.assertEquals(10, pet.getSpaceIndex());
  }
}
