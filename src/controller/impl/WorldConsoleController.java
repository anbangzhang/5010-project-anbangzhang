package controller.impl;

import controller.WorldController;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import world.World;
import world.base.BasePlayer;
import world.base.BaseSpace;
import world.enums.PlayerType;
import world.exception.BusinessException;
import world.model.Player;
import world.model.Space;
import world.model.Weapon;

/**
 * WorldConsoleController class.
 * 
 * @author anbang
 * @date 2023-02-10 22:30
 */
public class WorldConsoleController implements WorldController {

  /**
   * Input.
   */
  private Readable in;

  /**
   * Output.
   */
  private Appendable out;

  /**
   * Turn limit.
   */
  private Integer turn;

  /**
   * Scanner.
   */
  private Scanner scan;

  /**
   * Constructor.
   * 
   * @param in   input
   * @param out  output
   * @param turn turn limit
   */
  public WorldConsoleController(Readable in, Appendable out, Integer turn) {
    if (Objects.isNull(in) || Objects.isNull(out)) {
      throw new IllegalArgumentException("Invalid input and output source.");
    }
    this.in = in;
    this.out = out;
    this.turn = turn;
    this.scan = new Scanner(this.in);
  }

  /**
   * Play the game.
   *
   * @param world world model
   */
  @Override
  public void playGame(World world) {
    if (Objects.isNull(world)) {
      throw new IllegalArgumentException("invalid world model");
    }
    try {
      int playerOrder = 0;
      /* add players */
      while (true) {
        try {
          createPlayer(world, playerOrder++);
        } catch (InterruptedException e) {
          break;
        }
      }
      List<Player> players = world.getAllPlayers();
      this.out.append(String.format("All the players in the game: %s\n",
          players.stream().map(Player::getName).collect(Collectors.toList())));

      while (true) {
        this.out.append("Please input the number below to select the function:\n"
            + "\t1. displayAllSpaces\n" + "\t2. displaySpaceDetail\n"
            + "\t3. displayGraphicalImage\n" + "\t4. startGame\n" + "\tq. exit\n");
        try {
          String selection = getInput();

          int select = Integer.parseInt(selection);
          /* displayAllSpaces */
          if (select == 1) {
            this.out.append(String.format("The spaces: %s\n", world.getAllSpaces()));
            /* displaySpaceDetail */
          } else if (select == 2) {
            this.out.append("Please input the space index:\n");
            selection = getNumberInput();
            Space space = world.getSpace(Integer.parseInt(selection));
            if (Objects.nonNull(space)) {
              this.out.append(String.format("%s\n", space.toString()));
            } else {
              this.out.append("The space is null.\n");
            }
            /* displayGraphicalImage */
          } else if (select == 3) {
            this.out.append("Please input the output directory:\n");
            selection = this.scan.nextLine().trim();
            world.showGraphicalImage(selection);
            this.out.append(String.format(
                "Graphical Image generation succeed, please check %s directory\n", selection));
            /* startGame */
          } else if (select == 4) {
            start(world);
            break;
          } else {
            this.out.append("Invalid selection.\n");
          }
        } catch (InterruptedException e) {
          break;
        }
      }
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed.", ioe);
    }
  }

  /**
   * Start the game.
   * 
   * @param world world
   * @throws IOException output fail
   */
  private void start(World world) throws IOException {
    this.out.append("\nGame start.\n");
    List<Player> players = world.getAllPlayers();
    String input = "";
    for (int i = 0; i < this.turn; i++) {
      this.out.append(String.format("\nThis is the %dth turn of game.\n", i + 1));
      for (Player player : players) {
        this.out.append(
            String.format("This is the %dth turn for player [%s].\n", i + 1, player.getName()));
        displayPlayerDetail(player, world);

        if (Objects.equals(PlayerType.HUMAN_CONTROLLED, player.getType())) {
          while (true) {
            this.out.append(String.format(
                "Please use the number below to select thea action for player [%s]\n"
                    + "\t1. move to a neighbor space.\n"
                    + "\t2. pick up a weapon in the space.\n\t3. look around the space.\n",
                player.getName()));
            input = this.scan.nextLine().trim();
            int action = Integer.parseInt(input);
            Space cur = world.getSpace(player.getSpaceIndex());
            /* move the player */
            if (action == 1) {
              this.out.append(String.format(
                  "Please input a neighbor space name from the neighbors: %s\n", cur.getNeighbors()
                      .stream().map(BaseSpace::getName).collect(Collectors.toList())));
              input = this.scan.nextLine().trim();
              try {
                movePlayer(player, cur, input, world);
                break;
              } catch (BusinessException e) {
                this.out.append(String.format("Player [%s] move to space [%s] failed, cause: %s\n",
                    player.getName(), input, e.getMessage()));
              }
              /* pick up a weapon */
            } else if (action == 2) {
              this.out.append(String.format("Please input a weapon name from the weapons: %s\n",
                  cur.getWeapons().stream().map(Weapon::getName).collect(Collectors.toList())));
              input = this.scan.nextLine().trim();
              try {
                pickUp(player, cur, input, world);
                break;
              } catch (BusinessException e) {
                this.out.append(String.format("Player [%s] pick up weapon [%s] failed, cause: %s\n",
                    player.getName(), input, e.getMessage()));
              }
              /* look around */
            } else if (action == 3) {
              displayPlayerSpaceDetail(player, world);
              break;
            }
          }
        } else {
          /* show player's detail */
          displayPlayerSpaceDetail(player, world);
        }
      }
    }
    this.out.append("\nGame end.\n");
    for (Player player : players) {
      displayPlayerDetail(player, world);
    }
  }

