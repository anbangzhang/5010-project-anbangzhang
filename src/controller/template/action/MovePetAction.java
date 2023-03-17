package controller.template.action;

import controller.template.result.BaseResult;
import controller.template.AbstractActionCallBack;
import world.context.Context;

/**
 * MovePetAction.
 * 
 * @author anbang
 * @date 2023-03-15 20:31
 */
public class MovePetAction extends AbstractActionCallBack<BaseResult, Context> {

  @Override
  public void paramValidate(Context context) {

  }

  @Override
  public void bizValidate(Context context) {

  }

  @Override
  public void process(Context context) {

  }

  @Override
  public BaseResult assemble(Context context) {
    return BaseResult.newSuccessResult().build();
  }

}
