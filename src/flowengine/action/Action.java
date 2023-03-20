package flowengine.action;

import world.context.Context;

/**
 * Action.
 * 
 * @author anbang
 * @date 2023-03-18 06:51
 */
public interface Action<T extends Context> {

  /**
   * execute.
   * 
   * @param context context
   */
  void execute(T context);

}
