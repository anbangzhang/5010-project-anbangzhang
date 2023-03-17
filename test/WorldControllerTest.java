import static org.mockito.ArgumentMatchers.eq;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import controller.WorldController;
import controller.impl.WorldConsoleController;
import world.base.BaseWeapon;
import world.context.Context;
import world.context.ContextBuilder;
import world.context.ContextHolder;
import world.enums.PlayerType;
import world.impl.World;
import world.model.Player;
import world.model.Space;

/**
 * Test class for WorldController.
 * 
 * @author anbang
 * @date 2023-02-13 04:48
 */
public class WorldControllerTest {

  private Context context;

  private WorldController controller;

  @Mock
  private Player mockPlayer1;

  @Mock
  private Player mockPlayer2;

  /**
   * Set up world and mocks.
   */
  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
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
  public void testWithConcreteModel() {
    StringReader input = new StringReader(
        "1\nPlayer1\n6\n5\n2\nPlayer2\n3\n4\nq\n1\n2\n6\n3\nres/\n4\n3\n2\nTrowel\n"
            + "1\nHedge Maze\n");
    StringBuilder log = new StringBuilder();
    controller = new WorldConsoleController(input, log, 3);
    controller.playGame(context);
    Assert.assertEquals("Please input the type of player to create:\n" + "\t1. human-controlled\n"
        + "\t2. computer-controlled\n" + "\tq. quit creating\n"
        + "Please input the name of player:\n"
        + "Please input the space index that the player created at:\n"
        + "Please input the weapon limit of player, -1 indicates no limit:\n"
        + "Add player [Player1] succeed.\n" + "Please input the type of player to create:\n"
        + "\t1. human-controlled\n" + "\t2. computer-controlled\n" + "\tq. quit creating\n"
        + "Please input the name of player:\n"
        + "Please input the space index that the player created at:\n"
        + "Please input the weapon limit of player, -1 indicates no limit:\n"
        + "Add player [Player2] succeed.\n" + "Please input the type of player to create:\n"
        + "\t1. human-controlled\n" + "\t2. computer-controlled\n" + "\tq. quit creating\n"
        + "All the players in the game: [Player1, Player2]\n"
        + "Please input the number below to select the function:\n" + "\t1. displayAllSpaces\n"
        + "\t2. displaySpaceDetail\n" + "\t3. displayGraphicalImage\n" + "\t4. startGame\n"
        + "\tq. exit\n"
        + "The spaces: [Armory, Billiard Room, Carriage House, Dining Hall, Drawing Room, Foyer,"
        + " Green House, Hedge Maze, Kitchen, Lancaster Room, Library, Lilac Room, Master Suite, "
        + "Nursery, Parlor, Piazza, Servants' Quarters, Tennessee Room, Trophy Room, Wine Cellar, "
        + "Winter Garden]\n" + "Please input the number below to select the function:\n"
        + "\t1. displayAllSpaces\n" + "\t2. displaySpaceDetail\n" + "\t3. displayGraphicalImage\n"
        + "\t4. startGame\n" + "\tq. exit\n" + "Please input the space index:\n"
        + "Space: [Green House], neighbors: [Hedge Maze], weapons: [Trowel, Pinking Shears], "
        + "occupies: [Player1]\n" + "Please input the number below to select the function:\n"
        + "\t1. displayAllSpaces\n" + "\t2. displaySpaceDetail\n" + "\t3. displayGraphicalImage\n"
        + "\t4. startGame\n" + "\tq. exit\n" + "Please input the output directory:\n"
        + "Graphical Image generation succeed, please check res/ directory\n"
        + "Please input the number below to select the function:\n" + "\t1. displayAllSpaces\n"
        + "\t2. displaySpaceDetail\n" + "\t3. displayGraphicalImage\n" + "\t4. startGame\n"
        + "\tq. exit\n" + "\n" + "Game start.\n" + "\n" + "This is the 1th turn of game.\n"
        + "This is the 1th turn for player [Player1].\n"
        + "Player: [Player1] is in space: [Green House], carrying weapons: []\n"
        + "Please use the number below to select thea template for player [Player1]\n"
        + "\t1. move to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n"
        + "Player: [Player1] is in space: [Green House], players inside this space: [Player1], "
        + "its neighbors: [Hedge Maze], weapons inside this space: [Trowel, Pinking Shears]\n"
        + "This is the 1th turn for player [Player2].\n"
        + "Player: [Player2] is in space: [Dining Hall], carrying weapons: []\n"
        + "Player: [Player2] is in space: [Dining Hall], players inside this space: [Player2], "
        + "its neighbors: [Armory, Billiard Room, Drawing Room, Kitchen, Parlor, Tennessee Room, "
        + "Trophy Room, Wine Cellar], weapons inside this space: []\n" + "\n"
        + "This is the 2th turn of game.\n" + "This is the 2th turn for player [Player1].\n"
        + "Player: [Player1] is in space: [Green House], carrying weapons: []\n"
        + "Please use the number below to select thea template for player [Player1]\n"
        + "\t1. move to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n"
        + "Please input a weapon name from the weapons: [Trowel, Pinking Shears]\n"
        + "Player [Player1] pick up weapon [Trowel] succeed.\n"
        + "This is the 2th turn for player [Player2].\n"
        + "Player: [Player2] is in space: [Dining Hall], carrying weapons: []\n"
        + "Player: [Player2] is in space: [Dining Hall], players inside this space: [Player2], "
        + "its neighbors: [Armory, Billiard Room, Drawing Room, Kitchen, Parlor, Tennessee Room, "
        + "Trophy Room, Wine Cellar], weapons inside this space: []\n" + "\n"
        + "This is the 3th turn of game.\n" + "This is the 3th turn for player [Player1].\n"
        + "Player: [Player1] is in space: [Green House], carrying weapons: [Trowel]\n"
        + "Please use the number below to select thea template for player [Player1]\n"
        + "\t1. move to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n"
        + "Please input a neighbor space name from the neighbors: [Hedge Maze]\n"
        + "Player [Player1] move to space [Hedge Maze] succeed.\n"
        + "This is the 3th turn for player [Player2].\n"
        + "Player: [Player2] is in space: [Dining Hall], carrying weapons: []\n"
        + "Player: [Player2] is in space: [Dining Hall], players inside this space: [Player2], "
        + "its neighbors: [Armory, Billiard Room, Drawing Room, Kitchen, Parlor, Tennessee Room, "
        + "Trophy Room, Wine Cellar], weapons inside this space: []\n" + "\n" + "Game end.\n"
        + "Player: [Player1] is in space: [Hedge Maze], carrying weapons: [Trowel]\n"
        + "Player: [Player2] is in space: [Dining Hall], carrying weapons: []\n", log.toString());
  }

