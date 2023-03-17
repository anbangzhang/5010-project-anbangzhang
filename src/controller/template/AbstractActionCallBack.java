package controller.template;

import controller.template.result.BaseResult;
import world.context.Context;

/**
 * AbstractActionCallBack.
 * 
 * @author anbang
 * @date 2023-03-16 21:30
 */
public abstract class AbstractActionCallBack<T extends BaseResult, C extends Context>
    implements ActionCallBack<T, C> {

  @Override
  public void beforeProcess(C context) {

  }

  @Override
  public void afterProcess(C context) {

  }

}
