package controller.template;

import controller.template.result.BaseResult;
import world.container.Context;
import world.exception.BusinessException;

/**
 * ActionCallBack.
 * 
 * @author anbang
 * @date 2023-03-15 18:13
 */
public interface ActionCallBack<T extends BaseResult, C extends Context> {

  /**
   * Validate parameters.
   * 
   * @param context context
   * @throws IllegalArgumentException illegal argument
   */
  void paramValidate(C context) throws IllegalArgumentException;

  /**
   * Validate business logic.
   *
   * @param context context
   * @throws BusinessException biz exception
   */
  void bizValidate(C context) throws BusinessException;

  /**
   * Before action.
   * 
   * @param context context
   */
  void beforeProcess(C context);

  /**
   * Execute the action.
   * 
   * @param context context
   */
  void process(C context);

  /**
   * After action.
   * 
   * @param context context
   */
  void afterProcess(C context);

  /**
   * Assemble result
   * 
   * @param context context
   * @return result
   */
  T assemble(C context);

}
