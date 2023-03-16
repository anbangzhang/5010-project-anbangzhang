package controller.action;

import world.container.Context;

/**
 * Action.
 * 
 * @author anbang
 * @date 2023-03-15 18:13
 */
public interface Action {

  /**
   * Execute the action.
   * 
   * @param context context
   */
  void execute(Context context);

}
