package application;

import controller.ControllerApplication;
import controller.WorldController;
import controller.impl.WorldConsoleController;
import controller.impl.WorldGuiController;
import flowengine.FlowEngineApplication;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import model.constant.Constants;
import model.context.Context;
import model.context.ContextBuilder;
import model.context.ContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

/**
 * WorldDriver class.
 * 
 * @author anbang
 * @date 2023-02-01 22:59
 */
@Import(value = { FlowEngineApplication.class, ControllerApplication.class })
public class WorldDriver {

  /**
   * main function.
   * 
   * @param args arguments
   */
  public static void main(String[] args) {
    switch (args.length) {
      case 0:
        System.out.println("Please input valid model specification file address.");
        return;
      case 1:
        System.out.println("Please input valid turn amount.");
        return;
      case 2:
        System.out.println("Please input valid maximum player amount.");
        return;
      case 3:
        System.out.println("Please input valid mode.");
        return;
      default:
        break;
    }
    try (FileReader fileReader = new FileReader(args[0])) {

      Context context = ContextBuilder.build(fileReader);
      ContextHolder.set(context);

      WorldController controller;
      ApplicationContext ctx = new AnnotationConfigApplicationContext(WorldDriver.class);
      if (Constants.GUI.equalsIgnoreCase(args[3])) {

        WorldGuiController guiController = ctx.getBean(WorldGuiController.class);
        guiController.setTurn(Integer.parseInt(args[1]));
        guiController.setUp(context, args[0]);

        controller = guiController;

      } else if (Constants.TEXT.equalsIgnoreCase(args[3])) {

        WorldConsoleController consoleController = ctx.getBean(WorldConsoleController.class);
        consoleController.setIn(new InputStreamReader(System.in));
        consoleController.setOut(System.out);
        consoleController.setTurn(Integer.parseInt(args[1]));

        controller = consoleController;
      } else {
        throw new IllegalArgumentException("Invalid Game Mode.");
      }

      controller.setMaxPlayerAmount(Integer.parseInt(args[2]));

      controller.playGame(context);

      ContextHolder.remove();
    } catch (FileNotFoundException e1) {
      System.out.println(String.format("File: %s doesn't exist.", args[0]));
    } catch (IOException e2) {
      System.out.println(String.format("File: %s closure failed.", args[0]));
    }
  }

}
