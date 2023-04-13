package controller.impl;

import controller.gameengine.GameEngine;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import model.World;
import model.base.BasePlayer;
import model.base.BaseWeapon;
import model.constant.Constants;
import model.context.Context;
import model.context.ContextBuilder;
import model.context.ContextHolder;
import model.enums.PlayerType;
import model.exception.BusinessException;
import model.model.Player;
import model.model.Space;
import model.model.Target;
import org.springframework.stereotype.Component;
import view.WorldGuiView;
import view.WorldView;
import view.listener.ButtonListener;
import view.listener.KeyboardListener;
import view.listener.MouseClickListener;

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

  private Timer timer;

  private Context context;

  private String filePath;

  /**
   * Constructor.
   */
  public WorldGuiController() {
    this.view = new WorldGuiView("Kill Doctor Lucky");
    this.mouseListener = new MouseClickListener();
    this.logger = new ArrayList<>();
    this.timer = new Timer(Constants.TIMER_DELAY, e -> this.computerPlayerAction());
    configureButtonListener();
    configureKeyboardListener();
    configureMouseClickListener();
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
    initGameEngine();
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
      this.timer.start();
      displayBoard("Play Game");
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
        executeMovePlayer(input);
      } else if (this.context.getPlayers().stream().map(Player::getName)
          .collect(Collectors.toList()).contains(input)) {
        showPlayerDetails(input);
      } else {
        showTargetDetails();
      }
    });

    mouseClickedMap.put(MouseEvent.BUTTON3, (input) -> {
      if (World.getAllSpaces(this.context).contains(input)) {
        executeMovePet(input);
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
      this.timer.stop();
      this.view.setupScreen();
    }
  }

  @Override
  public void createNewGame() {
    String[] inputs = this.view.getSetupInput();
    if (Objects.nonNull(inputs)) {
      try {
        Context context = ContextBuilder.build(new FileReader(inputs[0]));
        String filePath = inputs[0];
        ContextHolder.set(this.context);
        setTurn(Integer.parseInt(inputs[1]));
        setMaxPlayerAmount(Integer.parseInt(inputs[2]));
        setUp(context, filePath);

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
        this.timer.stop();
        Context context = ContextBuilder.build(new FileReader(this.filePath));
        ContextHolder.set(context);
        setUp(context, this.filePath);

        this.logger = new ArrayList<>();
        this.view.restartGame(World.getAllSpaces(this.context));
      } catch (FileNotFoundException e) {
        this.view.displayError(e.getMessage());
      }
    }
  }

  private void initGameEngine() {
    this.gameEngine = new GameEngine(this.context, this.turn);
  }

  private void addPlayer() {
    String[] inputs = this.view.getPlayerInput();
    if (Objects.nonNull(inputs)) {
      try {
        int size = this.context.getPlayers().size();
        if (Objects.equals(size, this.maxPlayerAmount)) {
          throw new IllegalStateException("Players Reached Maximum Amount.");
        }
        Space space = World.getSpace(this.context, inputs[1]);
        int weaponLimit = Integer.parseInt(inputs[2]);
        PlayerType type = Boolean.parseBoolean(inputs[3]) ? PlayerType.HUMAN_CONTROLLED
            : PlayerType.COMPUTER_CONTROLLED;
        Player player = new BasePlayer(size, inputs[0], space.getOrder(), type,
            weaponLimit == -1 ? null : weaponLimit);
        if (!World.addPlayer(this.context, player)) {
          throw new IllegalArgumentException("Repeated Player Name.");
        }
        this.view.setPlayerColor(World.getAllSpaces(this.context));
      } catch (IllegalArgumentException | IllegalStateException exception) {
        this.view.displayError(exception.getMessage());
      }
    }
  }

  private void addPlayerScreen() {
    this.timer.stop();
    if (!Objects.equals(Boolean.TRUE, gameEngine.gameOver())) {
      this.view.addPlayerScreen(World.getAllSpaces(this.context));
    } else {
      this.view.displayError("Game is Over!");
    }
  }

  private void displayBoard(String message) {
    logger.add(message);
    try {
      if (Boolean.TRUE.equals(gameEngine.gameOver())) {
        timer.stop();
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

        this.view.refreshBoard(context, this.gameEngine.getCurrentPlayer(),
            this.gameEngine.getTurn(), buildMessage.toString(), mouseListener);
      }
    } catch (BusinessException e) {
      timer.stop();
      this.view.displayError(e.getMessage());
    }
  }

  private void computerPlayerAction() {
    try {
      Player player = this.gameEngine.getCurrentPlayer();
      if (Objects.equals(PlayerType.COMPUTER_CONTROLLED, player.getType())) {
        Flow flow = determineFlowForComputer(this.context, player);
        ContextHolder.set(this.context);

        executeFlow(flow, player, null, false, false);

      }
    } catch (IOException | BusinessException e) {
      this.view.displayError(e.getMessage());
    }
  }

  private void executePick() {
    try {
      Player player = this.gameEngine.getCurrentPlayer();
      if (Objects.equals(PlayerType.HUMAN_CONTROLLED, player.getType())) {
        Space space = World.getSpace(context, player.getSpaceIndex());
        ContextHolder.set(this.context);

        String weapon = this.view.displayWeapons(space.getWeapons());
        if (Objects.nonNull(weapon)) {
          executeFlow(Flow.PICK_UP_WEAPON, player, weapon, false, true);
        }
      }
    } catch (IOException | BusinessException e) {
      this.view.displayError(e.getMessage());
    }
  }

  private void executeLook() {
    try {
      Player player = this.gameEngine.getCurrentPlayer();
      if (Objects.equals(PlayerType.HUMAN_CONTROLLED, player.getType())) {
        ContextHolder.set(this.context);

        executeFlow(Flow.LOOK_AROUND, player, null, true, false);

      }
    } catch (IOException | BusinessException e) {
      this.view.displayError(e.getMessage());
    }
  }

  private void executeAttack() {
    try {
      Player player = this.gameEngine.getCurrentPlayer();
      if (Objects.equals(PlayerType.HUMAN_CONTROLLED, player.getType())) {
        ContextHolder.set(this.context);

        if (player.getWeapons().isEmpty()) {
          executeFlow(Flow.ATTACK_TARGET, player, Constants.NO_WEAPON, true, true);
        } else {
          executeFlow(Flow.ATTACK_TARGET, player, player.getWeapons().get(0).getName(), true, true);
        }
      }
    } catch (IOException | BusinessException e) {
      this.view.displayError(e.getMessage());
    }
  }

  private void executeMovePlayer(String input) {
    try {
      Player player = this.gameEngine.getCurrentPlayer();
      if (Objects.equals(PlayerType.HUMAN_CONTROLLED, player.getType())) {
        ContextHolder.set(this.context);

        executeFlow(Flow.MOVE_PLAYER, player, input, false, true);

      }
    } catch (IOException | BusinessException e) {
      this.view.displayError(e.getMessage());
    }
  }

  private void executeMovePet(String input) {
    try {
      Player player = this.gameEngine.getCurrentPlayer();
      if (Objects.equals(PlayerType.HUMAN_CONTROLLED, player.getType())) {
        ContextHolder.set(this.context);

        executeFlow(Flow.MOVE_PET, player, input, false, true);

      }
    } catch (IOException | BusinessException e) {
      this.view.displayError(e.getMessage());
    }
  }

  private void executeFlow(Flow flow, Player player, String input, Boolean displayInfo,
      Boolean appendResult) throws IOException {
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
      updateGameState();
      if (displayInfo) {
        this.view.displayInfo(baseResult.getResult());
      }
      if (appendResult) {
        displayBoard(message + " " + baseResult.getResult());
      } else {
        displayBoard(message);
      }
    } else {
      this.view.displayError(baseResult.getErrorMsg());
    }
  }

  private void showPlayerDetails(String playerName) {
    Player player = this.context.getPlayers().stream()
        .filter(item -> Objects.equals(item.getName(), playerName)).findFirst().orElse(null);
    Objects.requireNonNull(player);
    ContextHolder.set(this.context);

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

    Space space = World.getSpace(this.context, target.getPosition());
    this.view.displayInfo(
        String.format("Target Details:\nTarget Name = %s\nHealth = %s\nTarget Location = %s",
            target.getName(), target.getHealth(), space.getName()));
  }

  private void updateGameState() {
    if (this.gameEngine.updateState()) {
      context.setExposedSpaces(new HashSet<>());
      World.moveTarget(this.context);
      try {
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
      } catch (IOException e) {
        this.view.displayError(e.getMessage());
      }
    }
  }

}
