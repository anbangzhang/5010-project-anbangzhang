package controller.template.action;

import controller.template.AbstractActionCallBack;
import controller.template.result.BaseResult;
import world.context.Context;

/**
 * AttackAction.
 * 
 * @author anbang
 * @date 2023-03-15 19:59
 */
public class AttackAction extends AbstractActionCallBack<BaseResult, Context> {

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
