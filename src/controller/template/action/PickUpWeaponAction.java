package controller.template.action;

import controller.template.result.BaseResult;
import controller.template.ActionCallBack;
import world.container.Context;

/**
 * PickUpWeaponAction.
 * 
 * @author anbang
 * @date 2023-03-15 20:22
 */
public class PickUpWeaponAction implements ActionCallBack {

  @Override
  public void paramValidate(Context context) {

  }

  @Override
  public void bizValidate(Context context) {

  }

  @Override
  public void beforeProcess(Context context) {

  }

  @Override
  public void process(Context context) {

  }

  @Override
  public void afterProcess(Context context) {

  }

  @Override
  public BaseResult assemble(Context context) {
    return BaseResult.newSuccessResult().build();
  }

}
