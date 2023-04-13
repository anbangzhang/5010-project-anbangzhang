package view;

import java.util.List;
import model.base.BaseWeapon;
import model.context.Context;
import model.model.Player;
import view.listener.ButtonListener;
import view.listener.KeyboardListener;
import view.listener.MouseClickListener;

/**
 * WorldView.
 * 
 * @author anbang
 * @date 2023-04-03 03:57
 */
public interface WorldView {

  /**
   * Displays the list of items with their damages available for use.
   *
   * @param weapons list of weapons
   * @return selected weapon name
   */
  String displayWeapons(List<BaseWeapon> weapons);

  /**
   * Displays the message as information to the user.
   *
   * @param message information message
   */
  void displayInfo(String message);

  /**
   * Displays the message as error to the user.
   *
   * @param message error message
   */
  void displayError(String message);

  /**
   * Restarts the game.
   *
   * @param spaceNames list of spaces in the current game
   */
  void restartGame(List<String> spaceNames);

  /**
   * Displays the screen to add players in the game.
   *
   * @param spaceNames list of rooms in the current game
   */
  void addPlayerScreen(List<String> spaceNames);

  /**
   * Displays the screen to setup a new game with a new model specification.
   */
  void setupScreen();

  /**
   * Refreshes board to display the current state of the game.
   *
   * @param context       context
   * @param currentPlayer current player of the game
   * @param turns         turns
   * @param message       Result of the previous action
   * @param listener      Listener to execute the actions
   */
  void refreshBoard(Context context, Player currentPlayer, int turns, String message,
      MouseClickListener listener);

  /**
   * Displays the welcome screen.
   */
  void welcomeScreen();

  /**
   * Quits the game.
   */
  void quit();

  /**
   * Adds button listener to the components in the game.
   *
   * @param buttonListener button listener object
   */
  void addActionListener(ButtonListener buttonListener);

  /**
   * Adds keyboard listener to the components in the game.
   *
   * @param keyboardListener keyboard listener object
   */
  void addActionListener(KeyboardListener keyboardListener);

  /**
   * Asks for confirmation from the user.
   *
   * @param message String asking confirmation
   * @return integer value of selected option same as JOptionPane
   */
  int confirmScreen(String message);

  /**
   * Gets the input to create player.
   *
   * @return String array with name of the player, starting location of the
   *         player, item picking capacity and if the player is human or not, in
   *         the same order.
   */
  String[] getPlayerInput();

  /**
   * Sets player's color representation against their name.
   *
   * @param spaceNames list of spaces in the current game
   */
  void setPlayerColor(List<String> spaceNames);

  /**
   * Gets the input to create new game.
   *
   * @return String array with file path of the new configuration file, number of
   *         turns for new game and maximum number of players for the new game, in
   *         the same order.
   */
  String[] getSetupInput();

  /**
   * Displays the results of the game when game ends.
   *
   * @param result result of the game
   */
  void gameEndScreen(String result);

}
