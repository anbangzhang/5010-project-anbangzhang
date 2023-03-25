package flowengine.template.impl;

import flowengine.context.FlowContext;
import flowengine.process.BaseProcessCallBack;
import flowengine.process.BizFlowExecutor;
import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;
import flowengine.template.ServiceTemplate;
import java.io.IOException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * ServiceTemplateImpl.
 * 
 * @author anbang
 * @date 2023-03-16 21:41
 */
@Component(value = "serviceTemplate")
public class ServiceTemplateImpl<T extends BaseRequest, R extends BaseResult>
    implements ServiceTemplate<T, R> {

  @Autowired
  @Qualifier(value = "bizFlowExecutor")
  private BizFlowExecutor bizFlowExecutor;

  @Override
  public R execute(T request, String flowName, BaseProcessCallBack<T, R> callBack)
      throws IOException {

    FlowContext context = new FlowContext();

    R result = callBack.createDefaultResult();

    try {

      validateRequest(request);

      initFlowContext(context, flowName, request, result);

      callBack.enrichContext(request, context);

      bizFlowExecutor.startProcess(context);

      callBack.process(request, result, context);

      callBack.fillResult(request, result, context);

      result = assembleSuccessResult(context);

    } catch (IllegalArgumentException | IllegalStateException e) {

      result = assembleFailResult(e);

    }

    return result;

  }

  private void validateRequest(T request) {
  }

  private void initFlowContext(FlowContext context, String flowName, T request, R result) {
    context.setFlow(flowName);
    context.setRequest(request);
    context.setResult(result);
  }

  private <R extends BaseResult> R assembleSuccessResult(FlowContext ctx) {
    if (Objects.nonNull(ctx.getContext())) {
      return (R) ctx.getResult();
    }
    return (R) BaseResult.newSuccessResult().build();
  }

  private <R extends BaseResult> R assembleFailResult(Exception e) {
    return (R) BaseResult.newFailResult().errorMsg(e.getMessage()).build();
  }

}
