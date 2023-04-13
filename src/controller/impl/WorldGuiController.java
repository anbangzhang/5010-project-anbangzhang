package controller.impl;

import flowengine.context.FlowContext;
import flowengine.enums.Flow;
import flowengine.process.BaseProcessCallBack;
import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import org.springframework.stereotype.Component;
import view.WorldGuiView;
import view.WorldView;
import view.listener.ButtonListener;
import view.listener.KeyboardListener;
import view.listener.MouseClickListener;
import world.World;
import world.base.BasePlayer;
import world.base.BaseWeapon;
import world.constant.Constants;
import world.context.Context;
import world.context.ContextBuilder;
import world.context.ContextHolder;
import world.enums.PlayerType;
import world.exception.BusinessException;
import world.model.Player;
import world.model.Space;
import world.model.Target;

/**
 * WorldGuiController.
 *
 * @author anbang
 * @date 2023-04-05 01:32
 */
@Component(value = "worldGuiController")
public class WorldGuiController extends AbstractWorldController {

  private final WorldView view;

  private final MouseClickListener mouseListener;

  private List<String> logger;

  private Context context;

  private String filePath;

  /**
   * Constructor.
   */
  public WorldGuiController() {
    this.view = new WorldGuiView("Kill Doctor Lucky");
    this.mouseListener = new MouseClickListener();
    this.logger = new ArrayList<>();
  }

  /**
   * Set up controller.
   * 
   * @param context  context
   * @param filePath filePath
   */
  public void setUp(Context context, String filePath) {
    this.context = context;
    this.filePath = filePath;
    configureButtonListener();
    configureKeyboardListener();
    configureMouseClickListener();
  }

  private void configureButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<>();

    buttonClickedMap.put(Constants.QUIT_GAME, this::quitGame);

    buttonClickedMap.put(Constants.HOW_TO_PLAY_GAME, this::help);

    buttonClickedMap.put(Constants.ABOUT_GAME, this::about);

    buttonClickedMap.put(Constants.NEW_GAME, this::newGame);

    buttonClickedMap.put(Constants.RESTART_GAME, this::restartGame);

    buttonClickedMap.put(Constants.CREATE_NEW_GAME, this::createNewGame);

    buttonClickedMap.put(Constants.ADD_PLAYER, this::addPlayerScreen);

    buttonClickedMap.put(Constants.ADD, this::addPlayer);

    buttonClickedMap.put(Constants.PLAY_GAME, () -> {
      initGame();
      this.displayBoard("Play Game");
    });

