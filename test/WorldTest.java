import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import world.World;
import world.base.BasePlayer;
import world.base.BaseSpace;
import world.base.BaseWeapon;
import world.context.Context;
import world.context.ContextBuilder;
import world.context.ContextHolder;
import world.enums.PlayerType;
import world.exception.BusinessException;
import world.model.Player;
import world.model.Space;

/**
 * Test class of world.
 *
 * @author anbang
 * @date 2023-01-29 03:40
 */
public class WorldTest {

  private List<BaseSpace> baseSpaces;

  private Context context;

  /**
   * Set up the world variables.
   */
  @Before
  public void setUp() {
    baseSpaces = initSpaces();
    try {
      /* Construct world with file */
      Readable fileReader = new FileReader("./res/world specification/mansion.txt");
      context = ContextBuilder.build(fileReader);
      ContextHolder.set(context);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testConstructFail() {
    try {
      Context ctx = ContextBuilder.build(null);
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
    try {
      constructWithInvalidWeapon();
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Space index or damage is smaller than 0.", e.getMessage());
    }
  }

  private void constructWithInvalidWidth() {
    try {
      /* Invalid width. */
      Readable fileReader = new FileReader("./res/world specification/invalid width.txt");
      context = ContextBuilder.build(fileReader);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void constructWithInvalidCoordinates() {
    try {
      /* There is a space whose lower right corner coordinates are invalid. */
      Readable fileReader = new FileReader("./res/world specification/invalid coordinates.txt");
      context = ContextBuilder.build(fileReader);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void constructWithOverlapSpace() {
    try {
      /* There is a overlap in the spaces. */
      Readable fileReader = new FileReader("./res/world specification/overlap space.txt");
      context = ContextBuilder.build(fileReader);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void constructWithInvalidWeapon() {
    try {
      /* There is a overlap in the spaces. */
      Readable fileReader = new FileReader("./res/world specification/invalid weapon.txt");
      context = ContextBuilder.build(fileReader);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetAllSpaces() {
    Assert.assertEquals(baseSpaces.stream().map(BaseSpace::getName).collect(Collectors.toList()),
        World.getAllSpaces(context));
    Assert.assertEquals(baseSpaces.stream().map(BaseSpace::getName).collect(Collectors.toList()),
        World.getAllSpaces(context));
  }

  @Test
  public void testGetTargetPosition() {
    /* Get the space that target is in */
    Assert.assertEquals(0, World.getTargetPosition(context).getOrder());
    Assert.assertEquals(baseSpaces.get(0).getName(), World.getTargetPosition(context).getName());

    /* Get the space that target is in */
    Assert.assertEquals(0, World.getTargetPosition(context).getOrder());
    Assert.assertEquals(baseSpaces.get(0).getName(), World.getTargetPosition(context).getName());
  }

  @Test
  public void testMoveTarget() {
    int n = baseSpaces.size();
    for (int i = 0; i < n - 1; i++) {
      /* Target move from i to i + 1 space */
      Space space = World.moveTarget(context);
      Assert.assertEquals(i + 1, space.getOrder());
      Assert.assertEquals(baseSpaces.get(i + 1).getName(), space.getName());
    }

    /* Target move from the last space to 0th space */
    Space space = World.moveTarget(context);
    Assert.assertEquals(0, space.getOrder());
    Assert.assertEquals(baseSpaces.get(0).getName(), space.getName());
  }

  @Test
  public void testShowGraphicalImage() {
    try {
      World.showGraphicalImage(context, "res/");
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
      Assert.assertEquals(baseSpaces.get(i).getName(), World.getSpace(context, i).getName());
      Assert.assertEquals(baseSpaces.get(i).getName(),
          World.getSpace(context, baseSpaces.get(i).getName()).getName());

      /* Get the space with valid index */
      Assert.assertEquals(baseSpaces.get(i).getName(), World.getSpace(context, i).getName());
      Assert.assertEquals(baseSpaces.get(i).getName(),
          World.getSpace(context, baseSpaces.get(i).getName()).getName());

      if (baseSpaces.get(i).getName().equals("Kitchen")) {
        Space space = World.getSpace(context, "Kitchen");
        /* Check weapons */
        Assert.assertEquals(initWeaponsForKitchen(),
            space.getWeapons().stream().map(BaseWeapon::getName).collect(Collectors.toList()));
        /* Check neighbors */
        Assert.assertEquals(initNeighborsForKitchen(),
            space.getNeighbors().stream().map(Space::getName).collect(Collectors.toList()));

        space = World.getSpace(context, i);
        /* Check weapons */
        Assert.assertEquals(initWeaponsForKitchen(),
            space.getWeapons().stream().map(BaseWeapon::getName).collect(Collectors.toList()));
        /* Check neighbors */
        Assert.assertEquals(initNeighborsForKitchen(),
            space.getNeighbors().stream().map(Space::getName).collect(Collectors.toList()));

        space = World.getSpace(context, "Kitchen");
        /* Check weapons */
        Assert.assertEquals(initWeaponsForKitchen(),
            space.getWeapons().stream().map(BaseWeapon::getName).collect(Collectors.toList()));
        /* Check neighbors */
        Assert.assertEquals(initNeighborsForKitchen(),
            space.getNeighbors().stream().map(Space::getName).collect(Collectors.toList()));

        space = World.getSpace(context, i);
        /* Check weapons */
        Assert.assertEquals(initWeaponsForKitchen(),
            space.getWeapons().stream().map(BaseWeapon::getName).collect(Collectors.toList()));
        /* Check neighbors */
        Assert.assertEquals(initNeighborsForKitchen(),
            space.getNeighbors().stream().map(Space::getName).collect(Collectors.toList()));
      }
    }

    /* No weapon */
    Assert.assertEquals(0, World.getSpace(context, 20).getWeapons().size());

    /* One weapon */
    Assert.assertEquals(1, World.getSpace(context, 0).getWeapons().size());
    Assert.assertEquals(Arrays.asList("Revolver"), World.getSpace(context, 0).getWeapons().stream()
        .map(BaseWeapon::getName).collect(Collectors.toList()));

    /* Get the space with invalid space name */
    Assert.assertNull(World.getSpace(context, "test"));
    Assert.assertNull(World.getSpace(context, "test"));

    /* Get the space with invalid index */
    Assert.assertNull(World.getSpace(context, baseSpaces.size() + 10));
    Assert.assertNull(World.getSpace(context, baseSpaces.size() + 10));
  }

  @Test
  public void testGetNeighbors() {
    Assert.assertEquals(new ArrayList<>(), World.getNeighbors(context, "null"));
    Assert.assertEquals(initNeighborsForDiningHall(), World.getNeighbors(context, "Dining Hall"));
    Assert.assertEquals(initNeighborsForKitchen(), World.getNeighbors(context, "Kitchen"));
    /* 8 is the index of Kitchen */
    Assert.assertEquals(initNeighborsForKitchen(), World.getNeighbors(context, 8));

    Assert.assertEquals(Arrays.asList("Hedge Maze"), World.getNeighbors(context, "Green House"));

    try {
      /* Construct world with file */
      Readable fileReader = new FileReader("./res/world specification/MyWorld.txt");
      context = ContextBuilder.build(fileReader);
      ContextHolder.set(context);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    Assert.assertEquals(new ArrayList<>(), World.getNeighbors(context, "Numenor"));
  }

  @Test
  public void testPlayer() {
    Assert.assertEquals(0, World.getAllPlayers(context).size());
    final Player player0 = new BasePlayer(0, "player0", 6, PlayerType.HUMAN_CONTROLLED, 1);
    final Player player1 = new BasePlayer(1, "player1", 8, PlayerType.COMPUTER_CONTROLLED, null);
    final Player player2 = new BasePlayer(2, "player0", 8, PlayerType.COMPUTER_CONTROLLED, 2);
    final Player player3 = new BasePlayer(2, "player3", 100, PlayerType.HUMAN_CONTROLLED, 2);
    World.addPlayer(context, player0);
    Assert.assertEquals(1, World.getAllPlayers(context).size());

    /* add player with repeated name fail */
    Assert.assertFalse(World.addPlayer(context, player2));
    Assert.assertEquals(1, World.getAllPlayers(context).size());

    /* add player with invalid space index fail */
    Assert.assertFalse(World.addPlayer(context, player3));
    Assert.assertEquals(1, World.getAllPlayers(context).size());

    /* get all players */
    World.addPlayer(context, player1);
    Assert.assertEquals(Arrays.asList("player0", "player1"),
        World.getAllPlayers(context).stream().map(Player::getName).collect(Collectors.toList()));

    /* get player by index */
    Assert.assertEquals(player0, World.getPlayer(context, 0));
    Assert.assertEquals(player1, World.getPlayer(context, 1));

    /* get player with invalid index */
    Assert.assertNull(World.getPlayer(context, 10));

    Assert.assertEquals(6, World.getPlayer(context, 0).getSpaceIndex());
    Assert.assertEquals(8, World.getPlayer(context, 1).getSpaceIndex());

    /* player with weapon limit picks up weapon and reach limit */
    Space space6 = World.getSpace(context, player0.getSpaceIndex());
    while (!space6.getWeapons().isEmpty()) {
      BaseWeapon weapon = space6.getWeapons().get(0);
      try {
        World.pickUp(player0, weapon);
      } catch (BusinessException e) {
        Assert.assertEquals("Player's weapons reach limit.", e.getMessage());
        break;
      }
    }
    Assert.assertEquals(1, player0.getWeapons().size());

    /* player with unlimited weapon limit picks up all the weapons in one space */
    Space space8 = World.getSpace(context, player1.getSpaceIndex());
    while (!space8.getWeapons().isEmpty()) {
      BaseWeapon weapon = space8.getWeapons().get(0);
      try {
        World.pickUp(player1, weapon);
      } catch (BusinessException e) {
        Assert.assertEquals("Player's weapons reach limit.", e.getMessage());
        break;
      }
    }
    Assert.assertEquals(2, player1.getWeapons().size());

    /* move a player */
    Space baseSpace = space6.getNeighbors().get(0);
    try {
      World.movePlayer(player0, World.getSpace(context, baseSpace.getName()));
    } catch (BusinessException e) {
      Assert.assertEquals("Invalid space.", e.getMessage());
    }
    Assert.assertEquals(baseSpace.getOrder(), player0.getSpaceIndex());

    /* move a player to a null space, fail */
    try {
      World.movePlayer(player1, null);
    } catch (BusinessException e) {
      Assert.assertEquals("Invalid space.", e.getMessage());
    }
    Assert.assertEquals(8, player1.getSpaceIndex());
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
