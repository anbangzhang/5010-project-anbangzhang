package flowengine.action;

import java.io.IOException;
import world.context.Context;
import world.exception.BusinessException;

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
   * @throws BusinessException business exception
   * @throws IOException       io exception
   */
  void execute(T context) throws  IOException;

}
