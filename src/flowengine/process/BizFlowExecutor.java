package flowengine.process;

import flowengine.context.FlowContext;
import flowengine.result.BaseResult;
import java.io.IOException;

/**
 * BizFlowExecutor.
 * 
 * @author anbang
 * @date 2023-03-15 18:13
 */
public interface BizFlowExecutor {

  /**
   * Execute the flow.
   * 
   * @param context context
   * @throws IOException io exception
   */
  void startProcess(FlowContext context) throws IOException;

}
