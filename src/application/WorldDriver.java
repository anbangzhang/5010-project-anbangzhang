package application;

import controller.WorldController;
import controller.impl.WorldConsoleController;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import world.context.Context;
import world.context.ContextBuilder;
import world.context.ContextHolder;

/**
 * WorldDriver class.
 * 
 * @author anbang
 * @date 2023-02-01 22:59
 */
@ComponentScan(basePackages = {"controller", "flowengine"})
public class WorldDriver {

  /**
   * main function.
   * 
   * @param args arguments
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Please input valid world specification file address.");
      return;
    }
    if (args.length == 1) {
      System.out.println("Please input valid turn amount.");
      return;
    }
    try (FileReader fileReader = new FileReader(args[0])) {

      Context context = ContextBuilder.build(fileReader);
      ContextHolder.set(context);

      Readable in = new InputStreamReader(System.in);
      ApplicationContext ctx = new AnnotationConfigApplicationContext(WorldDriver.class);

      WorldController controller = ctx.getBean(WorldConsoleController.class);
      controller.setIn(in);
      controller.setOut(System.out);
      controller.setTurn(Integer.parseInt(args[1]));

      controller.playGame(context);

      ContextHolder.remove();
    } catch (FileNotFoundException e1) {
      System.out.println(String.format("File: %s doesn't exist.", args[0]));
    } catch (IOException e2) {
      System.out.println(String.format("File: %s closure failed.", args[0]));
    }
  }

}
