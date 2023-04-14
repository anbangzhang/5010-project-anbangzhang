package model.constant;

/**
 * Constants.
 * 
 * @author anbang
 * @date 2023-03-15 20:16
 */
public class Constants {
  /**
   * M.
   */
  public static final String M = "m";
  /**
   * N.
   */
  public static final String N = "n";
  /**
   * World name.
   */
  public static final String WORLD_NAME = "worldName";
  /**
   * Target.
   */
  public static final String TARGET = "target";
  /**
   * Pet.
   */
  public static final String PET = "pet";
  /**
   * SPACES.
   */
  public static final String SPACES = "spaces";
  /**
   * Neighbor map.
   */
  public static final String NEIGHBOR_MAP = "neighborMap";
  /**
   * Exposed spaces.
   */
  public static final String EXPOSED_SPACES = "exposedSpaces";
  /**
   * WEAPONS.
   */
  public static final String WEAPONS = "weapons";
  /**
   * EVIDENCES.
   */
  public static final String EVIDENCES = "evidences";
  /**
   * PLAYERS.
   */
  public static final String PLAYERS = "players";
  /**
   * SCANNER.
   */
  public static final String SCANNER = "scanner";
  /**
   * OUT.
   */
  public static final String OUT = "out";
  /**
   * TIMER DELAY.
   */
  public static final int TIMER_DELAY = 3000;
  /**
   * COMPUTER DEFAULT.
   */
  public static final String COMPUTER_DEFAULT = "##computer_default##";
  /**
   * WINNER.
   */
  public static final String WINNER = "##Winner##";
  /**
   * CONTEXT.
   */
  public static final String CONTEXT = "##Context##";
  /**
   * NEW GAME.
   */
  public static final String NEW_GAME = "New Game";
  /**
   * CREATE NEW GAME.
   */
  public static final String CREATE_NEW_GAME = "Create New Game";
  /**
   * PLAY GAME.
   */
  public static final String PLAY_GAME = "Play Game";
  /**
   * RESTART GAME.
   */
  public static final String RESTART_GAME = "Restart Game";
  /**
   * QUIT GAME.
   */
  public static final String QUIT_GAME = "Quit Game";
  /**
   * ABOUT GAME.
   */
  public static final String ABOUT_GAME = "About Game";
  /**
   * HOW TO PLAY GAME.
   */
  public static final String HOW_TO_PLAY_GAME = "How to Play Game";
  /**
   * ADD PLAYER.
   */
  public static final String ADD_PLAYER = "Add Player";
  /**
   * ADD.
   */
  public static final String ADD = "Add";
  /**
   * NULL.
   */
  public static final String NULL = "NULL";
  /**
   * GUI.
   */
  public static final String GUI = "GUI";
  /**
   * TEXT.
   */
  public static final String TEXT = "TEXT";
  /**
   * NO WEAPON.
   */
  public static final String NO_WEAPON = "poking him in the eye";
  /**
   * HELP_TIPS.
   */
  public static final String HELP_TIPS = "<html>" + "How To Play<br/>" + "<br/>" + "Menu<br/>"
      + "New Game allows users to change the settings of the game.<br/>"
      + "Restart Game allows users to start the game with the previous settings.<br/>"
      + "Add Player allow users to add a player to the game.<br/>"
      + "Quit Game allow users to quit the game.<br/>" + "<br/>" + "<b>Keys</b> <br/>"
      + "Press l  player can look around. <br/>" + "Press a  player can attack the target.<br/>"
      + "Press p  player can pick up a weapon from the space. <br/>" + "<br/>"
      + "<b>Mouse Click</b> <br/>" + "Click on a player's icon displays player's description.<br/>"
      + "Click on a neighbor room, the current player moves to that room. <br/> "
      + "Right Click on a room, the current player moves the pet to that room. <br/> " + "</html>";
  /**
   * ABOUT STRING.
   */
  public static final String ABOUT_STRING = "<html>"
      + "The world of consists of several non-overlapping spaces"
      + " that are laid out on a 2D grid.<br/> The details of the world are "
      + "specified in a simple text file that consists of three sections: <br/>"
      + "<br/>1. world description including the size, the name, the target "
      + "character, and the target character's pet.<br/> 2. a detailed list of "
      + "all the spaces or rooms that make up the world<br/> 3. a detailed list "
      + "of all the items that can be found in the world<br/> "
      + "<br/>This game has two types of players: Human and Computer Players. "
      + "Each player should be identified by their name. <br/> They enter the world "
      + "in a space of their choice. With multiple players, each player gets a turn "
      + "in the order in which <br/>they were added to the game. The options for "
      + "actions that player can take in a single turn are:<br/><br/> 1. Move "
      + "to a neighbor space.<br/> 2. Pick up a weapon from the space they "
      + "are currently occupying.<br/> 3. Look around the space they are currently "
      + "occupying. <br/> 4. Move the Pet to a space. <br/> 5. Attempt to attack "
      + "the Target with the weapon they have. <br/>" + "</html>";
}
