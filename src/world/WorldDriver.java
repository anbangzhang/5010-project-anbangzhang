package world;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import world.base.BaseSpace;
import world.impl.WorldImpl;
import world.model.Space;
import world.model.Weapon;

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
    try (FileReader fileReader = new FileReader(args[0])) {
      World world = new WorldImpl(fileReader);
      boolean exit = false;
      int num = -1;
      String row;
      Scanner scan = new Scanner(System.in);
      while (!exit) {
        System.out.println(
            "Please use the order number below to select the function:\n\t1. getAllSpaces\n"
                + "\t2. getNeighbors\n\t3. getSpace\n\t4. getTargetPosition\n\t5. moveTarget\n"
                + "\t6. showGraphicalImage\n\t7. exit\n");
        row = scan.nextLine().trim();
        if (!row.isEmpty()) {
          num = Integer.parseInt(row);
        }

        if (num == 1) {
          List<String> spaces = world.getAllSpaces();
          System.out.println(String.format("The spaces: %s\n", spaces));
        } else if (num == 2) {
          System.out.println("Please input the index of space.");
          num = Integer.parseInt(scan.nextLine().trim());
          System.out.println(
              String.format("The neighbors of %sth space: %s\n", num, world.getNeighbors(num)));
        } else if (num == 3) {
          System.out.println("Please input the index of space.");
          num = Integer.parseInt(scan.nextLine().trim());
          Space space = world.getSpace(num);
          if (Objects.isNull(space)) {
            System.out.println("The space is null.\n");
          } else {
            System.out.println(String.format(
                "The space is %s, its neighbors: %s, weapons inside this space: %s.\n",
                space.getName(),
                space.getNeighbors().stream().map(BaseSpace::getName).collect(Collectors.toList()),
                space.getWeapons().stream().map(Weapon::getName).collect(Collectors.toList())));
          }
        } else if (num == 4) {
          Space space = world.getTargetPosition();
          System.out.println(String.format("The target is at space %s.\n", space.getName()));
        } else if (num == 5) {
          Space space = world.moveTarget();
          System.out.println(String.format("The target moves to space %s.\n", space.getName()));
        } else if (num == 6) {
          System.out.println("Please input the output directory:\n");
          row = scan.nextLine().trim();
          try {
            world.showGraphicalImage(row);
            System.out.println(String.format(
                "Graphical image has been generated, please check the %s directory.\n", row));
          } catch (IOException e2) {
            System.out.println(String.format("Graphical image generation at %s failed.", row));
          }
        } else if (num == 7) {
          exit = true;
        } else {
          System.out.println("Invalid input.");
        }

      }
    } catch (FileNotFoundException e1) {
      System.out.println(String.format("File: %s doesn't exist.", args[0]));
    } catch (IOException e2) {
      System.out.println(String.format("File:%s close failed.", args[0]));
    }
  }

}
