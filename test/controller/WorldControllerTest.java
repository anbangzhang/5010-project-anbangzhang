package controller;

import static org.mockito.ArgumentMatchers.eq;

import controller.impl.WorldConsoleController;
import flowengine.FlowEngineApplication;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import world.World;
import world.base.BasePet;
import world.base.BasePlayer;
import world.base.BaseSpace;
import world.base.BaseWeapon;
import world.context.Context;
import world.context.ContextBuilder;
import world.context.ContextHolder;
import world.enums.PlayerType;
import world.model.Pet;
import world.model.Player;
import world.model.Target;

/**
 * Test class for WorldController.
 * 
 * @author anbang
 * @date 2023-02-13 04:48
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { FlowEngineApplication.class, ControllerApplication.class })
public class WorldControllerTest {

  private Context context;

  @Autowired
  private WorldConsoleController controller;

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
  public void testWithConcreteModelNoWinner() {
    StringReader input = new StringReader("1\nPlayer0\n0\n2\n2\nPlayer1\n1\n3\nq\n4\n5\n");
    StringBuilder log = new StringBuilder();
    controller.setIn(input);
    controller.setOut(log);
    controller.setTurn(1);
    controller.playGame(context);

    Assert.assertEquals(
        "Please input the type of player to create:\n" + "\t1. human-controlled\n"
            + "\t2. computer-controlled\n" + "\tq. quit creating\n"
            + "Please input the name of player:\n"
            + "Please input the space index that the player created at:\n"
            + "Please input the weapon limit of player, -1 indicates no limit:\n"
            + "Add player [Player0] succeed.\n" + "Please input the type of player to create:\n"
            + "\t1. human-controlled\n" + "\t2. computer-controlled\n" + "\tq. quit creating\n"
            + "Please input the name of player:\n"
            + "Please input the space index that the player created at:\n"
            + "Please input the weapon limit of player, -1 indicates no limit:\n"
            + "Add player [Player1] succeed.\n" + "Please input the type of player to create:\n"
            + "\t1. human-controlled\n" + "\t2. computer-controlled\n" + "\tq. quit creating\n"
            + "All the players in the game: [Player0, Player1]\n"
            + "Please input the number below to select the function:\n" + "\t1. displayAllSpaces\n"
            + "\t2. displaySpaceDetail\n" + "\t3. generateGraphicalImage\n" + "\t4. startGame\n"
            + "\tq. exit\n" + "\n" + "Game start.\n" + "\n"
            + "This is the 1th turn of game. The target is in space: [Armory] health: [3],"
            + " the pet is in space: [Armory], evidence: []\n" + "\n"
            + "This is the 1th turn for player [Player0].\n"
            + "Player: [Player0] is in space: [Armory], carrying weapons: []\n"
            + "Please use the number below to select the action for player [Player0]\n"
            + "\t1. move player to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
            + "\t3. look around the space.\n" + "\t4. move pet to a new space.\n"
            + "\t5. attack target.\n" + "Player: [Player0] choose to ATTACK_TARGET.\n"
            + "Attack succeed.\n" + "\n" + "This is the 1th turn for player [Player1].\n"
            + "Player: [Player1] is in space: [Billiard Room], carrying weapons: []\n"
            + "Player: [Player1] choose to PICK_UP_WEAPON.\n" + "Player pick up weapon succeed.\n"
            + "\n" + "Game end.\n"
            + "The target escaped with health: [2]. The pet is in space: [Drawing Room]. No winner."
            + " Evidences: []\n" + "Player: [Player0] is in space: [Armory], carrying weapons: []\n"
            + "Player: [Player1] is in space: [Billiard Room], carrying weapons: [Billiard Cue]\n",
        log.toString());
  }

  @Test
  public void testWithConcreteModelNoWinnerAndAttackIsSeen() {
    StringReader input = new StringReader(
        "1\nPlayer0\n0\n2\n2\nPlayer1\n1\n3\n1\nPlayer2\n0\n1\nq\n4\n5\n2\n"
            + "Revolver\n3\n1\nDining Hall\n4\nDining Hall\n3\n");
    StringBuilder log = new StringBuilder();
    controller.setIn(input);
    controller.setOut(log);
    controller.setTurn(3);
    controller.playGame(context);

    Assert.assertEquals("Please input the type of player to create:\n" + "\t1. human-controlled\n"
        + "\t2. computer-controlled\n" + "\tq. quit creating\n"
        + "Please input the name of player:\n"
        + "Please input the space index that the player created at:\n"
        + "Please input the weapon limit of player, -1 indicates no limit:\n"
        + "Add player [Player0] succeed.\n" + "Please input the type of player to create:\n"
        + "\t1. human-controlled\n" + "\t2. computer-controlled\n" + "\tq. quit creating\n"
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
        + "All the players in the game: [Player0, Player1, Player2]\n"
        + "Please input the number below to select the function:\n" + "\t1. displayAllSpaces\n"
        + "\t2. displaySpaceDetail\n" + "\t3. generateGraphicalImage\n" + "\t4. startGame\n"
        + "\tq. exit\n" + "\n" + "Game start.\n" + "\n"
        + "This is the 1th turn of game. The target is in space: [Armory] health: [3], "
        + "the pet is in space: [Armory], evidence: []\n" + "\n"
        + "This is the 1th turn for player [Player0].\n"
        + "Player: [Player0] is in space: [Armory], carrying weapons: []\n"
        + "Please use the number below to select the action for player [Player0]\n"
        + "\t1. move player to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n" + "\t4. move pet to a new space.\n"
        + "\t5. attack target.\n" + "Player: [Player0] choose to ATTACK_TARGET.\n"
        + "Attack failed, the attack is seen.\n" + "\n"
        + "This is the 1th turn for player [Player1].\n"
        + "Player: [Player1] is in space: [Billiard Room], carrying weapons: []\n"
        + "Player: [Player1] choose to PICK_UP_WEAPON.\n" + "Player pick up weapon succeed.\n"
        + "\n" + "This is the 1th turn for player [Player2].\n"
        + "Player: [Player2] is in space: [Armory], carrying weapons: []\n"
        + "Please use the number below to select the action for player [Player2]\n"
        + "\t1. move player to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n" + "\t4. move pet to a new space.\n"
        + "\t5. attack target.\n" + "Player: [Player2] choose to PICK_UP_WEAPON.\n"
        + "Please input a weapon name from the weapons: [Revolver]\n"
        + "Player pick up weapon succeed.\n" + "\n"
        + "This is the 2th turn of game. The target is in space: [Billiard Room] health: [3], "
        + "the pet is in space: [Drawing Room], evidence: []\n" + "\n"
        + "This is the 2th turn for player [Player0].\n"
        + "Player: [Player0] is in space: [Armory], carrying weapons: []\n"
        + "Please use the number below to select the action for player [Player0]\n"
        + "\t1. move player to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n" + "\t4. move pet to a new space.\n"
        + "\t5. attack target.\n" + "Player: [Player0] choose to LOOK_AROUND.\n"
        + "Space: [Armory], occupiers: [Player0, Player2], weapons: []\n" + "Neighbors: [\n"
        + "Space: [Billiard Room], occupiers: [Player1], weapons: [], Target: [Doctor Lucky]\n"
        + "Space: [Dining Hall], occupiers: [], weapons: []\n"
        + "Space: [Drawing Room], Pet: [Fortune the Cat]]\n" + "\n"
        + "This is the 2th turn for player [Player1].\n"
        + "Player: [Player1] is in space: [Billiard Room], carrying weapons: [Billiard Cue]\n"
        + "Player: [Player1] choose to ATTACK_TARGET.\n" + "Attack failed, the attack is seen.\n"
        + "\n" + "This is the 2th turn for player [Player2].\n"
        + "Player: [Player2] is in space: [Armory], carrying weapons: [Revolver]\n"
        + "Please use the number below to select the action for player [Player2]\n"
        + "\t1. move player to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n" + "\t4. move pet to a new space.\n"
        + "\t5. attack target.\n" + "Player: [Player2] choose to MOVE_PLAYER.\n"
        + "Please input a neighbor space name from the neighbors: [Billiard Room, Dining Hall, "
        + "Drawing Room]\n" + "Player move to the neighbor space succeed.\n" + "\n"
        + "This is the 3th turn of game. The target is in space: [Carriage House] health: [3], "
        + "the pet is in space: [Wine Cellar], evidence: [Billiard Cue]\n" + "\n"
        + "This is the 3th turn for player [Player0].\n"
        + "Player: [Player0] is in space: [Armory], carrying weapons: []\n"
        + "Please use the number below to select the action for player [Player0]\n"
        + "\t1. move player to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n" + "\t4. move pet to a new space.\n"
        + "\t5. attack target.\n" + "Player: [Player0] choose to MOVE_PET.\n"
        + "Please input a space name from the spaces: [Armory, Billiard Room, Carriage House, "
        + "Dining Hall, Drawing Room, Foyer, Green House, Hedge Maze, Kitchen, Lancaster Room, "
        + "Library, Lilac Room, Master Suite, Nursery, Parlor, Piazza, Servants' Quarters, "
        + "Tennessee Room, Trophy Room, Wine Cellar, Winter Garden]\n"
        + "Player move the pet succeed.\n" + "\n" + "This is the 3th turn for player [Player1].\n"
        + "Player: [Player1] is in space: [Billiard Room], carrying weapons: []\n"
        + "Player: [Player1] choose to LOOK_AROUND.\n"
        + "Space: [Billiard Room], occupiers: [Player1], weapons: []\n" + "Neighbors: [\n"
        + "Space: [Armory], occupiers: [Player0], weapons: []\n"
        + "Space: [Dining Hall], Pet: [Fortune the Cat]\n"
        + "Space: [Trophy Room], occupiers: [], weapons: [Duck Decoy, Monkey Hand]]\n" + "\n"
        + "This is the 3th turn for player [Player2].\n"
        + "Player: [Player2] is in space: [Dining Hall], carrying weapons: [Revolver]\n"
        + "Please use the number below to select the action for player [Player2]\n"
        + "\t1. move player to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n" + "\t4. move pet to a new space.\n"
        + "\t5. attack target.\n" + "Player: [Player2] choose to LOOK_AROUND.\n"
        + "Space: [Dining Hall], occupiers: [Player2], weapons: [], Pet: [Fortune the Cat]\n"
        + "Neighbors: [\n" + "Space: [Armory], occupiers: [Player0], weapons: []\n"
        + "Space: [Billiard Room], occupiers: [Player1], weapons: []\n"
        + "Space: [Drawing Room], occupiers: [], weapons: [Letter Opener]\n"
        + "Space: [Kitchen], occupiers: [], weapons: [Crepe Pan, Sharp Knife]\n"
        + "Space: [Parlor], occupiers: [], weapons: []\n"
        + "Space: [Tennessee Room], occupiers: [], weapons: []\n"
        + "Space: [Trophy Room], occupiers: [], weapons: [Duck Decoy, Monkey Hand]\n"
        + "Space: [Wine Cellar], occupiers: [], weapons: [Rat Poison, Piece of Rope]]\n" + "\n"
        + "Game end.\n"
        + "The target escaped with health: [3]. The pet is in space: [Trophy Room]. No winner. "
        + "Evidences: [Billiard Cue]\n"
        + "Player: [Player0] is in space: [Armory], carrying weapons: []\n"
        + "Player: [Player1] is in space: [Billiard Room], carrying weapons: []\n"
        + "Player: [Player2] is in space: [Dining Hall], carrying weapons: [Revolver]\n",
        log.toString());
  }

  @Test
  public void testWithConcreteModelHumanPlayerWin() {
    StringReader input = new StringReader(
        "1\nPlayer0\n1\n2\n2\nPlayer1\n0\n3\nq\n4\n2\nBilliard Cue\n5\n");
    StringBuilder log = new StringBuilder();
    controller.setIn(input);
    controller.setOut(log);
    controller.setTurn(2);
    controller.playGame(context);

    Assert.assertEquals("Please input the type of player to create:\n" + "\t1. human-controlled\n"
        + "\t2. computer-controlled\n" + "\tq. quit creating\n"
        + "Please input the name of player:\n"
        + "Please input the space index that the player created at:\n"
        + "Please input the weapon limit of player, -1 indicates no limit:\n"
        + "Add player [Player0] succeed.\n" + "Please input the type of player to create:\n"
        + "\t1. human-controlled\n" + "\t2. computer-controlled\n" + "\tq. quit creating\n"
        + "Please input the name of player:\n"
        + "Please input the space index that the player created at:\n"
        + "Please input the weapon limit of player, -1 indicates no limit:\n"
        + "Add player [Player1] succeed.\n" + "Please input the type of player to create:\n"
        + "\t1. human-controlled\n" + "\t2. computer-controlled\n" + "\tq. quit creating\n"
        + "All the players in the game: [Player0, Player1]\n"
        + "Please input the number below to select the function:\n" + "\t1. displayAllSpaces\n"
        + "\t2. displaySpaceDetail\n" + "\t3. generateGraphicalImage\n" + "\t4. startGame\n"
        + "\tq. exit\n" + "\n" + "Game start.\n" + "\n"
        + "This is the 1th turn of game. The target is in space: [Armory] health: [3], "
        + "the pet is in space: [Armory], evidence: []\n" + "\n"
        + "This is the 1th turn for player [Player0].\n"
        + "Player: [Player0] is in space: [Billiard Room], carrying weapons: []\n"
        + "Please use the number below to select the action for player [Player0]\n"
        + "\t1. move player to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n" + "\t4. move pet to a new space.\n"
        + "\t5. attack target.\n" + "Player: [Player0] choose to PICK_UP_WEAPON.\n"
        + "Please input a weapon name from the weapons: [Billiard Cue]\n"
        + "Player pick up weapon succeed.\n" + "\n" + "This is the 1th turn for player [Player1].\n"
        + "Player: [Player1] is in space: [Armory], carrying weapons: []\n"
        + "Player: [Player1] choose to ATTACK_TARGET.\n" + "Attack succeed.\n" + "\n"
        + "This is the 2th turn of game. The target is in space: [Billiard Room] health: [2], "
        + "the pet is in space: [Drawing Room], evidence: []\n" + "\n"
        + "This is the 2th turn for player [Player0].\n"
        + "Player: [Player0] is in space: [Billiard Room], carrying weapons: [Billiard Cue]\n"
        + "Please use the number below to select the action for player [Player0]\n"
        + "\t1. move player to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n" + "\t4. move pet to a new space.\n"
        + "\t5. attack target.\n" + "Player: [Player0] choose to ATTACK_TARGET.\n"
        + "Attack succeed.\n" + "\n" + "Game end.\n"
        + "Winner is player: [Player0]. The pet is in space: [Drawing Room]. The target is dead. "
        + "Evidences: [Billiard Cue]\n"
        + "Player: [Player0] is in space: [Billiard Room], carrying weapons: []\n"
        + "Player: [Player1] is in space: [Armory], carrying weapons: []\n", log.toString());
  }

  @Test
  public void testWithConcreteModelComputerPlayerWin() {
    StringReader input = new StringReader(
        "1\nPlayer0\n0\n2\n2\nPlayer1\n1\n3\nq\n4\n5\n2\nRevolver\n");
    StringBuilder log = new StringBuilder();
    controller.setIn(input);
    controller.setOut(log);
    controller.setTurn(2);
    controller.playGame(context);

    Assert.assertEquals("Please input the type of player to create:\n" + "\t1. human-controlled\n"
        + "\t2. computer-controlled\n" + "\tq. quit creating\n"
        + "Please input the name of player:\n"
        + "Please input the space index that the player created at:\n"
        + "Please input the weapon limit of player, -1 indicates no limit:\n"
        + "Add player [Player0] succeed.\n" + "Please input the type of player to create:\n"
        + "\t1. human-controlled\n" + "\t2. computer-controlled\n" + "\tq. quit creating\n"
        + "Please input the name of player:\n"
        + "Please input the space index that the player created at:\n"
        + "Please input the weapon limit of player, -1 indicates no limit:\n"
        + "Add player [Player1] succeed.\n" + "Please input the type of player to create:\n"
        + "\t1. human-controlled\n" + "\t2. computer-controlled\n" + "\tq. quit creating\n"
        + "All the players in the game: [Player0, Player1]\n"
        + "Please input the number below to select the function:\n" + "\t1. displayAllSpaces\n"
        + "\t2. displaySpaceDetail\n" + "\t3. generateGraphicalImage\n" + "\t4. startGame\n"
        + "\tq. exit\n" + "\n" + "Game start.\n" + "\n"
        + "This is the 1th turn of game. The target is in space: [Armory] health: [3], "
        + "the pet is in space: [Armory], evidence: []\n" + "\n"
        + "This is the 1th turn for player [Player0].\n"
        + "Player: [Player0] is in space: [Armory], carrying weapons: []\n"
        + "Please use the number below to select the action for player [Player0]\n"
        + "\t1. move player to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n" + "\t4. move pet to a new space.\n"
        + "\t5. attack target.\n" + "Player: [Player0] choose to ATTACK_TARGET.\n"
        + "Attack succeed.\n" + "\n" + "This is the 1th turn for player [Player1].\n"
        + "Player: [Player1] is in space: [Billiard Room], carrying weapons: []\n"
        + "Player: [Player1] choose to PICK_UP_WEAPON.\n" + "Player pick up weapon succeed.\n"
        + "\n"
        + "This is the 2th turn of game. The target is in space: [Billiard Room] health: [2], "
        + "the pet is in space: [Drawing Room], evidence: []\n" + "\n"
        + "This is the 2th turn for player [Player0].\n"
        + "Player: [Player0] is in space: [Armory], carrying weapons: []\n"
        + "Please use the number below to select the action for player [Player0]\n"
        + "\t1. move player to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n" + "\t4. move pet to a new space.\n"
        + "\t5. attack target.\n" + "Player: [Player0] choose to PICK_UP_WEAPON.\n"
        + "Please input a weapon name from the weapons: [Revolver]\n"
        + "Player pick up weapon succeed.\n" + "\n" + "This is the 2th turn for player [Player1].\n"
        + "Player: [Player1] is in space: [Billiard Room], carrying weapons: [Billiard Cue]\n"
        + "Player: [Player1] choose to ATTACK_TARGET.\n" + "Attack succeed.\n" + "\n"
        + "Game end.\n" + "Winner is player: [Player1]. The pet is in space: [Drawing Room]. "
        + "The target is dead. Evidences: [Billiard Cue]\n"
        + "Player: [Player0] is in space: [Armory], carrying weapons: [Revolver]\n"
        + "Player: [Player1] is in space: [Billiard Room], carrying weapons: []\n", log.toString());
  }

  @Test
  public void testWithMockModel() {
    Context mockContext = Mockito.mock(Context.class);
    BaseSpace space0 = Mockito.mock(BaseSpace.class);
    BaseSpace space1 = Mockito.mock(BaseSpace.class);
    Map<BaseSpace, List<BaseSpace>> map = new HashMap<>();
    map.put(space0, Arrays.asList(space1));
    map.put(space1, Arrays.asList(space0));

    Player mockPlayer1 = Mockito.mock(BasePlayer.class);
    Player mockPlayer2 = Mockito.mock(BasePlayer.class);
    Target mockTarget = Mockito.mock(Target.class);
    Pet mockPet = Mockito.mock(BasePet.class);
    BaseWeapon mockWeapon = Mockito.mock(BaseWeapon.class);
    BufferedImage mockImage = Mockito.mock(BufferedImage.class);

    Mockito.when(mockContext.getTarget()).thenReturn(mockTarget);
    Mockito.when(mockContext.getPet()).thenReturn(mockPet);
    Mockito.when(mockContext.getPlayers()).thenReturn(Arrays.asList(mockPlayer1, mockPlayer2));
    Mockito.when(mockContext.getSpaces()).thenReturn(Arrays.asList(space0, space1));
    Mockito.when(mockContext.getWeapons()).thenReturn(Arrays.asList(mockWeapon));
    Mockito.when(mockContext.getNeighborMap()).thenReturn(map);

    Mockito.when(mockWeapon.getHolder()).thenReturn("space: Space0");
    Mockito.when(mockWeapon.getName()).thenReturn("Weapon");
    Mockito.when(mockImage.getWidth()).thenReturn(10);
    Mockito.when(mockImage.getHeight()).thenReturn(15);
    MockedStatic<World> mockStatic = Mockito.mockStatic(World.class);

    mockStatic.when(() -> World.addPlayer(Mockito.any(Context.class), Mockito.any(Player.class)))
        .thenReturn(true);
    mockStatic.when(() -> World.getGraphicalImage(Mockito.any(Context.class)))
        .thenReturn(mockImage);
    mockStatic.when(() -> World.getSpace(Mockito.any(Context.class), eq(0))).thenReturn(space0);
    mockStatic.when(() -> World.getSpace(Mockito.any(Context.class), eq(1))).thenReturn(space1);
    mockStatic.when(() -> World.getSpace(Mockito.any(Context.class), eq("Space0")))
        .thenReturn(space0);
    mockStatic.when(() -> World.getSpace(Mockito.any(Context.class), eq("Space1")))
        .thenReturn(space1);

    Mockito.when(space0.getName()).thenReturn("Space0");
    Mockito.when(space0.getNeighbors()).thenReturn(Arrays.asList(space1));
    Mockito.when(space0.getOccupiers()).thenReturn(Arrays.asList(mockPlayer1));
    Mockito.when(space0.showDetail()).thenReturn("Space0 detail");

    Mockito.when(space1.getName()).thenReturn("Space1");
    Mockito.when(space1.getNeighbors()).thenReturn(Arrays.asList(space0));
    Mockito.when(space1.getOccupiers()).thenReturn(Arrays.asList(mockPlayer2));
    Mockito.when(space1.showDetail()).thenReturn("Space1 detail");

    Mockito.when(mockPlayer1.getName()).thenReturn("A");
    Mockito.when(mockPlayer1.getType()).thenReturn(PlayerType.HUMAN_CONTROLLED);
    Mockito.when(mockPlayer1.getSpaceIndex()).thenReturn(0);
    Mockito.when(mockPlayer2.getName()).thenReturn("B");
    Mockito.when(mockPlayer2.getType()).thenReturn(PlayerType.COMPUTER_CONTROLLED);
    Mockito.when(mockPlayer2.getSpaceIndex()).thenReturn(1);
    Mockito.when(mockTarget.getHealth()).thenReturn(10);
    Mockito.when(mockTarget.getPosition()).thenReturn(0);
    Mockito.when(mockPet.getName()).thenReturn("Pet");
    Mockito.when(mockPet.getSpaceIndex()).thenReturn(0);

    StringReader input = new StringReader("a\n1\nA\n0\n5\n2\nB\n1\n4\nq\n4\n5\n");
    StringBuilder log = new StringBuilder();
    ContextHolder.set(mockContext);
    controller.setIn(input);
    controller.setOut(log);
    controller.setTurn(1);
    controller.playGame(mockContext);

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
        + "Add player [B] succeed.\n" + "Please input the type of player to create:\n"
        + "\t1. human-controlled\n" + "\t2. computer-controlled\n" + "\tq. quit creating\n"
        + "All the players in the game: [A, B]\n"
        + "Please input the number below to select the function:\n" + "\t1. displayAllSpaces\n"
        + "\t2. displaySpaceDetail\n" + "\t3. generateGraphicalImage\n" + "\t4. startGame\n"
        + "\tq. exit\n" + "\n" + "Game start.\n" + "\n"
        + "This is the 1th turn of game. The target is in space: [Space0] health: [10], "
        + "the pet is in space: [Space0], evidence: []\n" + "\n"
        + "This is the 1th turn for player [A].\n"
        + "Player: [A] is in space: [Space0], carrying weapons: []\n"
        + "Please use the number below to select the action for player [A]\n"
        + "\t1. move player to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
        + "\t3. look around the space.\n" + "\t4. move pet to a new space.\n"
        + "\t5. attack target.\n" + "Player: [A] choose to ATTACK_TARGET.\n" + "Attack succeed.\n"
        + "\n" + "This is the 1th turn for player [B].\n"
        + "Player: [B] is in space: [Space1], carrying weapons: []\n"
        + "Player: [B] choose to LOOK_AROUND.\n" + "Space1 detail\n" + "Neighbors: [\n"
        + "Space: [Space0], Pet: [Pet]]\n" + "\n" + "Game end.\n"
        + "The target escaped with health: [10]. The pet is in space: [Space0]. "
        + "No winner. Evidences: []\n" + "Player: [A] is in space: [Space0], carrying weapons: []\n"
        + "Player: [B] is in space: [Space1], carrying weapons: []\n", log.toString());
  }
}
