package flowengine.context;

import flowengine.request.BaseRequest;
import flowengine.result.BaseResult;
import java.util.HashMap;
import java.util.Map;
import model.constant.Constants;
import model.context.Context;

/**
 * FlowContext.
 * 
 * @author anbang
 * @date 2023-03-21 04:34
 */
public class FlowContext {

  /**
   * map.
   */
  private final Map<String, Object> map = new HashMap<>();

  /**
   * flow name.
   */
  private String flow;
  /**
   * Request.
   */
  private BaseRequest request;
  /**
   * result.
   */
  private BaseResult result;

  /**
   * Set context.
   * 
   * @param ctx context
   */
  public void setContext(Context ctx) {
    this.map.put(Constants.CONTEXT, ctx);
  }

  /**
   * Get context.
   */
  public Context getContext() {
    return (Context) this.map.get(Constants.CONTEXT);
  }

  /**
   * Set flow.
   *
   * @param flow flow
   */
  public void setFlow(String flow) {
    this.flow = flow;
  }

  /**
   * Get flow.
   *
   * @return flow
   */
  public String getFlow() {
    return this.flow;
  }

  /**
   * Set request.
   *
   * @param request request
   */
  public void setRequest(BaseRequest request) {
    this.request = request;
  }

  /**
   * Get request.
   *
   * @return request
   */
  public BaseRequest getRequest() {
    return request;
  }

  /**
   * Set result.
   *
   * @param result result
   */
  public void setResult(BaseResult result) {
    this.result = result;
  }

  /**
   * Get result.
   *
   * @return result
   */
  public BaseResult getResult() {
    return result;
  }
}