    ButtonListener buttonListener = new ButtonListener();
    buttonListener.setMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }

  private void configureKeyboardListener() {
    Map<Integer, Runnable> keyPresses = new HashMap<>();

    keyPresses.put(KeyEvent.VK_P, this::executePick);
    keyPresses.put(KeyEvent.VK_L, this::executeLook);
    keyPresses.put(KeyEvent.VK_A, this::executeAttack);

    KeyboardListener keyListener = new KeyboardListener();
    keyListener.setMap(keyPresses);
    this.view.addActionListener(keyListener);
  }

  private void configureMouseClickListener() {
    Map<Integer, Consumer<String>> mouseClickedMap = new HashMap<>();

    mouseClickedMap.put(MouseEvent.BUTTON1, (input) -> {
      if (World.getAllSpaces(this.context).contains(input)) {
        this.executeMovePlayer(input);
      } else if (context.getPlayers().stream().map(Player::getName).collect(Collectors.toList())
          .contains(input)) {
        this.showPlayerDetails(input);
      } else {
        this.showTargetDetails();
      }
    });

    mouseClickedMap.put(MouseEvent.BUTTON3, (input) -> {
      if (World.getAllSpaces(this.context).contains(input)) {
        this.executeMovePet(input);
      }
    });
    mouseListener.setMouseClickActionMap(mouseClickedMap);
  }

  @Override
  public void playGame(Context context) {
    this.view.welcomeScreen();
  }

  @Override
  public void quitGame() {
    int choice = this.view.confirmScreen("Are you sure you want to quit?");
    if (choice == JOptionPane.YES_OPTION) {
      this.view.quit();
    }
  }

  private void help() {
    this.view.displayInfo(Constants.HELP_TIPS);
  }

  private void about() {
    this.view.displayInfo(Constants.ABOUT_STRING);
  }

  private void newGame() {
    int choice = this.view.confirmScreen("Are you sure you want to start a new game?");
    if (choice == JOptionPane.YES_OPTION) {
      this.view.setupScreen();
    }
  }

  @Override
  public void createNewGame() {
    String[] inputs = this.view.getSetupInput();
    if (Objects.nonNull(inputs)) {
      try {
        this.context = ContextBuilder.build(new FileReader(inputs[0]));
        this.filePath = inputs[0];
        ContextHolder.set(this.context);
        setTurn(Integer.parseInt(inputs[1]));
        setMaxPlayerAmount(Integer.parseInt(inputs[2]));
        this.logger = new ArrayList<>();
        this.view.displayInfo("New Game Created.");
        this.view.addPlayerScreen(World.getAllSpaces(this.context));
      } catch (FileNotFoundException e) {
        this.view.displayError("File is not present.");
      } catch (NumberFormatException e) {
        this.view.displayError("Invalid File.");
      } catch (IllegalArgumentException | NullPointerException exception) {
        this.view.displayError(exception.getMessage());
      }
    }
  }

  @Override
  public void restartGame() {
    int choice = this.view
        .confirmScreen("Are you sure you want to restart the game with the same settings?");
    if (choice == JOptionPane.YES_OPTION) {
      try {
        Context context = ContextBuilder.build(new FileReader(this.filePath));
        ContextHolder.set(context);
        this.context = context;

        this.logger = new ArrayList<>();
        this.view.restartGame(World.getAllSpaces(context));
      } catch (FileNotFoundException e) {
        this.view.displayError(e.getMessage());
      }
    }
  }

  private void initGame() {
    if (this.context.getPlayers().size() == 0) {
      World.setCurrentPlayer(this.context, null);
    } else {
      World.setCurrentPlayer(this.context, this.context.getPlayers().get(0));
    }

    this.context.setCurrentTurn(0);
  }

  private void addPlayer() {
    String[] inputs = this.view.getPlayerInput();
    if (Objects.nonNull(inputs)) {
      try {
        int size = context.getPlayers().size();
        if (Objects.equals(size, this.maxPlayerAmount)) {
          throw new IllegalStateException("Players Reached Maximum Amount.");
        }
        Space space = World.getSpace(context, inputs[1]);
        int weaponLimit = Integer.parseInt(inputs[2]);
        PlayerType type = Boolean.parseBoolean(inputs[3]) ? PlayerType.HUMAN_CONTROLLED
            : PlayerType.COMPUTER_CONTROLLED;
        Player player = new BasePlayer(size, inputs[0], space.getOrder(), type,
            weaponLimit == -1 ? null : weaponLimit);
        if (!World.addPlayer(context, player)) {
          throw new IllegalArgumentException("Repeated Player Name.");
        }
        this.view.setPlayerColor(World.getAllSpaces(context));
      } catch (IllegalArgumentException | IllegalStateException exception) {
        this.view.displayError(exception.getMessage());
      }
    }
  }

  private void addPlayerScreen() {
    if (!Objects.equals(Boolean.TRUE, context.getGameOver())) {
      this.view.addPlayerScreen(World.getAllSpaces(context));
    } else {
      this.view.displayError("Game is Over!");
    }
  }

  private void displayBoard(String message) {
    logger.add(message);
    try {
      if (Boolean.TRUE.equals(context.getGameOver())) {
        if (Objects.isNull(context.get(Constants.WINNER))) {
          logger.add("Game Over. Target Escaped. Nobody wins!");

          this.view.gameEndScreen("Game Over. Target Escaped. Nobody wins!");
        } else {
          Player winner = (Player) context.get(Constants.WINNER);
          logger.add(winner.getName() + " killed the Target and Won the Game!");

          this.view.gameEndScreen(winner.getName() + " killed the Target and Won the Game!");
        }
      } else {
        StringBuilder buildMessage = new StringBuilder();
        buildMessage.append("<html>");
        int start = this.logger.size() - 200 < 0 ? 0 : this.logger.size() - 200;
        for (int i = start; i < this.logger.size(); i++) {
          buildMessage.append(this.logger.get(i)).append("<br>");
        }
        buildMessage.append("</html>");

        this.view.refreshBoard(context, World.getCurrentPlayer(context), context.getCurrentTurn(),
            buildMessage.toString(), mouseListener);
      }
    } catch (BusinessException e) {
      this.view.displayError(e.getMessage());
    }
  }

  private void executePick() {
    try {
      Player player = World.getCurrentPlayer(this.context);
      if (Objects.equals(PlayerType.HUMAN_CONTROLLED, player.getType())) {
        Space space = World.getSpace(context, player.getSpaceIndex());
        ContextHolder.set(this.context);

        String weapon = this.view.displayWeapons(space.getWeapons());
        if (Objects.nonNull(weapon)) {
          this.executeFlow(Flow.PICK_UP_WEAPON, player, weapon);
        }
      }
    } catch (IOException | BusinessException e) {
      this.view.displayError(e.getMessage());
    }
  }

  private void executeLook() {
    try {
      Player player = World.getCurrentPlayer(this.context);
      if (Objects.equals(PlayerType.HUMAN_CONTROLLED, player.getType())) {
        String message = player.getName() + " tried to look around. Looked around Successfully.";
        ContextHolder.set(this.context);

        BaseResult<String> baseResult = serviceTemplate.execute(
            new BaseRequest(player, Boolean.FALSE), Flow.LOOK_AROUND.getDesc(),
            new BaseProcessCallBack() {
              @Override
              public void enrichContext(BaseRequest request, FlowContext ctx) {
                ctx.setContext(context);
              }

              @Override
              public BaseResult createDefaultResult() {
                return BaseResult.newSuccessResult().build();
              }
            });
        this.view.displayInfo(baseResult.getResult());
        this.displayBoard(message);
      }
    } catch (IOException | BusinessException e) {
      this.view.displayError(e.getMessage());
    }
  }

  private void executeAttack() {
    try {
      Player player = World.getCurrentPlayer(this.context);
      if (Objects.equals(PlayerType.HUMAN_CONTROLLED, player.getType())) {
        ContextHolder.set(this.context);

        if (player.getWeapons().isEmpty()) {
          this.executeFlow(Flow.ATTACK_TARGET, player, Constants.NO_WEAPON);
        } else {
          this.executeFlow(Flow.ATTACK_TARGET, player, player.getWeapons().get(0).getName());
        }
      }
    } catch (IOException | BusinessException e) {
      this.view.displayError(e.getMessage());
    }
  }

  private void executeMovePlayer(String input) {
    try {
      Player player = World.getCurrentPlayer(this.context);
      if (Objects.equals(PlayerType.HUMAN_CONTROLLED, player.getType())) {
        ContextHolder.set(this.context);

        executeFlow(Flow.MOVE_PLAYER, player, input);
      }
    } catch (IOException | BusinessException e) {
      this.view.displayError(e.getMessage());
    }
  }

  private void executeMovePet(String input) {
    try {
      Player player = World.getCurrentPlayer(this.context);
      if (Objects.equals(PlayerType.HUMAN_CONTROLLED, player.getType())) {
        ContextHolder.set(this.context);

        executeFlow(Flow.MOVE_PET, player, input);
      }
    } catch (IOException | BusinessException e) {
      this.view.displayError(e.getMessage());
    }
  }

  private void executeFlow(Flow flow, Player player, String input) throws IOException {
    StringBuilder sb = new StringBuilder();
    sb.append(player.getName()).append(" tried to ").append(flow.getAction());
    if (Objects.nonNull(input) && input.length() > 0) {
      sb.append(" ").append(input);
    }
    sb.append(".");
    String message = sb.toString();

    BaseRequest request = new BaseRequest(player, Boolean.TRUE);
    request.setInput(input);
    BaseResult<String> baseResult = serviceTemplate.execute(request, flow.getDesc(),
        new BaseProcessCallBack() {
          @Override
          public void enrichContext(BaseRequest request, FlowContext ctx) {
            ctx.setContext(context);
          }

          @Override
          public BaseResult createDefaultResult() {
            return BaseResult.newSuccessResult().build();
          }
        });
    if (baseResult.isSuccess()) {
      this.displayBoard(message + " " + baseResult.getResult());
    } else {
      this.view.displayError(baseResult.getErrorMsg());
    }
  }

  private void showPlayerDetails(String playerName) {
    Player player = this.context.getPlayers().stream()
        .filter(item -> Objects.equals(item.getName(), playerName)).findFirst().orElse(null);
    Objects.requireNonNull(player);

    Space space = World.getSpace(this.context, player.getSpaceIndex());
    this.view.displayInfo(String.format(
        "Player Details:\nPlayer Name = %s\nWeapons = %s\nWeapon limit = %s\n"
            + "Player Type = %s\nCurrent space = %s",
        player.getName(),
        player.getWeapons().stream().map(BaseWeapon::getName).collect(Collectors.toList()),
        player.getWeaponLimit() == null ? "Unlimited" : player.getWeaponLimit(), player.getType(),
        space.getName()));
  }

  private void showTargetDetails() {
    Target target = this.context.getTarget();

    Space space = World.getSpace(context, target.getPosition());
    this.view.displayInfo(
        String.format("Target Details:\nTarget Name = %s\nHealth = %s\nTarget Location = %s",
            target.getName(), target.getHealth(), space.getName()));
  }

}
