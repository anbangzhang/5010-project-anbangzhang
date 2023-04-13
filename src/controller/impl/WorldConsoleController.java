package controller.impl;

import flowengine.context.FlowContext;
import flowengine.enums.Flow;
import flowengine.process.BaseProcessCallBack;
import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.springframework.stereotype.Component;
import world.World;
import world.base.BasePlayer;
import world.base.BaseWeapon;
import world.constant.Constants;
import world.context.Context;
import world.context.ContextHolder;
import world.enums.PlayerType;
import world.model.Player;
import world.model.Space;

/**
 * WorldConsoleController class.
 * 
 * @author anbang
 * @date 2023-02-10 22:30
 */
@Component(value = "worldController")
public class WorldConsoleController extends AbstractWorldController {
  /**
   * Input.
   */
  private Readable in;
  /**
   * Output.
   */
  private Appendable out;
  /**
   * Scanner.
   */
  private Scanner scan;

  /**
   * Set input.
   *
   * @param in input
   */
  public void setIn(Readable in) {
    if (Objects.isNull(in)) {
      throw new IllegalArgumentException("Invalid input source.");
    }
    this.in = in;
    this.scan = new Scanner(this.in);
  }

  /**
   * Set output.
   *
   * @param out output
   */
  public void setOut(Appendable out) {
    if (Objects.isNull(out)) {
      throw new IllegalArgumentException("Invalid output source.");
    }
    this.out = out;
  }

