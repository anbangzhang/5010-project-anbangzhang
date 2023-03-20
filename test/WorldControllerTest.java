import static org.mockito.ArgumentMatchers.eq;

import controller.WorldController;
import controller.impl.WorldConsoleController;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import world.World;
import world.base.BaseWeapon;
import world.context.Context;
import world.context.ContextBuilder;
import world.context.ContextHolder;
import world.enums.PlayerType;
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
    controller = new WorldConsoleController();
    controller.setIn(input);
    controller.setOut(log);
    controller.setTurn(3);
    controller.playGame(context);

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
    controller = new WorldConsoleController();
    controller.setIn(input);
    controller.setOut(log);
    controller.setTurn(3);
    controller.playGame(context);

    mockStatic.close();

  }
}