  @Test
  public void testWithMockModel() {
    Space space0 = Mockito.mock(Space.class);
    Space space1 = Mockito.mock(Space.class);
    BaseWeapon weapon = Mockito.mock(BaseWeapon.class);
    MockedStatic<World> mockStatic = Mockito.mockStatic(World.class);

    mockStatic.when(() -> World.addPlayer(Mockito.any(Context.class), Mockito.any(Player.class)))
        .thenReturn(true, false, true);
    mockStatic.when(() -> World.getAllPlayers(Mockito.any(Context.class)))
        .thenReturn(Arrays.asList(mockPlayer1, mockPlayer2));
    mockStatic.when(() -> World.getAllSpaces(Mockito.any(Context.class)))
        .thenReturn(Arrays.asList("Space0", "Space1", "Space2"));
    mockStatic.when(() -> World.getSpace(Mockito.any(Context.class), eq(0))).thenReturn(space0);
    mockStatic.when(() -> World.getSpace(Mockito.any(Context.class), eq(1))).thenReturn(space1);
    mockStatic.when(() -> World.getSpace(Mockito.any(Context.class), eq("Space1")))
        .thenReturn(space1);
    Mockito.when(space0.getName()).thenReturn("Space0");
    Mockito.when(space0.getNeighbors()).thenReturn(Arrays.asList(space1));
    Mockito.when(space0.getWeapons()).thenReturn(Arrays.asList(weapon));
    Mockito.when(space0.getOccupiers()).thenReturn(Arrays.asList(mockPlayer1));
    Mockito.when(space1.getName()).thenReturn("Space1");
    Mockito.when(space1.getNeighbors()).thenReturn(Arrays.asList(space0));
    Mockito.when(space1.getOccupiers()).thenReturn(Arrays.asList(mockPlayer2),
        Arrays.asList(mockPlayer2), Arrays.asList(mockPlayer2),
        Arrays.asList(mockPlayer1, mockPlayer2), Arrays.asList(mockPlayer1, mockPlayer2));
    Mockito.when(mockPlayer1.getName()).thenReturn("A");
    Mockito.when(mockPlayer1.getType()).thenReturn(PlayerType.HUMAN_CONTROLLED);
    Mockito.when(mockPlayer1.getSpaceIndex()).thenReturn(0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1);
    Mockito.when(mockPlayer1.getWeapons()).thenReturn(new ArrayList<>(), new ArrayList<>(),
        new ArrayList<>(), new ArrayList<>(), Arrays.asList(weapon));
    Mockito.when(mockPlayer2.getName()).thenReturn("B");
    Mockito.when(mockPlayer2.getType()).thenReturn(PlayerType.COMPUTER_CONTROLLED);
    Mockito.when(mockPlayer2.getSpaceIndex()).thenReturn(1);
    Mockito.when(weapon.getName()).thenReturn("Weapon");

    StringReader input = new StringReader(
        "a\n1\nA\n0\n5\n2\nA\n2\n4\n2\nB\n1\n4\nq\n1\n2\n6\n3\nres/\n4\n3\n2\nTrowel\n"
            + "2\nWeapon\n1\nSpace2\n1\nSpace1\n");
    StringBuilder log = new StringBuilder();
    controller = new WorldConsoleController(input, log, 3);
    controller.playGame(context);

    mockStatic.close();
    Assert.assertEquals("Please input the type of player to create:\n" + "\t1. human-controlled\n"
        + "\t2. computer-controlled\n" + "\tq. quit creating\n" + "Not a valid number: a\n"
        + "Please input the name of player:\n"
        + "Please input the space index that the player created at:\n"
        + "Please input the weapon limit of player, -1 indicates no limit:\n"
        + "Add player [A] succeed.\n" + "Please input the type of player to create:\n"
        + "\t1. human-controlled\n" + "\t2. computer-controlled\n" + "\tq. quit creating\n"
        + "Please input the name of player:\n"
        + "Please input the space index that the player created at:\n"
        + "Please input the weapon limit of player, -1 indicates no limit:\n"
        + "The name of player is repeated or the space index is invalid.\n"
        + "Please input the type of player to create:\n" + "\t1. human-controlled\n"
        + "\t2. computer-controlled\n" + "\tq. quit creating\n"
        + "Please input the name of player:\n"
        + "Please input the space index that the player created at:\n"
        + "Please input the weapon limit of player, -1 indicates no limit:\n"
        + "Add player [B] succeed.\n" + "Please input the type of player to create:\n"
        + "\t1. human-controlled\n" + "\t2. computer-controlled\n" + "\tq. quit creating\n"
        + "All the players in the game: [A, B]\n"
        + "Please input the number below to select the function:\n" + "\t1. displayAllSpaces\n"
        + "\t2. displaySpaceDetail\n" + "\t3. displayGraphicalImage\n" + "\t4. startGame\n"
        + "\tq. exit\n" + "The spaces: [Space0, Space1, Space2]\n"
        + "Please input the number below to select the function:\n" + "\t1. displayAllSpaces\n"
        + "\t2. displaySpaceDetail\n" + "\t3. displayGraphicalImage\n" + "\t4. startGame\n"
        + "\tq. exit\n" + "Please input the space index:\n" + "The space is null.\n"
        + "Please input the number below to select the function:\n" + "\t1. displayAllSpaces\n"
        + "\t2. displaySpaceDetail\n" + "\t3. displayGraphicalImage\n" + "\t4. startGame\n"
        + "\tq. exit\n" + "Please input the output directory:\n"
        + "Graphical Image generation succeed, please check res/ directory\n"
        + "Please input the number below to select the function:\n" + "\t1. displayAllSpaces\n"
        + "\t2. displaySpaceDetail\n" + "\t3. displayGraphicalImage\n" + "\t4. startGame\n"
        + "\tq. exit\n" + "\n" + "Game start.\n" + "\n" + "This is the 1th turn of game.\n"
        + "This is the 1th turn for player [A].\n"
        + "Player: [A] is in space: [Space0], carrying weapons: []\n"
        + "Please use the number below to select thea template for player [A]\n"
        + "\t1. move to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n"
        + "Player: [A] is in space: [Space0], players inside this space: [A], "
        + "its neighbors: [Space1], weapons inside this space: [Weapon]\n"
        + "This is the 1th turn for player [B].\n"
        + "Player: [B] is in space: [Space1], carrying weapons: []\n"
        + "Player: [B] is in space: [Space1], players inside this space: [B], "
        + "its neighbors: [Space0], weapons inside this space: []\n" + "\n"
        + "This is the 2th turn of game.\n" + "This is the 2th turn for player [A].\n"
        + "Player: [A] is in space: [Space0], carrying weapons: []\n"
        + "Please use the number below to select thea template for player [A]\n"
        + "\t1. move to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n"
        + "Please input a weapon name from the weapons: [Weapon]\n"
        + "Player [A] pick up weapon [Trowel] failed, "
        + "cause: Weapon Trowel is not in current space.\n"
        + "Please use the number below to select thea template for player [A]\n"
        + "\t1. move to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n"
        + "Please input a weapon name from the weapons: [Weapon]\n"
        + "Player [A] pick up weapon [Weapon] succeed.\n" + "This is the 2th turn for player [B].\n"
        + "Player: [B] is in space: [Space1], carrying weapons: []\n"
        + "Player: [B] is in space: [Space1], players inside this space: [B], "
        + "its neighbors: [Space0], weapons inside this space: []\n" + "\n"
        + "This is the 3th turn of game.\n" + "This is the 3th turn for player [A].\n"
        + "Player: [A] is in space: [Space0], carrying weapons: []\n"
        + "Please use the number below to select thea template for player [A]\n"
        + "\t1. move to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n"
        + "Please input a neighbor space name from the neighbors: [Space1]\n"
        + "Player [A] move to space [Space2] failed, "
        + "cause: Space Space2 is not a neighbor of player's current space.\n"
        + "Please use the number below to select thea template for player [A]\n"
        + "\t1. move to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n"
        + "Please input a neighbor space name from the neighbors: [Space1]\n"
        + "Player [A] move to space [Space1] succeed.\n" + "This is the 3th turn for player [B].\n"
        + "Player: [B] is in space: [Space1], carrying weapons: []\n"
        + "Player: [B] is in space: [Space1], players inside this space: [B], "
        + "its neighbors: [Space0], weapons inside this space: []\n" + "\n" + "Game end.\n"
        + "Player: [A] is in space: [Space1], carrying weapons: []\n"
        + "Player: [B] is in space: [Space1], carrying weapons: []\n", log.toString());
  }
}
