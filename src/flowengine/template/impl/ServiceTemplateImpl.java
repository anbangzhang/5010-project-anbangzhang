package flowengine.template.impl;

import com.google.common.collect.ImmutableMap;
import flowengine.context.FlowContext;
import flowengine.enums.Flow;
import flowengine.process.ProcessTemplateCallBack;
import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;
import flowengine.template.ServiceTemplate;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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

  private Map<String, List<String>> flowMap = ImmutableMap.<String, List<String>>builder()
      .put(Flow.MOVE_PLAYER.getDesc(), Arrays.asList("genericProcessTemplateCallBack"))
      .put(Flow.PICK_UP_WEAPON.getDesc(), Arrays.asList("genericProcessTemplateCallBack"))
      .put(Flow.LOOK_AROUND.getDesc(), Arrays.asList("genericProcessTemplateCallBack"))
      .put(Flow.MOVE_PET.getDesc(), Arrays.asList("genericProcessTemplateCallBack"))
      .put(Flow.ATTACK_TARGET.getDesc(), Arrays.asList("genericProcessTemplateCallBack"))
      .put(Flow.PET_DFS.getDesc(), Arrays.asList("genericProcessTemplateCallBack")).build();

  @Autowired
  private Map<String, ProcessTemplateCallBack> processMap;

  @Override
  public BaseResult execute(String flowName, T request) throws IOException {

    Context ctx = ContextHolder.get();
    FlowContext context = new FlowContext(ctx);

    context.setFlow(flowName);
    context.setRequest(request);

    BaseResult result;

    try {

      List<String> processNames = flowMap.getOrDefault(flowName, Arrays.asList());
      List<ProcessTemplateCallBack> processes = processNames.stream().map(processMap::get)
          .collect(Collectors.toList());

      for (ProcessTemplateCallBack process : processes) {

        paramValidate(context, process);

        bizValidate(context, process);

        beforeProcessCallBack(context, process);

        processCallBack(context, process);

        afterProcessCallBack(context, process);
      }

      result = assembleSuccessResult(context);

    } catch (IllegalArgumentException | IllegalStateException e) {

      result = assembleFailResult(e);

    }

    return result;

  }

  private void paramValidate(FlowContext ctx, ProcessTemplateCallBack process) {
    process.paramValidate(ctx);
  }

  private void bizValidate(FlowContext ctx, ProcessTemplateCallBack process) {
    process.bizValidate(ctx);
  }

  private void beforeProcessCallBack(FlowContext ctx, ProcessTemplateCallBack process) {
    process.beforeProcess(ctx);
  }

  private void processCallBack(FlowContext ctx, ProcessTemplateCallBack process)
      throws IOException {
    process.process(ctx);
  }

  private void afterProcessCallBack(FlowContext ctx, ProcessTemplateCallBack process) {
    process.afterProcess(ctx);
  }

  private BaseResult assembleSuccessResult(FlowContext ctx) {
    if (Objects.nonNull(ctx.getContext())) {
      return ctx.getResult();
    }
    return BaseResult.newSuccessResult().build();
  }

  private BaseResult assembleFailResult(Exception e) {
    return BaseResult.newFailResult().errorMsg(e.getMessage()).build();
  }

}
