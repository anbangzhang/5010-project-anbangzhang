package flowengine.action;

import flowengine.context.FlowContext;
import java.io.IOException;
import world.exception.BusinessException;

/**
 * Action.
 * 
 * @author anbang
 * @date 2023-03-18 06:51
 */
public interface Action<T extends FlowContext> {

  /**
   * execute.
   * 
   * @param context context
   * @throws BusinessException business exception
   * @throws IOException       io exception
   */
  void execute(T context) throws IOException;

}