  @Override
  public void playGame(Context context) {
    if (Objects.isNull(context)) {
      throw new IllegalArgumentException("invalid context model");
    }
    try {

      initPlayers(context);

      while (true) {

        printGeneralInstructions();

        try {
          String selection = getInput();

          int select = Integer.parseInt(selection);
          /* displayAllSpaces */
          if (select == 1) {
            this.out.append(String.format("The spaces: %s\n", World.getAllSpaces(context)));
            /* displaySpaceDetail */
          } else if (select == 2) {
            this.out.append("Please input the space index:\n");
            selection = getNumberInput();
            Space space = World.getSpace(context, Integer.parseInt(selection));
            if (Objects.nonNull(space)) {
              this.out.append(String.format("%s\n", space.toString()));
            } else {
              this.out.append("The space is null.\n");
            }
            /* generateGraphicalImage */
          } else if (select == 3) {
            this.out.append("Please input the output directory:\n");
            selection = this.scan.nextLine().trim();
            World.generateGraphicalImage(context, selection);
            this.out.append(String.format(
                "Graphical Image generation succeed, please check %s directory\n", selection));
            /* startGame */
          } else if (select == 4) {

            start(context);

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
   * @param ctx context
   * @throws IOException output fail
   */
  private void start(Context ctx) throws IOException {
    this.out.append("\nGame start.\n");
    ctx.set(Constants.SCANNER, this.scan);
    ctx.set(Constants.OUT, this.out);

    List<Player> players = ctx.getPlayers();

    BufferedImage image = World.getGraphicalImage(ctx);
    JFrame frame = new JFrame();
    frame.setLayout(new FlowLayout());
    frame.setSize(image.getWidth(), image.getHeight());
    JLabel label = new JLabel();

    label.setIcon(new ImageIcon(image));
    frame.add(label);
    frame.setVisible(true);

    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

    try {
      for (int i = 0; i < this.turn; i++) {

        resetExposedSpaces(ctx);

        printGameInfo(ctx, i);

        for (Player player : players) {

          label.setIcon(new ImageIcon(World.getGraphicalImage(ctx)));

          this.out.append(
              String.format("\nThis is the %dth turn for player [%s].\n", i + 1, player.getName()));

          displayPlayerDetail(ctx, player);

          // human-controlled player
          if (Objects.equals(PlayerType.HUMAN_CONTROLLED, player.getType())) {

            handleHumanPlayer(player);

          } else {
            // computer-controlled player
            handlerComputerPlayer(ctx, player);
          }

          if (Boolean.TRUE.equals(ctx.getGameOver())) {
            break;
          }

        }
        if (Boolean.TRUE.equals(ctx.getGameOver())) {
          break;
        }

        // #### Move Target and Pet ####
        moveTargetAndPet(ctx);
      }

      displayGameResult(ctx, ctx.getGameOver());

      for (Player player : players) {
        displayPlayerDetail(ctx, player);
      }
    } finally {
      // close image
      frame.dispose();
    }
  }

  private void initPlayers(Context context) throws IOException {
    /* add players */
    while (true) {
      if (Objects.equals(context.getPlayers().size(), this.maxPlayerAmount)) {
        break;
      }
      try {
        createPlayer(context, context.getPlayers().size());
      } catch (InterruptedException e) {
        break;
      }
    }
    this.out.append(String.format("All the players in the game: %s\n",
        context.getPlayers().stream().map(Player::getName).collect(Collectors.toList())));
  }

  private void printGeneralInstructions() throws IOException {
    this.out.append("Please input the number below to select the function:\n"
        + "\t1. displayAllSpaces\n" + "\t2. displaySpaceDetail\n" + "\t3. generateGraphicalImage\n"
        + "\t4. startGame\n" + "\tq. exit\n");
  }

  private void resetExposedSpaces(Context context) {
    context.setExposedSpaces(new HashSet<>());
  }

  private void printGameInfo(Context context, int turn) throws IOException {
    this.out.append(String.format(
        "\nThis is the %dth turn of game. The target is in space: [%s] health: [%d],"
            + " the pet is in space: [%s], evidence: %s\n",
        turn + 1, World.getSpace(context, context.getTarget().getPosition()).getName(),
        context.getTarget().getHealth(),
        World.getSpace(context, context.getPet().getSpaceIndex()).getName(),
        context.getEvidences().stream().map(BaseWeapon::getName).collect(Collectors.toList())));
  }

  private void handleHumanPlayer(Player player) throws IOException {
    while (true) {
      this.out
          .append(String.format("Please use the number below to select the action for player [%s]\n"
              + "\t1. move player to a neighbor space.\n" + "\t2. pick up a weapon in the space.\n"
              + "\t3. look around the space.\n" + "\t4. move pet to a new space.\n"
              + "\t5. attack target.\n", player.getName()));

      String input = this.scan.nextLine().trim();
      int actionIndex = Integer.parseInt(input);

      Flow flow = Flow.getByCode(actionIndex);
      if (Objects.isNull(flow) || Objects.equals(flow, Flow.PET_DFS)) {
        this.out.append("Invalid number.\n");
        continue;
      }

      this.out
          .append(String.format("Player: [%s] choose to %s.\n", player.getName(), flow.getDesc()));

      BaseResult baseResult = serviceTemplate.execute(new BaseRequest(player), flow.getDesc(),
          new BaseProcessCallBack() {
            @Override
            public void enrichContext(BaseRequest request, FlowContext context) {
              context.setContext(ContextHolder.get());
            }

            @Override
            public BaseResult createDefaultResult() {
              return BaseResult.newSuccessResult().build();
            }
          });

      if (baseResult.isSuccess()) {
        this.out.append((String) baseResult.getResult()).append("\n");
        return;
      } else {
        this.out.append(baseResult.getErrorMsg()).append("\n");
      }
    }
  }

  private void handlerComputerPlayer(Context ctx, Player player) throws IOException {
    Flow flow = determineFlowForComputer(ctx, player);

    this.out
        .append(String.format("Player: [%s] choose to %s.\n", player.getName(), flow.getDesc()));

    BaseResult baseResult = serviceTemplate.execute(new BaseRequest(player, Boolean.TRUE),
        flow.getDesc(), new BaseProcessCallBack() {
          @Override
          public void enrichContext(BaseRequest request, FlowContext context) {
            context.setContext(ContextHolder.get());
          }

          @Override
          public BaseResult createDefaultResult() {
            return BaseResult.newSuccessResult().build();
          }
        });

    if (baseResult.isSuccess()) {
      this.out.append((String) baseResult.getResult()).append("\n");
    } else {
      this.out.append(baseResult.getErrorMsg()).append("\n");
    }
  }

  private Flow determineFlowForComputer(Context ctx, Player player) {
    Space space = World.getSpace(ctx, player.getSpaceIndex());

    if (Objects.equals(ctx.getTarget().getPosition(), player.getSpaceIndex())) {
      return Flow.ATTACK_TARGET;
    } else if (space.getWeapons().size() > 0
        && player.getWeapons().size() < player.getWeaponLimit()) {
      return Flow.PICK_UP_WEAPON;
    }
    return Flow.LOOK_AROUND;
  }

  private void moveTargetAndPet(Context ctx) throws IOException {
    World.moveTarget(ctx);
    serviceTemplate.execute(new BaseRequest(null), Flow.PET_DFS.getDesc(),
        new BaseProcessCallBack() {
          @Override
          public void enrichContext(BaseRequest request, FlowContext context) {
            context.setContext(ContextHolder.get());
          }

          @Override
          public BaseResult createDefaultResult() {
            return BaseResult.newSuccessResult().build();
          }
        });
  }

  private void displayGameResult(Context context, boolean gameOver) throws IOException {
    this.out.append("\nGame end.\n");
    Space petPos = World.getSpace(context, context.getPet().getSpaceIndex());
    List<String> evidences = context.getEvidences().stream().map(BaseWeapon::getName)
        .collect(Collectors.toList());
    if (gameOver) {
      this.out.append(String.format(
          "Winner is player: [%s]. The pet is in space: [%s]. The target is dead. Evidences: %s\n",
          ((Player) context.get(Constants.WINNER)).getName(), petPos.getName(), evidences));
    } else {
      this.out
          .append(String.format(
              "The target escaped with health: [%d]. The pet is in space: [%s]. No winner. "
                  + "Evidences: %s\n",
              context.getTarget().getHealth(), petPos.getName(), evidences));
    }
  }

  /**
   * Display player's detail.
   * 
   * @param ctx    context
   * @param player player
   * @throws IOException output fail
   */
  private void displayPlayerDetail(Context ctx, Player player) throws IOException {
    Space space = World.getSpace(ctx, player.getSpaceIndex());
    this.out.append(String.format("Player: [%s] is in space: [%s], carrying weapons: %s\n",
        player.getName(), space.getName(),
        player.getWeapons().stream().map(BaseWeapon::getName).collect(Collectors.toList())));
  }

  /**
   * Create player from user input.
   * 
   * @param ctx   context
   * @param order player order
   * @return create player successful
   * @throws InterruptedException quit game
   * @throws IOException          fail to write out stream
   */
  private void createPlayer(Context ctx, int order) throws InterruptedException, IOException {
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
    if (!World.addPlayer(ctx, new BasePlayer(order, name, spaceIndex, type, limit))) {
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

  @Override
  public void createNewGame() {
    throw new UnsupportedOperationException("Unsupported method");
  }

  @Override
  public void restartGame() {
    throw new UnsupportedOperationException("Unsupported method");
  }

  @Override
  public void quitGame() {
    throw new UnsupportedOperationException("Unsupported method");
  }
}
