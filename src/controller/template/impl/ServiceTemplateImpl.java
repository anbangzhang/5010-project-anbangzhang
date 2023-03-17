package controller.template.impl;

import controller.template.result.BaseResult;
import controller.template.AbstractActionCallBack;
import controller.template.ServiceTemplate;
import world.container.Context;
import world.exception.BusinessException;

/**
 * ServiceTemplateImpl.
 * 
 * @author anbang
 * @date 2023-03-16 21:41
 */
public class ServiceTemplateImpl implements ServiceTemplate {

  @Override
  public BaseResult execute(Context context, AbstractActionCallBack action) {
    BaseResult result;
    try {

      paramValidate(context, action);

      bizValidate(context, action);

      beforeProcessCallBack(context, action);

      processCallBack(context, action);

      afterProcessCallBack(context, action);

      result = assembleSuccessResult(context, action);
    } catch (IllegalArgumentException | BusinessException e) {
      result = assembleFailResult(e);
    }
    return result;
  }

  private void paramValidate(Context ctx, AbstractActionCallBack action)
      throws IllegalArgumentException {
    action.paramValidate(ctx);
  }

  private void bizValidate(Context ctx, AbstractActionCallBack action) throws BusinessException {
    action.bizValidate(ctx);
  }

  private void beforeProcessCallBack(Context ctx, AbstractActionCallBack action) {
    action.beforeProcess(ctx);
  }

  private void processCallBack(Context ctx, AbstractActionCallBack action) {
    action.process(ctx);
  }

  private void afterProcessCallBack(Context ctx, AbstractActionCallBack action) {
    action.afterProcess(ctx);
  }

  private BaseResult assembleSuccessResult(Context ctx, AbstractActionCallBack action) {
    return action.assemble(ctx);
  }

  private BaseResult assembleFailResult(Exception e) {
    return BaseResult.newFailResult().errorMsg(e.getMessage()).build();
  }

}
