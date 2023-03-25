package flowengine.process;

import flowengine.context.FlowContext;
import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;

/**
 * BaseProcessCallBack.
 * 
 * @author anbang
 * @date 2023-03-24 23:06
 */
public abstract class BaseProcessCallBack<T extends BaseRequest, R extends BaseResult> {

  /**
   * Enrich context.
   * 
   * @param request request
   * @param context context
   */
  public void enrichContext(T request, FlowContext context) {
  }

  /**
   * Process.
   * 
   * @param request request
   * @param result  result
   * @param context context
   */
  public void process(T request, R result, FlowContext context) {
  }

  /**
   * Fill result.
   * 
   * @param request request
   * @param result  result
   * @param context context
   */
  public void fillResult(T request, R result, FlowContext context) {
  }

  /**
   * Create default result.
   * 
   * @param <R> result type
   * @return result
   */
  public <R extends BaseResult> R createDefaultResult() {
    return (R) BaseResult.newSuccessResult().build();
  }

}
