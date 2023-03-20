package flowengine.template.impl;

import flowengine.process.ProcessTemplateCallBack;
import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;
import flowengine.template.ServiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import world.context.Context;
import world.context.ContextHolder;

/**
 * ServiceTemplateImpl.
 * 
 * @author anbang
 * @date 2023-03-16 21:41
 */
@Component(value = "serviceTemplate")
public class ServiceTemplateImpl<T extends BaseRequest> implements ServiceTemplate<T> {

  @Autowired
  @Qualifier(value = "genericProcessTemplateCallBack")
  private ProcessTemplateCallBack process;

  @Override
  public BaseResult execute(String flowName, T request) {

    Context context = ContextHolder.get();
    context.setFlow(flowName);
    context.setRequest(request);

    BaseResult result;

    try {

      paramValidate(context, process);

      bizValidate(context, process);

      beforeProcessCallBack(context, process);

      processCallBack(context, process);

      afterProcessCallBack(context, process);

      result = assembleSuccessResult(context, process);

    } catch (IllegalArgumentException | IllegalStateException e) {

      result = assembleFailResult(e);

    }

    return result;

  }

  private void paramValidate(Context ctx, ProcessTemplateCallBack process) {
    process.paramValidate(ctx);
  }

  private void bizValidate(Context ctx, ProcessTemplateCallBack process) {
    process.bizValidate(ctx);
  }

  private void beforeProcessCallBack(Context ctx, ProcessTemplateCallBack process) {
    process.beforeProcess(ctx);
  }

  private void processCallBack(Context ctx, ProcessTemplateCallBack process) {
    process.process(ctx);
  }

  private void afterProcessCallBack(Context ctx, ProcessTemplateCallBack process) {
    process.afterProcess(ctx);
  }

  private BaseResult assembleSuccessResult(Context ctx, ProcessTemplateCallBack process) {
    return process.assemble(ctx);
  }

  private BaseResult assembleFailResult(Exception e) {
    return BaseResult.newFailResult().errorMsg(e.getMessage()).build();
  }

}
