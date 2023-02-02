package world;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import world.impl.WorldImpl;

/**
 * WorldDriver class.
 * 
 * @author anbang
 * @date 2023-02-01 22:59
 */
public class WorldDriver {

  /**
   * Main function, take the input file and draw the image of world.
   * 
   * @param args inputs
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Please input valid world specification file address.");
      return;
    }
    if (args.length == 1) {
      System.out.println("Please input valid output file directory.");
      return;
    }
    try {
      FileReader fileReader = new FileReader(args[0]);
      World world = new WorldImpl(fileReader);
      world.showGraphicalImage(args[1]);
      System.out.println(
          String.format("Graphical image of %s has been generated, please check the %s directory.",
              args[0], args[1]));
    } catch (FileNotFoundException e2) {
      System.out.println(String.format("File: %s doesn't exist.", args[0]));
    } catch (IOException e) {
      System.out.println(String.format("Graphical image generation at %s failed.", args[1]));
    }
  }

}
