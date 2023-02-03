import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import world.model.Space;
import world.model.Target;
import world.World;
import world.base.BaseSpace;
import world.base.BaseWeapon;
import world.impl.WorldImpl;

/**
 * Test class of world.
 * 
 * @author anbang
 * @date 2023-01-29 03:40
 */
public class WorldTest {

  private Target target;

  private List<BaseSpace> baseSpaces;

  private List<BaseWeapon> baseWeapons;

  private World world1;

  private World world2;

  /**
   * Set up the world variables.
   */
  @Before
  public void setUp() {
    target = new Target(50, "Doctor Lucky");
    baseSpaces = initSpaces();
    baseWeapons = initWeapons();
    /* Construct world with java objects */
    world1 = new WorldImpl(36, 30, "Doctor Lucky's Mansion", target, baseSpaces, baseWeapons);
    try {
      /* Construct world with file */
      FileReader fileReader = new FileReader("./res/mansion.txt");
      world2 = new WorldImpl(fileReader);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testConstructFail() {
    try {
      world1 = new WorldImpl(null);
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid input source.", e.getMessage());
    }
    try {
      constructWithInvalidWidth();
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("World's width and length is smaller than 0.", e.getMessage());
    }
    try {
      constructWithInvalidCoordinates();
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("There is a space whose lower right corner coordinates are invalid.",
          e.getMessage());
    }
    try {
      constructWithOverlapSpace();
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("There is a overlap in the spaces.", e.getMessage());
    }
  }

  private void constructWithInvalidWidth() {
    try {
      /* Invalid width. */
      FileReader fileReader = new FileReader("./res/invalid width.txt");
      world1 = new WorldImpl(fileReader);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void constructWithInvalidCoordinates() {
    try {
      /* There is a space whose lower right corner coordinates are invalid. */
      FileReader fileReader = new FileReader("./res/invalid coordinates.txt");
      world1 = new WorldImpl(fileReader);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void constructWithOverlapSpace() {
    try {
      /* There is a overlap in the spaces. */
      FileReader fileReader = new FileReader("./res/overlap space.txt");
      world1 = new WorldImpl(fileReader);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetTargetPosition() {
    /* Get the space that target is in */
    Assert.assertEquals(0, world1.getTargetPosition().getOrder());
    Assert.assertEquals(baseSpaces.get(0).getName(), world1.getTargetPosition().getName());

    /* Get the space that target is in */
    Assert.assertEquals(0, world2.getTargetPosition().getOrder());
    Assert.assertEquals(baseSpaces.get(0).getName(), world2.getTargetPosition().getName());
  }

  @Test
  public void testMoveTarget() {
    int n = baseSpaces.size();
    for (int i = 0; i < n - 1; i++) {
      /* Target move from i to i + 1 space */
      Space space = world1.moveTarget();
      Assert.assertEquals(i + 1, space.getOrder());
      Assert.assertEquals(baseSpaces.get(i + 1).getName(), space.getName());

      /* Target move from i to i + 1 space */
      space = world2.moveTarget();
      Assert.assertEquals(i + 1, space.getOrder());
      Assert.assertEquals(baseSpaces.get(i + 1).getName(), space.getName());
    }

    /* Target move from the last space to 0th space */
    Space space = world1.moveTarget();
    Assert.assertEquals(0, space.getOrder());
    Assert.assertEquals(baseSpaces.get(0).getName(), space.getName());

    /* Target move from the last space to 0th space */
    world2.moveTarget();
    Assert.assertEquals(0, space.getOrder());
    Assert.assertEquals(baseSpaces.get(0).getName(), space.getName());
  }

  @Test
  public void testShowGraphicalImage() {
    try {
      world2.showGraphicalImage("res/");
      File file = new File("res/Doctor Lucky's Mansion.png");
      Assert.assertTrue(file.exists());
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testGetSpace() {
    for (int i = 0; i < baseSpaces.size(); i++) {
      /* Get the space with valid space name */
      Assert.assertEquals(baseSpaces.get(i).getName(), world1.getSpace(i).getName());
      Assert.assertEquals(baseSpaces.get(i).getName(),
          world1.getSpace(baseSpaces.get(i).getName()).getName());

      /* Get the space with valid index */
      Assert.assertEquals(baseSpaces.get(i).getName(), world2.getSpace(i).getName());
      Assert.assertEquals(baseSpaces.get(i).getName(),
          world2.getSpace(baseSpaces.get(i).getName()).getName());

      if (baseSpaces.get(i).getName().equals("Kitchen")) {
        Space space = world1.getSpace("Kitchen");
        /* Check weapons */
        Assert.assertEquals(initWeaponsForKitchen(),
            space.getWeapons().stream().map(BaseWeapon::getName).collect(Collectors.toList()));
        /* Check neighbors */
        Assert.assertEquals(initNeighborsForKitchen(),
            space.getNeighbors().stream().map(BaseSpace::getName).collect(Collectors.toList()));

        space = world1.getSpace(i);
        /* Check weapons */
        Assert.assertEquals(initWeaponsForKitchen(),
            space.getWeapons().stream().map(BaseWeapon::getName).collect(Collectors.toList()));
        /* Check neighbors */
        Assert.assertEquals(initNeighborsForKitchen(),
            space.getNeighbors().stream().map(BaseSpace::getName).collect(Collectors.toList()));

        space = world2.getSpace("Kitchen");
        /* Check weapons */
        Assert.assertEquals(initWeaponsForKitchen(),
            space.getWeapons().stream().map(BaseWeapon::getName).collect(Collectors.toList()));
        /* Check neighbors */
        Assert.assertEquals(initNeighborsForKitchen(),
            space.getNeighbors().stream().map(BaseSpace::getName).collect(Collectors.toList()));

        space = world2.getSpace(i);
        /* Check weapons */
        Assert.assertEquals(initWeaponsForKitchen(),
            space.getWeapons().stream().map(BaseWeapon::getName).collect(Collectors.toList()));
        /* Check neighbors */
        Assert.assertEquals(initNeighborsForKitchen(),
            space.getNeighbors().stream().map(BaseSpace::getName).collect(Collectors.toList()));
      }
    }

    /* Get the space with invalid space name */
    Assert.assertNull(world1.getSpace("test"));
    Assert.assertNull(world2.getSpace("test"));

    /* Get the space with invalid index */
    Assert.assertNull(world1.getSpace(baseSpaces.size() + 10));
    Assert.assertNull(world2.getSpace(baseSpaces.size() + 10));
  }

  @Test
  public void testGetNeighbors() {
    Assert.assertEquals(new ArrayList<>(), world2.getNeighbors("null"));
    Assert.assertEquals(initNeighborsForDiningHall(), world2.getNeighbors("Dining Hall"));
    Assert.assertEquals(initNeighborsForKitchen(), world2.getNeighbors("Kitchen"));

  }

  private List<String> initNeighborsForDiningHall() {
    List<String> neighbors = new ArrayList<>();
    neighbors.add("Armory");
    neighbors.add("Billiard Room");
    neighbors.add("Drawing Room");
    neighbors.add("Kitchen");
    neighbors.add("Parlor");
    neighbors.add("Tennessee Room");
    neighbors.add("Trophy Room");
    neighbors.add("Wine Cellar");
    return neighbors;
  }

  private List<String> initNeighborsForKitchen() {
    List<String> neighbors = new ArrayList<>();
    neighbors.add("Dining Hall");
    neighbors.add("Parlor");
    neighbors.add("Wine Cellar");
    return neighbors;
  }

  private List<String> initWeaponsForKitchen() {
    List<String> weapons = new ArrayList<>();
    weapons.add("Crepe Pan");
    weapons.add("Sharp Knife");
    return weapons;
  }

  private List<BaseSpace> initSpaces() {
    List<BaseSpace> result = new ArrayList<>();
    result.add(new BaseSpace(22, 19, 23, 26, 0, "Armory"));
    result.add(new BaseSpace(16, 21, 21, 28, 1, "Billiard Room"));
    result.add(new BaseSpace(28, 0, 35, 5, 2, "Carriage House"));
    result.add(new BaseSpace(12, 11, 21, 20, 3, "Dining Hall"));
    result.add(new BaseSpace(22, 13, 25, 18, 4, "Drawing Room"));
    result.add(new BaseSpace(26, 13, 27, 18, 5, "Foyer"));
    result.add(new BaseSpace(28, 26, 35, 29, 6, "Green House"));
    result.add(new BaseSpace(30, 20, 35, 25, 7, "Hedge Maze"));
    result.add(new BaseSpace(16, 3, 21, 10, 8, "Kitchen"));
    result.add(new BaseSpace(0, 3, 5, 8, 9, "Lancaster Room"));
    result.add(new BaseSpace(4, 23, 9, 28, 10, "Library"));
    result.add(new BaseSpace(2, 9, 7, 14, 11, "Lilac Room"));
    result.add(new BaseSpace(2, 15, 7, 22, 12, "Master Suite"));
    result.add(new BaseSpace(0, 23, 3, 28, 13, "Nursery"));
    result.add(new BaseSpace(10, 5, 15, 10, 14, "Parlor"));
    result.add(new BaseSpace(28, 12, 35, 19, 15, "Piazza"));
    result.add(new BaseSpace(6, 3, 9, 8, 16, "Servants' Quarters"));
    result.add(new BaseSpace(8, 11, 11, 20, 17, "Tennessee Room"));
    result.add(new BaseSpace(10, 21, 15, 26, 18, "Trophy Room"));
    result.add(new BaseSpace(22, 5, 23, 12, 19, "Wine Cellar"));
    result.add(new BaseSpace(30, 6, 35, 11, 20, "Winter Garden"));
    return result;
  }

  private List<BaseWeapon> initWeapons() {
    List<BaseWeapon> result = new ArrayList<>();
    result.add(new BaseWeapon(8, 3, "Crepe Pan"));
    result.add(new BaseWeapon(4, 2, "Letter Opener"));
    result.add(new BaseWeapon(12, 2, "Shoe Horn"));
    result.add(new BaseWeapon(8, 3, "Sharp Knife"));
    result.add(new BaseWeapon(0, 3, "Revolver"));
    result.add(new BaseWeapon(15, 3, "Civil War Cannon"));
    result.add(new BaseWeapon(2, 4, "Chain Saw"));
    result.add(new BaseWeapon(16, 2, "Broom Stick"));
    result.add(new BaseWeapon(1, 2, "Billiard Cue"));
    result.add(new BaseWeapon(19, 2, "Rat Poison"));
    result.add(new BaseWeapon(6, 2, "Trowel"));
    result.add(new BaseWeapon(2, 4, "Big Red Hammer"));
    result.add(new BaseWeapon(6, 2, "Pinking Shears"));
    result.add(new BaseWeapon(18, 3, "Duck Decoy"));
    result.add(new BaseWeapon(13, 2, "Bad Cream"));
    result.add(new BaseWeapon(18, 2, "Monkey Hand"));
    result.add(new BaseWeapon(11, 2, "Tight Hat"));
    result.add(new BaseWeapon(19, 2, "Piece of Rope"));
    result.add(new BaseWeapon(9, 3, "Silken Cord"));
    result.add(new BaseWeapon(7, 2, "Loud Noise"));
    return result;
  }
}