  /**
   * Move a player to a neighbor space.
   * 
   * @param player   player
   * @param cur      current space
   * @param neighbor neighbor name
   * @param world    world
   * @throws BusinessException move fail
   * @throws IOException       output fail
   */
  private void movePlayer(Player player, Space cur, String neighbor, World world)
      throws BusinessException, IOException {
    if (cur.getNeighbors().stream().noneMatch(item -> neighbor.equals(item.getName()))) {
      throw new BusinessException(
          String.format("Space %s is not a neighbor of player's current space.", neighbor));
    } else {
      Space next = world.getSpace(neighbor);
      world.movePlayer(player, next);
      this.out.append(
          String.format("Player [%s] move to space [%s] succeed.\n", player.getName(), neighbor));
    }
  }

  /**
   * Player picks up a weapon.
   * 
   * @param player player
   * @param cur    current space
   * @param weapon weapon name
   * @param world  world
   * @throws BusinessException pick up fail
   * @throws IOException       output fail
   */
  private void pickUp(Player player, Space cur, String weapon, World world)
      throws BusinessException, IOException {
    Weapon w = cur.getWeapons().stream().filter(item -> weapon.equals(item.getName())).findFirst()
        .orElse(null);
    if (Objects.isNull(w)) {
      throw new BusinessException(String.format("Weapon %s is not in current space.", weapon));
    } else {
      world.pickUp(player, w);
      this.out.append(
          String.format("Player [%s] pick up weapon [%s] succeed.\n", player.getName(), weapon));
    }
  }

  /**
   * Display player's space detail.
   * 
   * @param player player
   * @param world  world
   * @throws IOException output fail
   */
  private void displayPlayerSpaceDetail(Player player, World world) throws IOException {
    Space space = world.getSpace(player.getSpaceIndex());
    this.out.append(String.format(
        "Player: [%s] is in space: [%s], players inside this space: %s,"
            + " its neighbors: %s, weapons inside this space: %s\n",
        player.getName(), space.getName(),
        space.getOccupiers().stream().map(Player::getName).collect(Collectors.toList()),
        space.getNeighbors().stream().map(BaseSpace::getName).collect(Collectors.toList()),
        space.getWeapons().stream().map(Weapon::getName).collect(Collectors.toList())));
  }

  /**
   * Display player's detail.
   * 
   * @param player player
   * @param world  world
   * @throws IOException output fail
   */
  private void displayPlayerDetail(Player player, World world) throws IOException {
    Space space = world.getSpace(player.getSpaceIndex());
    this.out.append(String.format("Player: [%s] is in space: [%s], carrying weapons: %s\n",
        player.getName(), space.getName(),
        player.getWeapons().stream().map(Weapon::getName).collect(Collectors.toList())));
  }

  /**
   * Create player from user input.
   * 
   * @param world world
   * @param order player order
   * @throws InterruptedException quit game
   * @throws IOException          fail to write out stream
   */
  private void createPlayer(World world, int order) throws InterruptedException, IOException {
    this.out.append("Please input the type of player to create:\n"
        + "\t1. human-controlled\n\t2. computer-controlled\n\tq. quit creating\n");
    String input = getInput();
    final PlayerType type = Integer.parseInt(input) == 1 ? PlayerType.HUMAN_CONTROLLED
        : PlayerType.COMPUTER_CONTROLLED;
    this.out.append("Please input the name of player:\n");
    final String name = this.scan.nextLine();
    this.out.append("Please input the space index that the player created at:\n");
    input = getNumberInput();
    int spaceIndex = Integer.parseInt(input);
    this.out.append("Please input the weapon limit of player, -1 indicates no limit:\n");
    input = getNumberInput();
    Integer limit = Integer.parseInt(input) == -1 ? null : Integer.parseInt(input);
    if (!world.addPlayer(new BasePlayer(order, name, spaceIndex, type, limit))) {
      this.out.append("The name of player is repeated or the space index is invalid.\n");
    } else {
      this.out.append(String.format("Add player [%s] succeed.\n", name));
    }
  }

  /**
   * get input.
   *
   * @return input string
   * @throws InterruptedException user input q or Q
   * @throws IOException          fail to append
   */
  private String getInput() throws InterruptedException, IOException {
    String str = this.scan.nextLine();
    while (invalidInput(str)) {
      this.out.append(String.format("Not a valid number: %s\n", str));
      str = this.scan.nextLine();
    }
    return str;
  }

  /**
   * get input.
   *
   * @return input string
   * @throws IOException fail to append
   */
  private String getNumberInput() throws IOException {
    String str = this.scan.nextLine();
    while (validNumber(str)) {
      this.out.append(String.format("Not a valid number: %s\n", str));
      str = this.scan.nextLine();
    }
    return str;
  }

  /**
   * check if input is invalid.
   *
   * @param num num
   * @return if it's invalid
   * @throws InterruptedException quit
   */
  private boolean invalidInput(String num) throws InterruptedException {
    if ("Q".equals(num) || "q".equals(num)) {
      throw new InterruptedException();
    }
    try {
      Integer.parseInt(num);
      return false;
    } catch (NumberFormatException e) {
      return true;
    }
  }

  /**
   * check if input is number.
   *
   * @param num num
   * @return if it's valid number
   */
  private boolean validNumber(String num) {
    try {
      Integer.parseInt(num);
      return false;
    } catch (NumberFormatException e) {
      return true;
    }
  }
}
