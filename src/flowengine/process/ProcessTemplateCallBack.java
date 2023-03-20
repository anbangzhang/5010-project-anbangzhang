package flowengine.process;

import flowengine.result.BaseResult;
import world.context.Context;

import java.io.IOException;

/**
 * ProcessTemplateCallBack.
 * 
 * @author anbang
 * @date 2023-03-15 18:13
 */
public interface ProcessTemplateCallBack<T extends Context> {

  /**
   * Validate parameters.
   * 
   * @param context context
   */
  void paramValidate(T context);

  /**
   * Validate business logic.
   *
   * @param context context
   */
  void bizValidate(T context);

  /**
   * Before process.
   * 
   * @param context context
   */
  void beforeProcess(T context);

  /**
   * Execute the process.
   * 
   * @param context context
   * @throws IOException io exception
   */
  void process(T context) throws IOException;

  /**
   * After action.
   * 
   * @param context context
   */
  void afterProcess(T context);

  /**
   * Assemble result.
   * 
   * @param context context
   * @return result
   */
  BaseResult assemble(T context);

}
