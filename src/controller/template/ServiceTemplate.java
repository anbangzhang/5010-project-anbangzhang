package controller.template;

import controller.template.result.BaseResult;
import world.context.Context;

/**
 * ServiceTemplate.
 * 
 * @author anbang
 * @date 2023-03-16 21:38
 */
public interface ServiceTemplate<T extends BaseResult, C extends Context> {

  /**
   * Execute.
   * 
   * @param context
   * @param action
   * @return
   */
  T execute(C context, AbstractActionCallBack<T, C> action);

}
